package com.rpsg.rpg.utils;

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
	
	public static void init(GameView gv){
		hero=new HeaderHero("/walk_marisa.png");
		npc=new HeaderHero("/walk_marisa.png");
		hero.generalPosition(10, 10, 1,gv.map).enableCollide=true;
		npc.generalPosition(20, 10, 1, gv.map).enableCollide=true;
		gv.stage.addActor(npc);
		gv.stage.addActor(hero);
	}
	
	public static void draw(SpriteBatch batch,GameView gv){
		int size=gv.map.layers.size();
		for(int i=0;i<size;i++){
			gv.render.render(gv.camera,new int[]{i});
			SpriteBatch sb=gv.stage.getSpriteBatch();
			sb.begin();
			sb.setProjectionMatrix(gv.camera.combined);
			for(Actor a:gv.stage.getActors()){
				if(a instanceof IRPGObject){
					IRPGObject c = (IRPGObject)a;
					if(c.layer==i)
						c.draw(sb,1f);
				}
			}
			sb.end();
		}
//		System.out.println(hero.getX()+" "+hero.getY()+" "+gv.camera.position.x+" "+gv.camera.position.y+" "+hero.mapx+" "+hero.mapy);
	}
	
	public static void logic(GameView gv){
		
	}
	

}
