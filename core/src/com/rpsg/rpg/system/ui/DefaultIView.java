package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rpsg.rpg.view.GameView;
import com.rpsg.rpg.view.GameViews;
import com.rpsg.rpg.view.menu.MenuView;

public abstract class DefaultIView extends View{
	public Stage stage;
	public MenuView parent=(MenuView) GameViews.gameview.stackView;
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