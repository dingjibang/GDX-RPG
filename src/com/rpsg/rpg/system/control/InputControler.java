package com.rpsg.rpg.system.control;

import com.badlogic.gdx.Input.Keys;
import com.rpsg.rpg.io.SaveLoad;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.view.GameView;
import com.rpsg.rpg.view.GameViews;



public class InputControler{
	
	public static int currentIOMode=IOMode.MAP_INPUT_NORMAL; 
	
	public static boolean keyDown(int keycode,GameView gv) {
		if(keycode==Keys.R){
			GameViews.global=SaveLoad.load(0);
			GameViews.gameview.dispose();
			GameViews.gameview=new GameView();
			GameViews.gameview.init();
		}
		if(keycode==Keys.L){
			SaveLoad.save(0);
		}
		if(keycode==Keys.N){
			GameViews.global=new Global();
			GameViews.gameview.dispose();
			GameViews.gameview=new GameView();
			GameViews.gameview.init();
		}
		switch(currentIOMode){
		case IOMode.MAP_INPUT_NORMAL:{
			MoveControler.keyDown(keycode, gv);
			break;
		}
		case IOMode.MAP_INPUT_MESSAGING:{
			break;
		}
		}
		return false;
	}

	public static boolean keyUp(int keycode,GameView gv) {
		switch(currentIOMode){
		case IOMode.MAP_INPUT_NORMAL:{
			MoveControler.keyUp(keycode, gv);
			break;
		}
		case IOMode.MAP_INPUT_MESSAGING:{
			break;
		}
		}
		return false;
	}

	public static boolean keyTyped(char character) {
		return false;
	}

}
