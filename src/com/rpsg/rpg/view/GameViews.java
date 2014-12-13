package com.rpsg.rpg.view;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.input.GameInput;
import com.rpsg.rpg.utils.GameUtil;

public class GameViews implements ApplicationListener {
	private static SpriteBatch batch;
	public static final int STATE_LOGO=0;
	public static final int STATE_LOAD=1;
	public static final int STATE_TITLE=2;
	public static final int STATE_GAME_LOAD=3;
	public static final int STATE_GAME=4;
	public static int state=0;
	public static boolean flag=true;
	//init view
	public static LogoView logoview;
	public static LoadView loadview;
	public static TitleView titleview;
	public static GameView gameview;
	@Override
	public void create() {		
		GameUtil.screen_width = Gdx.graphics.getWidth();
		GameUtil.screen_height = Gdx.graphics.getHeight();
		//start init
		//input
		Gdx.input.setInputProcessor(new GameInput());
		//view
		logoview = new LogoView();
		logoview.init();
		//other
		batch = new SpriteBatch();
	}

	@Override
	public void dispose() {
		batch.dispose();
		System.exit(0);
	}

	@Override
	public void render() {
		if(!flag) dispose();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		switch(state){
		case STATE_LOGO:{
			logoview.logic();
			if(logoview.played){
				loadview=new LoadView();
				loadview.init();
				state=STATE_LOAD;
				Gdx.app.postRunnable(new Runnable(){
					@Override
					public void run() {
						titleview=new TitleView();
						titleview.init();
					}});
			}
			logoview.draw(batch);
			break;
		}
		case STATE_LOAD:{
			loadview.logic();
			loadview.draw(batch);
			if (titleview.inited)
				state=STATE_TITLE;
			break;
		}
		case STATE_TITLE:{
			titleview.logic();
			titleview.draw(batch);
			break;
		}
		case STATE_GAME_LOAD:{
			loadview.logic();
			loadview.draw(batch);
		}
		case STATE_GAME:{
			gameview.logic();
			gameview.draw(batch);
		}
		}
		batch.end();
		batch.begin();
		GameUtil.debugDrawString(batch, "FPS:"+Gdx.graphics.getFramesPerSecond(), 11, GameUtil.screen_height-11);
		GameUtil.debugDrawString(batch, "FPS:"+Gdx.graphics.getFramesPerSecond(), 10, GameUtil.screen_height-10,Color.WHITE);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
}
