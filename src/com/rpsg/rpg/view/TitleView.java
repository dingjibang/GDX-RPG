package com.rpsg.rpg.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.system.base.IView;
public class TitleView extends IView{
	public boolean inited=false;
	@Override
	public void init() {
		GameViews.gameview=new GameView();
		GameViews.gameview.init();
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
	}

	@Override
	public void onkeyTyped(char character) {
		
	}

	public void keyDown(int keycode) {
//		System.out.println(keycode);
	}
}
