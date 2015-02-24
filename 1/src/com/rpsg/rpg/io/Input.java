package com.rpsg.rpg.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.system.controller.HoverController;
import com.rpsg.rpg.utils.display.MouseUtil;
import com.rpsg.rpg.view.GameViews;

public class Input implements InputProcessor{
	public static int state=IOMode.GAME_INPUT_NORMAL;
	@Override
	public boolean keyDown(int keycode) {
		if(state==IOMode.GAME_INPUT_HOVER)
			return HoverController.keyDown(keycode);
		switch(GameViews.state){
		case GameViews.STATE_TITLE:{
			GameViews.titleview.onkeyDown(keycode);break;
		}
		case GameViews.STATE_GAME:{
			if(keycode==Keys.P){
				MouseUtil.MoveTo(300, 20);
			}
			GameViews.gameview.onkeyDown(keycode);break;
		}
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(state==IOMode.GAME_INPUT_HOVER)
			return HoverController.keyUp(keycode);
		switch(GameViews.state){
		case GameViews.STATE_GAME:{
			GameViews.gameview.onkeyUp(keycode);break;
		}
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		if(state==IOMode.GAME_INPUT_HOVER)
			return HoverController.keyTyped(character);
		switch(GameViews.state){
		case GameViews.STATE_LOGO:{
			GameViews.logoview.onkeyTyped(character);
			break;
		}
		case GameViews.STATE_GAME:{
			break;
		}
		}
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(state==IOMode.GAME_INPUT_HOVER)
			return HoverController.touchDown(screenX, screenY, pointer, button);
		switch(GameViews.state){
		case GameViews.STATE_GAME:{
			GameViews.gameview.touchDown(screenX, screenY, pointer, button);
		}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(state==IOMode.GAME_INPUT_HOVER)
			return HoverController.touchUp(screenX, screenY, pointer, button);
		switch(GameViews.state){
		case GameViews.STATE_GAME:{
			GameViews.gameview.touchUp(screenX, screenY, pointer, button);
		}
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(state==IOMode.GAME_INPUT_HOVER)
			return HoverController.touchDragged(screenX, screenY, pointer);
		switch(GameViews.state){
		case GameViews.STATE_GAME:{
			GameViews.gameview.touchDragged(screenX, screenY, pointer);
		}
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		if(state==IOMode.GAME_INPUT_HOVER)
			return HoverController.mouseMoved(screenX, screenY);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		if(state==IOMode.GAME_INPUT_HOVER)
			return HoverController.scrolled(amount);
		switch(GameViews.state){
		case GameViews.STATE_GAME:{
			GameViews.gameview.scrolled(amount);
		}
		}
		return false;
	}
	
	public static boolean isPress(int keyCode){
		return Gdx.input.isKeyPressed(keyCode);
	}
	
}
