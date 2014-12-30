package com.rpsg.rpg.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.rpsg.rpg.view.GameViews;

public class Input implements InputProcessor{

	@Override
	public boolean keyDown(int keycode) {
//		System.out.println(keycode);
		switch(GameViews.state){
		case GameViews.STATE_TITLE:{
			GameViews.titleview.onkeyDown(keycode);break;
		}
		case GameViews.STATE_GAME:{
			GameViews.gameview.onkeyDown(keycode);break;
		}
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(GameViews.state){
		case GameViews.STATE_GAME:{
			GameViews.gameview.onkeyUp(keycode);break;
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
		switch(GameViews.state){
		case GameViews.STATE_GAME:{
			GameViews.gameview.touchDown(screenX, screenY, pointer, button);
		}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		switch(GameViews.state){
		case GameViews.STATE_GAME:{
			GameViews.gameview.touchUp(screenX, screenY, pointer, button);
		}
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		switch(GameViews.state){
		case GameViews.STATE_GAME:{
			GameViews.gameview.touchDragged(screenX, screenY, pointer);
		}
		}
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
	
	public static boolean isPress(int keyCode){
		return Gdx.input.isKeyPressed(keyCode);
	}
	
}
