package com.rpsg.rpg.system.control;

import com.badlogic.gdx.Input.Keys;
import com.rpsg.rpg.system.base.IOMode;
import com.rpsg.rpg.view.GameView;
import com.rpsg.rpg.view.GameViews;



public class InputControler{
	
	public static int currentIOMode=IOMode.MAP_INPUT_NORMAL; 
	
	public static boolean keyDown(int keycode,GameView gv) {
		if(keycode==Keys.R){
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
