package com.rpsg.rpg.system.control;

import com.rpsg.rpg.system.base.IOMode;
import com.rpsg.rpg.view.GameView;



public class InputControler{
	
	public static int currentIOMode=IOMode.MAP_INPUT_NORMAL; 
	
	public static boolean keyDown(int keycode,GameView gv) {
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
