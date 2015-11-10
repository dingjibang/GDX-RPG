package com.rpsg.rpg.system.controller;

import com.badlogic.gdx.Input.Keys;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.io.SL;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.object.base.IOMode.MapInput;
import com.rpsg.rpg.system.base.Initialization;
import com.rpsg.rpg.utils.display.PostUtil;
import com.rpsg.rpg.utils.game.Path;
import com.rpsg.rpg.view.GameView;
import com.rpsg.rpg.view.GameViews;



public class InputController{
	
	public static MapInput currentIOMode=IOMode.MapInput.NORMAL; 
	static IOMode.MapInput tmpIO=null;
	
	public static void setTempIOMode(int IOMode){
		tmpIO=currentIOMode;
	}
	
	public static void resotryIOMode(){
		if(tmpIO!=null)
			currentIOMode=tmpIO;
		tmpIO=null;
	}
	public static boolean keyDown(int keycode,GameView gv) {
		if(keycode==Keys.L){
			SL.save(0);
		}
		switch(currentIOMode){
		case NORMAL:{
			if(keycode==Keys.ESCAPE || keycode==Keys.X){
				MenuController.createMenu();
				currentIOMode=IOMode.MapInput.MENU;
			}else{
					MoveController.keyDown(keycode, gv);
			}
			break;
		}
		case MENU:{
			MenuController.keyDown(keycode);
			break;
		}
		
		}
		return false;
	}

	public static boolean keyUp(int keycode,GameView gv) {
		switch(currentIOMode){
		case NORMAL:{
			MoveController.keyUp(keycode, gv);
			break;
		}
		case MENU:{
			MenuController.keyUp(keycode);
			break;
		}
		}
		return false;
	}
	
	public static boolean touchDown(int screenX, int screenY, int pointer, int button) {
		switch(currentIOMode){
		case MENU:{
			MenuController.touchDown(screenX, screenY, pointer, button);
			break;
		}
		case NORMAL:{
			if(GameViews.gameview.inited && GameViews.gameview.renderAble){
				if(!PostUtil.touchDown(screenX, screenY, pointer, button))//如果没点到屏幕的UI，则自动移动
					Path.click(screenX, screenY);
			}
			break;
		}
		}
		return false;
	}

	public static boolean keyTyped(char character) {
		switch(currentIOMode){
		case NORMAL:{
			
			PostUtil.keyTyped(character);
			break;
		}
		}
		return false;
	}

	public static boolean touchDragged(int screenX, int screenY, int pointer) {
		switch(currentIOMode){
		case MENU:{
			MenuController.touchDragged(screenX, screenY, pointer);
			break;
		}
		case NORMAL:{
			
			PostUtil.touchDragged( screenX,  screenY,  pointer);
			break;
		}
		}
		return false;
	}

	public static boolean touchUp(int screenX, int screenY, int pointer, int button) {
		switch(currentIOMode){
		case MENU:{
			MenuController.touchUp(screenX, screenY, pointer, button);
			break;
		}
		case NORMAL:{
			PostUtil.touchUp(screenX, screenY, pointer, button);
			break;
		}
		}
		return false;
	}

	public static void scrolled(int amount) {
		switch(currentIOMode){
		case MENU:{
			MenuController.scrolled(amount);
			break;
		}
		}
	}

	public static void mouseMoved(int x, int y) {
		switch(currentIOMode){
		case NORMAL:{
			PostUtil.mouseMoved(x, y);
			break;
		}
		}
	}

}
