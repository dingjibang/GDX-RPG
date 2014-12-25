package com.rpsg.rpg.system.control;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rpsg.rpg.io.Input;
import com.rpsg.rpg.object.rpgobj.Collide;
import com.rpsg.rpg.object.rpgobj.Hero;
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
				o.collide.testCollide(o.mapx, o.mapy,  ((TiledMapTileLayer)gv.map.getLayers().get(o.layer)),gv.stage.getActors(),o);
			}
		}
		for(ScriptCollide sc:Collide.testNPCCollide(gv, HeroControler.getHeadHero(), gv.stage.getActors())){
			sc.toCollide();
		}
		HeroControler.setWalkSpeed(Input.isPress(Keys.CONTROL_LEFT)?8:4);
		if(InputControler.currentIOMode==IOMode.MAP_INPUT_NORMAL){
			if(Input.isPress(Keys.RIGHT) && HeroControler.walked()){
				HeroControler.turn(Hero.FACE_R);
				HeroControler.walk(1);
				HeroControler.testWalk();
			}
			if(Input.isPress(Keys.LEFT) && HeroControler.walked()){
				HeroControler.turn(Hero.FACE_L);
				HeroControler.walk(1);
				HeroControler.testWalk();
			}
			if(Input.isPress(Keys.UP) && HeroControler.walked()){
				HeroControler.turn(Hero.FACE_U);
				HeroControler.walk(1);
				HeroControler.testWalk();
			}
			if(Input.isPress(Keys.DOWN) && HeroControler.walked()){
				HeroControler.turn(Hero.FACE_D);
				HeroControler.walk(1);
				HeroControler.testWalk();
			}
		}
		int twidth=(int) (((TiledMapTileLayer)gv.map.getLayers().get(0)).getWidth() * ((TiledMapTileLayer)gv.map.getLayers().get(0)).getTileWidth());
		int theight=(int) (((TiledMapTileLayer)gv.map.getLayers().get(0)).getHeight() * ((TiledMapTileLayer)gv.map.getLayers().get(0)).getTileHeight());
		float herox=HeroControler.getHeadHero().position.x+(HeroControler.getHeadHero().getWidth()/2);
		float heroy=HeroControler.getHeadHero().position.y+(HeroControler.getHeadHero().getHeight()/2);
		if(herox>MAP_MAX_OUT_X && herox<(twidth*48)-MAP_MAX_OUT_X)
			gv.camera.position.x=herox;
		else
			if(!(herox>MAP_MAX_OUT_X))
				gv.camera.position.x=MAP_MAX_OUT_X;
			else
				gv.camera.position.x=(twidth*48)-MAP_MAX_OUT_X;
		if(heroy>MAP_MAX_OUT_Y && heroy<(theight*48)-MAP_MAX_OUT_Y)
			gv.camera.position.y=heroy;
		else
			if(!(heroy>MAP_MAX_OUT_Y))
				gv.camera.position.y=MAP_MAX_OUT_Y;
			else
				gv.camera.position.y=(theight*48)-MAP_MAX_OUT_Y;
		gv.camera.update();
	}
	
	
	public static boolean testCameraPos(GameView gv){
		float herox=HeroControler.getHeadHero().position.x+(HeroControler.getHeadHero().getWidth()/2);
		float heroy=HeroControler.getHeadHero().position.y+(HeroControler.getHeadHero().getHeight()/2);
		return !(herox>MAP_MAX_OUT_X && herox<(48*48)-MAP_MAX_OUT_X) && (heroy>MAP_MAX_OUT_Y && heroy<(48*48)-MAP_MAX_OUT_Y);
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
