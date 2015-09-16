package com.rpsg.rpg.utils.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PathFinder {
	
	static int[][] data = new int[][]{
		new int[]{1,1,1,1,1,1,1,1,1,1,1,1},
		new int[]{1,0,0,0,0,0,0,0,0,0,0,1},
		new int[]{1,0,0,0,1,1,1,1,0,0,0,1},
		new int[]{1,0,0,1,0,1,0,1,0,0,0,1},
		new int[]{1,0,0,1,0,1,0,0,0,0,0,1},
		new int[]{1,0,0,1,0,1,0,1,0,0,0,1},
		new int[]{1,0,0,1,0,1,0,1,1,0,0,1},
		new int[]{1,0,0,1,0,1,0,0,1,0,0,1},
		new int[]{1,0,0,0,0,1,0,0,0,0,0,1},
		new int[]{1,1,1,1,1,1,1,1,1,1,1,1},
	};
	public static void main(String[] args) {
		find(new Path(5,2),new Path(7,6),data);
	}
	
	public static List<Path> find(Path start,Path end,int[][] map){
		Path current = start;
		List<Path> paths=new ArrayList<Path>();
//		map[end.x][end.y]=3;
		
		int count = 0;
		while(true){
//			if(count++>15) break;
			if(current.equals(end))
				break;
			
			
			Path result = current.generate(map, end).getBestChild();
			
			map[current.x][current.y]=2;
			print(map);
			System.out.println(current);
			System.out.println(current.getAround());
			System.out.println();
			
			if(result==null){
				current.setEnable(false);
				current=current.parent.setEnable(true);
			}else{
				current=result;
			}
			
		}
		
		while(true){
			if(current==null)
				break;
			paths.add(current);
			map[current.x][current.y]=3;
			current=current.parent;
		}
		return paths;
	}
	
	private static boolean testWalkAble(int[][] map,int x,int y){
		return x>=0 && x<map.length && y>=0 && y<map[x].length && map[x][y]==0;
	}

	public static void print (int[][] map){
		for(int[] i:map){
			for(int j:i){
				if(j==0)
					System.out.print("□");
				else if(j==2)
					System.out.print("●");
				else if(j==3)
					System.out.print("○");
				else
					System.out.print("■");
			}
			System.out.println();
		}
	}
	
	public static class Path{
		Path parent;
		Path left,right,up,down;
		double H,G;
		int x,y;
		boolean enable = true;
		
		public Path setEnable(boolean e){
			enable=e;
			if(parent!=null && !parent.enable)
				enable=false;
			return this;
		}
		public Path getParents(Path p){
			Path current = this;
			while(true){
				if(current==null)
					return null;
				if(current.equals(p))
					return current;
				current=current.parent;
			}
		}
		
		public Path generate(int[][] map,Path end){
			if(left==null) left = new Path(x-1,y).setParent(this).setEnable(testWalkAble(map,x-1,y)).calcH(end).calcG();
			if(parent!=null && parent.equals(left)) (left=parent).setEnable(false);
			if(getParents(left)!=null) parent=getParents(left);
			
			if(right==null) right = new Path(x+1,y).setParent(this).setEnable(testWalkAble(map,x+1,y)).calcH(end).calcG();
			if(parent!=null && parent.equals(right)) (right=parent).setEnable(false);
			if(getParents(right)!=null) parent=getParents(right);
			
			if(up==null) up = new Path(x,y+1).setParent(this).setEnable(testWalkAble(map,x,y+1)).calcH(end).calcG();
			if(parent!=null && parent.equals(up)) (up=parent).setEnable(false);
			if(getParents(up)!=null) parent=getParents(up);
			
			if(down==null) down = new Path(x,y-1).setParent(this).setEnable(testWalkAble(map,x,y-1)).calcH(end).calcG();
			if(parent!=null && parent.equals(down)) (down=parent).setEnable(false);
			if(getParents(down)!=null) parent=getParents(down);
			
			
			return this;
		}
		
		public Path calcH(Path end){
			if(enable){
				int width=end.x-this.x;
				int height=end.y-this.y;
//				if(width==0 || height==0)
//					H=width+height;
//				else
					H = Math.sqrt(width*width+height*height)*10;
			}else{
				H= 999999;
			}
			
			return this;
		}
		
		public Path calcG(){
			int count = 0;
			Path current = this;
			while(true){
				current=current.parent;
				count++;
				if(current==null)
					break;
			}
			G=count;
			return this;
		}
		
		public Path getBestChild(){
			List<Path> around = getAround();
			if(around.isEmpty())
				return null;
			Collections.sort(around,new Comparator<Path>() {
				public int compare(Path o1, Path o2) {
					return (o1.H+o1.G)-(o2.H+o2.G)>=0?1:-1;
				}
			});
			return around.get(0);
		}
		
		public List<Path> getAround(){
			List<Path> around = new ArrayList<>();
			if((parent==null || !parent.equals(left)) && left.enable) around.add(left);
			if((parent==null || !parent.equals(right)) && right.enable) around.add(right);
			if((parent==null || !parent.equals(up)) && up.enable) around.add(up);
			if((parent==null || !parent.equals(down)) && down.enable) around.add(down);
			return around;
		}
		
		public Path(int x,int y) {
			this.x=x;
			this.y=y;
		}
		
		public Path setParent(Path parent){
			this.parent=parent;
			return this;
		}
		
		public boolean equals(Object obj) {
			return ((Path)obj).x==this.x && ((Path)obj).y==this.y;  
		}
		
		public String toString() {
			return "[x:"+x+", y:"+y+", H:"+H+", G:"+G+"]";
		}
		
//		public Path 
	}
}
