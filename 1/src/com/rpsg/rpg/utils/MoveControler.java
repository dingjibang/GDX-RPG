package com.rpsg.rpg.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rpsg.rpg.object.Collide;
import com.rpsg.rpg.object.IRPGObject;
import com.rpsg.rpg.object.ScriptCollide;
import com.rpsg.rpg.object.heros.Hero;
import com.rpsg.rpg.object.heros.NPC;
import com.rpsg.rpg.view.GameView;

public class MoveControler {
	
	public static int MAP_MAX_OUT_X=300;
	public static int MAP_MAX_OUT_Y=200;
	
	public static void draw(SpriteBatch batch,GameView gv){
		
	}
	
	public static void logic(GameView gv){
		for(Actor a:gv.stage.getActors()){
			if(a instanceof IRPGObject && ((IRPGObject)a).enableCollide){
				IRPGObject o=(IRPGObject)a;
				int[][] tiles = gv.map.layers.get(o.layer).tiles;
				o.collide.testCollide(o.mapx, o.mapy, tiles,gv.stage.getActors(),o);
			}
		}
		for(ScriptCollide sc:Collide.testNPCCollide(gv, MapControler.hero, gv.stage.getActors())){
			sc.toCollide();
		}
		MapControler.hero.setWalkSpeed(gv.isPressCtrl?8:4);
		if(gv.isPressWalk_r && MapControler.hero.walked)
			MapControler.hero.turn(Hero.FACE_R).walk(1).testWalk();
		if(gv.isPressWalk_l && MapControler.hero.walked)
			MapControler.hero.turn(Hero.FACE_L).walk(1).testWalk();
		if(gv.isPressWalk_u && MapControler.hero.walked)
			MapControler.hero.turn(Hero.FACE_U).walk(1).testWalk();
		if(gv.isPressWalk_d && MapControler.hero.walked)
			MapControler.hero.turn(Hero.FACE_D).walk(1).testWalk();
		float herox=MapControler.hero.getX()+(MapControler.hero.getWidth()/2);
		float heroy=MapControler.hero.getY()+(MapControler.hero.getHeight()/2);
		if(herox>MAP_MAX_OUT_X && herox<(gv.map.width*gv.map.tileWidth)-MAP_MAX_OUT_X)
			gv.camera.position.x=herox;
		else
			if(!(herox>MAP_MAX_OUT_X))
				gv.camera.position.x=MAP_MAX_OUT_X;
			else
				gv.camera.position.x=(gv.map.width*gv.map.tileWidth)-MAP_MAX_OUT_X;
		if(heroy>MAP_MAX_OUT_Y && heroy<(gv.map.height*gv.map.tileHeight)-MAP_MAX_OUT_Y)
			gv.camera.position.y=heroy;
		else
			if(!(heroy>MAP_MAX_OUT_Y))
				gv.camera.position.y=MAP_MAX_OUT_Y;
			else
				gv.camera.position.y=(gv.map.height*gv.map.tileHeight)-MAP_MAX_OUT_Y;
		gv.camera.update();
	}
	
	
	public static boolean testCameraPos(GameView gv){
		float herox=MapControler.hero.getX()+(MapControler.hero.getWidth()/2);
		float heroy=MapControler.hero.getY()+(MapControler.hero.getHeight()/2);
		return !(herox>MAP_MAX_OUT_X && herox<(gv.map.width*gv.map.tileWidth)-MAP_MAX_OUT_X) && (heroy>MAP_MAX_OUT_Y && heroy<(gv.map.height*gv.map.tileHeight)-MAP_MAX_OUT_Y);
	}
	
	public static void keyUp(int keycode,GameView gv){
		if(keycode==21)
			gv.isPressWalk_l=false;
		if(keycode==22)
			gv.isPressWalk_r=false;
		if(keycode==19)
			gv.isPressWalk_u=false;
		if(keycode==20)
			gv.isPressWalk_d=false;
		if(keycode==129)
			gv.isPressCtrl=false;
		if(keycode==54)
			gv.isPressZ=false;
	}
	
	public static void keyDown(int keycode,GameView gv){
		if(keycode==21)
			gv.isPressWalk_l=true;
		if(keycode==22)
			gv.isPressWalk_r=true;
		if(keycode==19)
			gv.isPressWalk_u=true;
		if(keycode==20)
			gv.isPressWalk_d=true;
		if(keycode==129)
			gv.isPressCtrl=true;
		if(keycode==54)
			gv.isPressZ=true;
		if(keycode==51)
			MapControler.npc.turn(NPC.FACE_U).walk(1);
		if(keycode==47)
			MapControler.npc.turn(NPC.FACE_D).walk(1);
		if(keycode==29)
			MapControler.npc.turn(NPC.FACE_L).walk(1);
		if(keycode==32)
			MapControler.npc.turn(NPC.FACE_R).walk(1);
	}
}
