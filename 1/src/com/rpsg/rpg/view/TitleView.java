package com.rpsg.rpg.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.system.ui.IView;
public class TitleView extends IView{
	public boolean inited=false;
	@Override
	public void init() {
		inited=true;
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void draw(SpriteBatch batch) {
	}

	@Override
	public void logic() {
		GameViews.state=GameViews.STATE_GAME;
		if(GameViews.gameview!=null)
			GameViews.gameview.dispose();
		GameViews.gameview=new GameView();
		GameViews.gameview.init();
	}

	@Override
	public void onkeyTyped(char character) {
		
	}

	@Override
	public void onkeyDown(int keyCode) {
		
	}

	@Override
	public void onkeyUp(int keyCode) {
		
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
	
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
