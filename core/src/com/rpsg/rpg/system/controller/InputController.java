package com.rpsg.rpg.system.controller;

import com.badlogic.gdx.Input.Keys;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.object.base.IOMode.MapInput;
import com.rpsg.rpg.utils.display.PostUtil;
import com.rpsg.rpg.utils.game.Path;
import com.rpsg.rpg.view.GameView;
import com.rpsg.rpg.view.GameViews;
import com.rpsg.rpg.view.hover.LoadView;



public class InputController{
	
	public static MapInput currentIOMode=IOMode.MapInput.normal; 
	static IOMode.MapInput tmpIO=null;
	
	public static void saveIOMode(MapInput IOMode){
		tmpIO = currentIOMode;
		currentIOMode = IOMode;
	}
	
	public static void loadIOMode(){
		if(tmpIO!=null)
			currentIOMode = tmpIO;
		tmpIO = null;
	}
	
	public static boolean keyDown(int keycode,GameView gv) {
		switch(currentIOMode){
		case normal:{
			if(keycode == Keys.R) {
				RPG.popup.add(LoadView.class);
			}
			if(keycode==Keys.ESCAPE || keycode==Keys.X){
				MenuController.createMenu();
				currentIOMode=IOMode.MapInput.menu;
			}else{
					MoveController.keyDown(keycode, gv);
			}
			break;
		}
		case menu:{
			MenuController.keyDown(keycode);
			break;
		}
		case battle:{
			GameViews.gameview.battleView.onkeyDown(keycode);
			break;
		}
		default:
			break;
		
		}
		return false;
	}

	public static boolean keyUp(int keycode,GameView gv) {
		switch(currentIOMode){
		case normal:{
			MoveController.keyUp(keycode, gv);
			break;
		}
		case menu:{
			MenuController.keyUp(keycode);
			break;
		}
		case battle:{
			GameViews.gameview.battleView.onkeyUp(keycode);
			break;
		}
		default:
			break;
		}
		return false;
	}
	
	public static boolean touchDown(int screenX, int screenY, int pointer, int button) {
		switch(currentIOMode){
		case menu:{
			MenuController.touchDown(screenX, screenY, pointer, button);
			break;
		}
		case normal:{
			if(GameViews.gameview.inited && GameViews.gameview.renderable){
				if((!PostUtil.touchDown(screenX, screenY, pointer, button)) && Setting.persistence.pathFind)//如果没点到屏幕的UI，则自动移动
					Path.click(screenX, screenY);
			}
			break;
		}
		case battle:{
			GameViews.gameview.battleView.touchDown(screenX, screenY, pointer, button);
			break;
		}
		default:
			break;
		}
		return false;
	}

	public static boolean keyTyped(char character) {
		switch(currentIOMode){
		case normal:{
			
			PostUtil.keyTyped(character);
			break;
		}
		case battle:{
			GameViews.gameview.battleView.onkeyTyped(character);;
			break;
		}
		default:
			break;
		}
		return false;
	}

	public static boolean touchDragged(int screenX, int screenY, int pointer) {
		switch(currentIOMode){
		case menu:{
			MenuController.touchDragged(screenX, screenY, pointer);
			break;
		}
		case normal:{
			if(GameViews.gameview.inited && GameViews.gameview.renderable){
				if(!PostUtil.touchDragged( screenX,  screenY,  pointer))
					Path.click(screenX, screenY);
			}
			break;
		}
		case battle:{
			GameViews.gameview.battleView.touchDragged(screenX, screenY, pointer);
			break;
		}
		default:
			break;
		}
		return false;
	}

	public static boolean touchUp(int screenX, int screenY, int pointer, int button) {
		switch(currentIOMode){
		case menu:{
			MenuController.touchUp(screenX, screenY, pointer, button);
			break;
		}
		case normal:{
			PostUtil.touchUp(screenX, screenY, pointer, button);
			break;
		}
		case battle:{
			GameViews.gameview.battleView.touchUp(screenX, screenY, pointer, button);
			break;
		}
		default:
			break;
		}
		return false;
	}

	public static void scrolled(int amount) {
		switch(currentIOMode){
		case menu:{
			MenuController.scrolled(amount);
			break;
		}
		case battle:{
			GameViews.gameview.battleView.scrolled(amount);
			break;
		}
		default:
			break;
		}
	}

	public static void mouseMoved(int x, int y) {
		switch(currentIOMode){
		case normal:{
			PostUtil.mouseMoved(x, y);
			break;
		}
		case battle:{
			GameViews.gameview.battleView.mouseMoved(x, y);
			break;
		}
		default:
			break;
		}
	}

}
