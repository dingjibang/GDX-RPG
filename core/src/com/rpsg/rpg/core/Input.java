package com.rpsg.rpg.core;

import java.util.List;

import com.badlogic.gdx.InputProcessor;
import com.rpsg.rpg.ui.view.View;

/**
 * GDX-RPG 输入管理器
 * <br>
 * 每次接收到从LibGDX获得的输入回调，他都对{@link Views#views}相应输入进行冒泡
 */
public class Input implements InputProcessor{
	
	List<View> views;
	
	public Input(List<View> views) {
		this.views = views;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		for(View view : views)
			if(!view.keyDown(keycode))
				return false;
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		for(View view : views)
			if(!view.keyUp(keycode))
				return false;
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		for(View view : views)
			if(!view.keyTyped(character))
				return false;
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		for(View view : views)
			if(!view.touchDown(screenX, screenY, pointer, button))
				return false;
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		for(View view : views)
			if(!view.touchUp(screenX, screenY, pointer, button))
				return false;
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		for(View view : views)
			if(!view.touchDragged(screenX, screenY, pointer))
				return false;
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		for(View view : views)
			if(!view.mouseMoved(screenX, screenY))
				return false;
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		for(View view : views)
			if(!view.scrolled(amount))
				return false;
		return true;
	}

}
