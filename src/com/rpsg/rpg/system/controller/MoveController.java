package com.rpsg.rpg.system.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rpsg.rpg.io.Input;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.object.rpg.Collide;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.IRPGObject;
import com.rpsg.rpg.object.script.ScriptCollide;
import com.rpsg.rpg.view.GameView;

public class MoveController {
	
	public static int MAP_MAX_OUT_X=300;
	public static int MAP_MAX_OUT_Y=200;
	static boolean wu=false,wd=false,wl=false,wr=false;
	
	public static void up(){
		wu=true;
		wd=wl=wr=false;
	}
	public static void down(){
		wd=true;
		wu=wl=wr=false;
	}
	public static void left(){
		wl=true;
		wd=wu=wr=false;
	}
	public static void right(){
		wr=true;
		wd=wu=wl=false;
	}
	
	
	public static void logic(GameView gv){
		for(Actor a:gv.stage.getActors()){
			if(a instanceof IRPGObject && ((IRPGObject)a).enableCollide){
				IRPGObject o=(IRPGObject)a;
					o.collide.testCollide(o.mapx, o.mapy,  ((TiledMapTileLayer)MapController.layer.get(o.layer)),gv.stage.getActors(),o);
			}
		}
		for(ScriptCollide sc:Collide.testNPCCollide(gv, HeroController.getHeadHero(), gv.stage.getActors())){
			sc.toCollide();
		}
		HeroController.setWalkSpeed(Input.isPress(Keys.CONTROL_LEFT)?8:4);
		if(InputController.currentIOMode==IOMode.MAP_INPUT_NORMAL && HoverController.isEmpty()){
			if((Input.isPress(Keys.RIGHT) || Input.isPress(Keys.D) || wr) && HeroController.walked()){
				wr=false;
				HeroController.turn(Hero.FACE_R);
				HeroController.walk(1);
				HeroController.testWalk();
			}
			if((Input.isPress(Keys.LEFT) || Input.isPress(Keys.A) || wl) && HeroController.walked()){
				wl=false;
				HeroController.turn(Hero.FACE_L);
				HeroController.walk(1);
				HeroController.testWalk();
			}
			if((Input.isPress(Keys.UP) || Input.isPress(Keys.W) || wu) && HeroController.walked()){
				wu=false;
				HeroController.turn(Hero.FACE_U);
				HeroController.walk(1);
				HeroController.testWalk();
			}
			if((Input.isPress(Keys.DOWN) || Input.isPress(Keys.S) || wd) && HeroController.walked()){
				wd=false;
				HeroController.turn(Hero.FACE_D);
				HeroController.walk(1);
				HeroController.testWalk();
			}
		}
		int twidth=(int) (((TiledMapTileLayer)MapController.layer.get(0)).getWidth() * ((TiledMapTileLayer)MapController.layer.get(0)).getTileWidth());
		int theight=(int) (((TiledMapTileLayer)MapController.layer.get(0)).getHeight() * ((TiledMapTileLayer)MapController.layer.get(0)).getTileHeight());
		float herox=HeroController.getHeadHero().position.x+(HeroController.getHeadHero().getWidth()/2);
		float heroy=HeroController.getHeadHero().position.y+(HeroController.getHeadHero().getHeight()/2);
		if(herox>MAP_MAX_OUT_X && herox<(twidth)-MAP_MAX_OUT_X)
			gv.camera.position.x=herox;
		else
			if(!(herox>MAP_MAX_OUT_X))
				gv.camera.position.x=MAP_MAX_OUT_X;
			else
				gv.camera.position.x=(twidth)-MAP_MAX_OUT_X;
		if(heroy>MAP_MAX_OUT_Y && heroy<(theight)-MAP_MAX_OUT_Y)
			gv.camera.position.y=heroy;
		else
			if(!(heroy>MAP_MAX_OUT_Y))
				gv.camera.position.y=MAP_MAX_OUT_Y;
			else
				gv.camera.position.y=(theight)-MAP_MAX_OUT_Y;
		gv.camera.update();
	}
	
	
	public static boolean testCameraPos(GameView gv){
		float herox=HeroController.getHeadHero().position.x+(HeroController.getHeadHero().getWidth()/2);
		float heroy=HeroController.getHeadHero().position.y+(HeroController.getHeadHero().getHeight()/2);
		return !(herox>MAP_MAX_OUT_X && herox<(48*48)-MAP_MAX_OUT_X) && (heroy>MAP_MAX_OUT_Y && heroy<(48*48)-MAP_MAX_OUT_Y);
	}
	
	public static void keyUp(int keycode,GameView gv){
	}
	
	public static void keyDown(int keycode,GameView gv){
	}
}
