package com.rpsg.rpg.system.control;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rpsg.rpg.io.Input;
import com.rpsg.rpg.object.rpgobj.Collide;
import com.rpsg.rpg.object.rpgobj.HeroObj;
import com.rpsg.rpg.object.rpgobj.IRPGObject;
import com.rpsg.rpg.object.rpgobj.NPC;
import com.rpsg.rpg.object.script.ScriptCollide;
import com.rpsg.rpg.system.base.IOMode;
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
		MapControler.hero.setWalkSpeed(Input.isPress(Keys.CONTROL_LEFT)?8:4);
		if(InputControler.currentIOMode==IOMode.MAP_INPUT_NORMAL){
			if(Input.isPress(Keys.RIGHT) && MapControler.hero.walked)
				MapControler.hero.turn(HeroObj.FACE_R).walk(1).testWalk();
			if(Input.isPress(Keys.LEFT) && MapControler.hero.walked)
				MapControler.hero.turn(HeroObj.FACE_L).walk(1).testWalk();
			if(Input.isPress(Keys.UP) && MapControler.hero.walked)
				MapControler.hero.turn(HeroObj.FACE_U).walk(1).testWalk();
			if(Input.isPress(Keys.DOWN) && MapControler.hero.walked)
				MapControler.hero.turn(HeroObj.FACE_D).walk(1).testWalk();
		}
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
	}
	
	public static void keyDown(int keycode,GameView gv){
		if(keycode==Keys.W)
			MapControler.npc.turn(NPC.FACE_U).walk(1);
		if(keycode==Keys.S)
			MapControler.npc.turn(NPC.FACE_D).walk(1);
		if(keycode==Keys.A)
			MapControler.npc.turn(NPC.FACE_L).walk(1);
		if(keycode==Keys.D)
			MapControler.npc.turn(NPC.FACE_R).walk(1);
	}
}
