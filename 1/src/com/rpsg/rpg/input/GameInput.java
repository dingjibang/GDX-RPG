package com.rpsg.rpg.input;

import com.badlogic.gdx.InputProcessor;
import com.rpsg.rpg.view.GameViews;

public class GameInput implements InputProcessor{

	@Override
	public boolean keyDown(int keycode) {
//		System.out.println(keycode);
		switch(GameViews.state){
		case GameViews.STATE_TITLE:{
			GameViews.titleview.keyDown(keycode);break;
		}
		case GameViews.STATE_GAME:{
			GameViews.gameview.keyDown(keycode);break;
		}
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(GameViews.state){
		case GameViews.STATE_GAME:{
			GameViews.gameview.keyUp(keycode);break;
		}
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
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
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
}
