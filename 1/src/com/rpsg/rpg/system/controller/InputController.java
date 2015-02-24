package com.rpsg.rpg.system.controller;

import com.badlogic.gdx.Input.Keys;
import com.rpsg.rpg.io.SaveLoad;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.system.base.Initialization;
import com.rpsg.rpg.view.GameView;
import com.rpsg.rpg.view.GameViews;



public class InputController{
	
	public static int currentIOMode=IOMode.MAP_INPUT_NORMAL; 
	static int tmpIO=-1;
	
	public static void setTempIOMode(int IOMode){
		tmpIO=currentIOMode;
	}
	
	public static void resotryIOMode(){
		if(tmpIO!=-1)
			currentIOMode=tmpIO;
		tmpIO=-1;
	}
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
		HeroController.reinit();
		switch(currentIOMode){
		case IOMode.MAP_INPUT_NORMAL:{
			if(keycode==Keys.ESCAPE || keycode==Keys.X){
				MenuController.createMenu();
				currentIOMode=IOMode.MAP_INPUT_MENU;
			}else{
				MoveController.keyDown(keycode, gv);
			}
			break;
		}
		case IOMode.MAP_INPUT_MENU:{
			MenuController.keyDown(keycode);
			break;
		}
		
		}
		return false;
	}

	public static boolean keyUp(int keycode,GameView gv) {
		switch(currentIOMode){
		case IOMode.MAP_INPUT_NORMAL:{
			MoveController.keyUp(keycode, gv);
			break;
		}
		case IOMode.MAP_INPUT_MENU:{
			MenuController.keyUp(keycode);
			break;
		}
		}
		return false;
	}
	
	public static boolean touchDown(int screenX, int screenY, int pointer, int button) {
		switch(currentIOMode){
		case IOMode.MAP_INPUT_MENU:{
			MenuController.touchDown(screenX, screenY, pointer, button);
			break;
		}
		}
		return false;
	}

	public static boolean keyTyped(char character) {
		return false;
	}

	public static boolean touchDragged(int screenX, int screenY, int pointer) {
		switch(currentIOMode){
		case IOMode.MAP_INPUT_MENU:{
			MenuController.touchDragged(screenX, screenY, pointer);
			break;
		}
		}
		return false;
	}

	public static boolean touchUp(int screenX, int screenY, int pointer, int button) {
		switch(currentIOMode){
		case IOMode.MAP_INPUT_MENU:{
			MenuController.touchUp(screenX, screenY, pointer, button);
			break;
		}
		}
		return false;
	}

	public static void scrolled(int amount) {
		switch(currentIOMode){
		case IOMode.MAP_INPUT_MENU:{
			MenuController.scrolled(amount);
			break;
		}
		}
	}

}
