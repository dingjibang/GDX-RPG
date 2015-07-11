package com.rpsg.rpg.system.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rpsg.rpg.io.Input;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.object.rpg.Collide;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.IRPGObject;
import com.rpsg.rpg.object.script.ScriptCollide;
import com.rpsg.rpg.view.GameView;

public class MoveController {

	public static int MAP_MAX_OUT_X=512;
	public static int MAP_MAX_OUT_Y=288;
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
	
	static int xoff,yoff,bufx,bufy;
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
		//core
		//twidth theight 是地图的总宽度和告诉
		int twidth=(int) (((TiledMapTileLayer)MapController.layer.get(0)).getWidth() * ((TiledMapTileLayer)MapController.layer.get(0)).getTileWidth());
		int theight=(int) (((TiledMapTileLayer)MapController.layer.get(0)).getHeight() * ((TiledMapTileLayer)MapController.layer.get(0)).getTileHeight());
		
		//这两个坐标herox heroy 来确定了hero的位置
		float herox=HeroController.getHeadHero().position.x+(HeroController.getHeadHero().getWidth()/2);
		float heroy=HeroController.getHeadHero().position.y+(HeroController.getHeadHero().getHeight()/2);
		
		if(herox>MAP_MAX_OUT_X && herox<(twidth)-MAP_MAX_OUT_X)//如果角色没有到达地图的x边界，那么相机的x中央点就设定为hero的x位置
			gv.camera.position.x=herox;
		else
			if(!(herox>MAP_MAX_OUT_X))//
				gv.camera.position.x=MAP_MAX_OUT_X;
			else
				gv.camera.position.x=(twidth)-MAP_MAX_OUT_X;
		if(heroy>MAP_MAX_OUT_Y && heroy<(theight)-MAP_MAX_OUT_Y)//同理，设定到y
			gv.camera.position.y=heroy;
		else
			if(!(heroy>MAP_MAX_OUT_Y))
				gv.camera.position.y=MAP_MAX_OUT_Y;
			else
				gv.camera.position.y=(theight)-MAP_MAX_OUT_Y;
		
		Vector3 pos = gv.camera.position;
		int speed=5;
		if(bufx!=xoff)
			if((xoff >0 && bufx+speed<xoff) || (xoff <0 && bufx-speed>xoff))
				bufx+=speed*(xoff<bufx?-1:1);//camera move speed
			else
				bufx=xoff;
		if(bufy!=yoff)
			if((yoff >0 && bufy+speed<yoff) || (yoff <0 && bufy-speed>yoff))
				bufy+=speed*(yoff<bufy?-1:1);//camera move speed
			else
				bufy=yoff;
//		System.out.println("x:"+xoff+",y:"+yoff+",bx:"+bufx+"by:"+bufy);
		pos.x+=bufx;
		pos.y+=bufy;
		gv.camera.update();
	}
	
	
	public static void setCameraPosition(int x,int y){
		xoff=x;
		yoff=y;
	}
	
	static boolean isMoving=false;
	public static boolean isCameraMoving(){
		return isMoving;
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
