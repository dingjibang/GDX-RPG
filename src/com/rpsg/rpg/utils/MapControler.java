package com.rpsg.rpg.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rpsg.rpg.object.IRPGObject;
import com.rpsg.rpg.object.heros.HeaderHero;
import com.rpsg.rpg.view.GameView;

public class MapControler {
	
	public static int MAP_MAX_OUT_X=300;
	public static int MAP_MAX_OUT_Y=200;
	public static HeaderHero hero;
	public static HeaderHero npc;
	public static List<IRPGObject> drawlist=new ArrayList<IRPGObject>();
	
	public static void init(GameView gv){
		hero=new HeaderHero("/walk_marisa.png");
		npc=new HeaderHero("/walk_marisa.png");
		hero.generalPosition(10, 10, 1,gv.map).enableCollide=true;
		npc.generalPosition(20, 10, 1, gv.map).enableCollide=true;
		npc.waitWhenCollide=true;
		gv.stage.addActor(npc);
		gv.stage.addActor(hero);
	}
	
	public synchronized static void draw(SpriteBatch batch,GameView gv){
		int size=gv.map.layers.size();
		for(int i=0;i<size;i++){
			drawlist.clear();
			gv.render.render(gv.camera,new int[]{i});
			SpriteBatch sb=gv.stage.getSpriteBatch();
			sb.begin();								
			sb.setProjectionMatrix(gv.camera.combined);
			for(Actor a:gv.stage.getActors()){
				if(a instanceof IRPGObject){
					IRPGObject c = (IRPGObject)a;
					if(c.layer==i)
						drawlist.add(c);
				}
			}
			Collections.sort(drawlist);
			for(IRPGObject ir:drawlist)
				ir.draw(sb, 1f);
			sb.end();
		}
//		System.out.println(hero.getX()+" "+hero.getY()+" "+gv.camera.position.x+" "+gv.camera.position.y+" "+hero.mapx+" "+hero.mapy);
	}
	
	public static void logic(GameView gv){
	}
	

}
