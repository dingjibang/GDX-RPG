package com.rpsg.rpg.object.rpgobj;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.rpsg.rpg.io.Input;
import com.rpsg.rpg.object.script.ScriptCollide;
import com.rpsg.rpg.view.GameView;


public class Collide implements Serializable {
	private static final long serialVersionUID = 1L;
	public boolean left;
	public boolean right;
	public boolean top;
	public boolean bottom;
	
	
	public static int getID(TiledMapTileLayer layer,int x,int y){
		try{
			return layer.getCell(x, y).getTile().getId();
		}catch(Exception e){
			return 0;
		}
	}
	
	public void testCollide(int x,int y,TiledMapTileLayer layer,Array<Actor> list,IRPGObject mine){
		int maph=layer.getHeight();
		left=!(x<=0 || getID(layer, x-1, maph-y-1)!=0);
		right=!(x>=layer.getWidth()-1 || getID(layer, x+1, maph-y-1)!=0);
		top=!(y<=0 || getID(layer, x, maph-(y))!=0);
		bottom=!(y>=layer.getHeight()-1 || getID(layer, x,maph-(y+2))!=0);
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
//				System.out.println(fullTest(mine, o)+",cable:"+o.collideAble);
				if(!o.collideZAble){
					if(!testZ(mine, o))
						o.collideZAble=true;
				}
				if(!o.collideFaceAble){
					if(!testFace(mine, o))
						o.collideFaceAble=true;
				}
				if(!o.collideFaceZAble){
					if(!testFaceZ(mine, o))
						o.collideFaceZAble=true;
				}
				if(!o.collideNearAble){
					if(!testNear(mine, o))
						o.collideNearAble=true;
				}
				if(!o.collideFootAble){
					if(!testFoot(mine, o))
						o.collideFootAble=true;
				}
				if(testFoot(mine, o) && o.collideFootAble){
					l.add(new ScriptCollide(o, ScriptCollide.COLLIDE_TYPE_FOOT));
					o.collideFootAble=false;
				}else if(testFaceZ(mine, o) && o.collideFaceZAble){
					l.add(new ScriptCollide(o,ScriptCollide.COLLIDE_TYPE_FACE_Z));
					o.collideFaceZAble=false;
				}else if(testFace(mine, o) && o.collideFaceAble){
					l.add(new ScriptCollide(o,ScriptCollide.COLLIDE_TYPE_FACE));
					o.collideFaceAble=false;
				}else if(testZ(mine, o) && o.collideZAble){
					l.add(new ScriptCollide(o,ScriptCollide.COLLIDE_TYPE_Z));
					o.collideZAble=false;
				}else if(testNear(mine, o) && o.collideNearAble){
					l.add(new ScriptCollide(o,ScriptCollide.COLLIDE_TYPE_NEAR));
					o.collideNearAble=false;
				}
			}
		}
		return l;
	}
	
	private static boolean testFoot(IRPGObject mine,NPC o){
		return mine.layer-1==o.layer && mine.mapx==o.mapx && mine.mapy==o.mapy;
	}
	
	private static boolean testLayer(IRPGObject mine,NPC o){
		return o.layer==mine.layer;
	}
	
	private static boolean testFace(IRPGObject mine,NPC o){
		return testLayer(mine, o) && (mine.getCurrentFace()==o.getReverseFace() && (testFaceCollide(mine, o)));
	}
	
	private static boolean testNear(IRPGObject mine,NPC o){
//		System.out.println(o.layer);
		return testLayer(mine, o) && ((
					(mine.mapx-1==o.mapx && mine.mapy==o.mapy) 
					|| (mine.mapx+1==o.mapx && mine.mapy==o.mapy)
					|| (mine.mapx==o.mapx && mine.mapy+1==o.mapy)
					|| (mine.mapx==o.mapx && mine.mapy-1==o.mapy) 
			   )); 
	}
	
	private static boolean testZ(IRPGObject mine,NPC o){
		return testLayer(mine, o) &&((
					(mine.mapx-1==o.mapx && mine.mapy==o.mapy) 
					|| (mine.mapx+1==o.mapx && mine.mapy==o.mapy)
					|| (mine.mapx==o.mapx && mine.mapy+1==o.mapy)
					|| (mine.mapx==o.mapx && mine.mapy-1==o.mapy) 
			   ) && 
			   ((Input.isPress(Keys.Z)) && (testFaceCollide(mine, o))));
	}
	
	private static boolean testFaceZ(IRPGObject mine,NPC o){
		return testLayer(mine, o)&& (Input.isPress(Keys.Z) && (mine.getCurrentFace()==o.getReverseFace() && (testFaceCollide(mine, o))));
	}
	
//	private static boolean fullTest(IRPGObject mine,NPC o){
////		System.out.print("testFoot:"+testFoot(mine, o));
////		System.out.print("   testLay:"+testFoot(mine, o));
//		return testFoot(mine, o)==false 
//			&& testFace(mine, o)==false
//			&& testZ(mine, o)==false
//			&& testNear(mine, o)==false
//			&& testFaceZ(mine, o)==false;
//	}
	
//	private static void add(ScriptCollide sc,NPC npc,int type){
//		l.add(new ScriptCollide(npc, type));
//	}
	
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
