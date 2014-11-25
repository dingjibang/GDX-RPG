package com.rpsg.rpg.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.object.heros.Hero;
import com.rpsg.rpg.view.GameView;

public class MoveControler {
	
	public static void draw(SpriteBatch batch,GameView gv){
		
	}
	
	public static void logic(GameView gv){
		if(gv.isPressWalk_r)
			gv.hero.turn(Hero.FACE_R).walk(1);
		gv.hero.setWalkSpeed(gv.isPressCtrl?8:6);
		if(gv.isPressWalk_l)
			gv.hero.turn(Hero.FACE_L).walk(1);
		if(gv.isPressWalk_u)
			gv.hero.turn(Hero.FACE_U).walk(1);
		if(gv.isPressWalk_d)
			gv.hero.turn(Hero.FACE_D).walk(1);
		float herox=gv.hero.getX()+(gv.hero.getWidth()/2);
		float heroy=gv.hero.getY()+(gv.hero.getHeight()/2);
		if(herox>300)
			gv.camera.position.x=herox;
		if(heroy>200)
		gv.camera.position.y=heroy;
		
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
		
	}
}
