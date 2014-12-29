package com.rpsg.rpg.system.control;

import com.badlogic.gdx.Input.Keys;
import com.rpsg.rpg.io.SaveLoad;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.system.base.Initialization;
import com.rpsg.rpg.utils.display.BlurUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.view.GameView;
import com.rpsg.rpg.view.GameViews;



public class InputControler{
	
	public static int currentIOMode=IOMode.MAP_INPUT_NORMAL; 
	
	public static boolean keyDown(int keycode,GameView gv) {
		if(keycode==Keys.R){
			GameViews.global=SaveLoad.load(0);
			Initialization.restartGame();
		}
		if(keycode==Keys.L){
			SaveLoad.save(0);
		}
		if(keycode==Keys.N){
			GameViews.global=new Global();
			Initialization.restartGame();
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
		if(keycode==Keys.ESCAPE){
			System.out.println("escape");
//			GameView.tmp=false;
		}
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
