package com.rpsg.rpg.view;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.io.Input;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.utils.display.AlertUtil;
import com.rpsg.rpg.utils.display.MouseUtil;
import com.rpsg.rpg.utils.display.TipUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;

public class GameViews implements ApplicationListener {
	public static SpriteBatch batch;
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
	public static Global global;
	
	@Override
	public void create() {
		
		GameUtil.screen_width = Gdx.graphics.getWidth();
		GameUtil.screen_height = Gdx.graphics.getHeight();
		//start init
		//input
		Gdx.input.setInputProcessor(new Input());
		//view
		logoview = new LogoView();
		logoview.init();
		//other
		batch = new SpriteBatch();
		global=new Global();
		MouseUtil.init();
		TipUtil.init();
		AlertUtil.init();
		Logger.info("Gdx-RPG引擎初始化成功。");
	}

	@Override
	public void dispose() {
		batch.dispose();
		System.exit(0);
	}

	@Override
	public void render() {
		if(!flag) dispose();
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		switch(state){
		case STATE_LOGO:{
			logoview.logic();
			if(logoview.played){
				logoview.dispose();
				loadview=new LoadView();
				loadview.init();
				state=STATE_LOAD;
				Gdx.app.postRunnable(()->{
						titleview=new TitleView();
						titleview.init();
				});
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
		GameUtil.drawFPS(batch);
		AlertUtil.draw(batch);
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
