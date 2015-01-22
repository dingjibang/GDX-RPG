package com.rpsg.rpg.system.base;

import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class DefaultIView extends IView{
	public Stage stage;
	public void onkeyTyped(char character) {
		stage.keyTyped(character);
	}

	public void onkeyDown(int keyCode) {
		stage.keyDown(keyCode);
	}

	public void onkeyUp(int keyCode) {
		stage.keyUp(keyCode);
	}

	public void dispose() {
		stage.dispose();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return stage.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return stage.touchDragged(screenX, screenY, pointer);
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return stage.touchUp(screenX, screenY, pointer, button);
	}
	
	@Override
	public boolean scrolled(int amount) {
		return stage.scrolled(amount);
	}
}