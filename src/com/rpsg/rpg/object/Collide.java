package com.rpsg.rpg.object;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;


public class Collide {
	public boolean left;
	public boolean right;
	public boolean top;
	public boolean bottom;
	
	
	public void testCollide(int x,int y,int[][] t,Array<Actor> list,IRPGObject mine){
			left=!(x==0 || t[y][x-1]!=0);
			right=!(x==t[0].length-1 || t[y][x+1]!=0);
			top=!(y==0 || t[y-1][x]!=0);
			bottom=!(y==t.length-1 || t[y+1][x]!=0);
			for(int i=0;i<list.size;i++){
				Actor a=list.get(i);
				if(a instanceof IRPGObject && ((IRPGObject)a).enableCollide && ((IRPGObject)a).layer==mine.layer){
					IRPGObject o=((IRPGObject)a);
					if(mine.mapx+1==o.mapx && mine.mapy==o.mapy)
						right=false;
					if(mine.mapx-1==o.mapx && mine.mapy==o.mapy)
						left=false;
					if(mine.mapy-1==o.mapy && mine.mapx==o.mapx)
						top=false;
					if(mine.mapy+1==o.mapy && mine.mapx==o.mapx)
						bottom=false;
				}
			}
	}


	@Override
	public String toString() {
		return "Collide [bottom=" + bottom + ", left=" + left + ", right=" + right + ", top=" + top + "]";
	}
}
