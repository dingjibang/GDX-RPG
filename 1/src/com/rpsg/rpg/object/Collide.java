package com.rpsg.rpg.object;


import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.rpsg.rpg.object.heros.NPC;
import com.rpsg.rpg.view.GameView;


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
	private static List<ScriptCollide> l=new ArrayList<ScriptCollide>();
	public static List<ScriptCollide> testNPCCollide(GameView gv,IRPGObject mine,Array<Actor> list){
		l.clear();
		for(int i=0;i<list.size;i++){
			Actor a=list.get(i);
			if(a instanceof NPC){
				NPC o=((NPC)a);
				if(mine.layer-1==o.layer && mine.mapx==o.mapx && mine.mapy==o.mapy){
					l.add(new ScriptCollide(o, ScriptCollide.COLLIDE_TYPE_FOOT));
				}else if(o.layer==mine.layer){
					if(mine.getCurrentFace()==o.getReverseFace() && (testFaceCollide(mine, o)))
						l.add(new ScriptCollide(o,gv.isPressZ?ScriptCollide.COLLIDE_TYPE_FACE_Z:ScriptCollide.COLLIDE_TYPE_FACE));
					else if((mine.mapx-1==o.mapx && mine.mapy==o.mapy) || (mine.mapx+1==o.mapx && mine.mapy==o.mapy) 
					|| (mine.mapx==o.mapx && mine.mapy+1==o.mapy) || (mine.mapx==o.mapx && mine.mapy-1==o.mapy))
						if((gv.isPressZ) && (testFaceCollide(mine, o)))
							l.add(new ScriptCollide(o,ScriptCollide.COLLIDE_TYPE_Z));
						else
							l.add(new ScriptCollide(o,ScriptCollide.COLLIDE_TYPE_NEAR));
				}
			}
		}
		return l;
	}
	
	private static boolean testFaceCollide(IRPGObject mine,IRPGObject o){
		return  (mine.getCurrentFace()==IRPGObject.FACE_L && mine.mapx-1==o.mapx && mine.mapy==o.mapy) ||
				(mine.getCurrentFace()==IRPGObject.FACE_R && mine.mapx+1==o.mapx && mine.mapy==o.mapy) ||
				(mine.getCurrentFace()==IRPGObject.FACE_U && mine.mapx==o.mapx && mine.mapy-1==o.mapy) ||
				(mine.getCurrentFace()==IRPGObject.FACE_D && mine.mapx==o.mapx && mine.mapy+1==o.mapy);
	}
	
	@Override
	public String toString() {
		return "Collide [bottom=" + bottom + ", left=" + left + ", right=" + right + ", top=" + top + "]";
	}
}
