package com.rpsg.rpg.view;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.FileIO;
import com.rpsg.rpg.io.Input;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.object.base.Persistence;
import com.rpsg.rpg.system.controller.HoverController;
import com.rpsg.rpg.system.controller.MenuController;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.display.AlertUtil;
import com.rpsg.rpg.utils.display.GameViewRes;
import com.rpsg.rpg.utils.display.MouseUtil;
import com.rpsg.rpg.utils.display.RadarUtil;
import com.rpsg.rpg.utils.display.ScreenUtil;
import com.rpsg.rpg.utils.display.SelectUtil;
import com.rpsg.rpg.utils.display.TipUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.utils.game.TimeUtil;

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
	
	public static SelectUtil selectUtil;
	public static Input input;
	@Override
	public void create() {
		
		GameUtil.screen_width = Gdx.graphics.getWidth();
		GameUtil.screen_height = Gdx.graphics.getHeight();
		//start init
		//input
		input =new Input();
		Gdx.input.setInputProcessor(input);
		//view
		logoview = new LogoView();
		logoview.init();
		//other
		try{
			batch = new SpriteBatch();
		}catch(Exception e){
			Logger.faild("OpenGL画笔初始化失败。",e);
			Setting.persistence.useGL3=false;
			Setting.persistence.errorMessage="无法启用OpenGL3.0接口，程序已恢复为兼容模式。";
			Persistence.save();
			Logger.info("在程序崩溃前成功强制设置为兼容模式，如果程序仍然出现问题，请将错误信息提交给作者。");
			System.exit(0);
		}
		global=new Global();
		MouseUtil.init();
		TipUtil.init();
		AlertUtil.init();
		if(Setting.persistence.errorMessage!=null && Setting.persistence.errorMessage.length()!=0){
			AlertUtil.add(Setting.persistence.errorMessage, AlertUtil.Red);
			Setting.persistence.errorMessage="";
		}
		RadarUtil.init(1, new TextureRegion(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_MENU_STATUS+"propbg.png"))), 1);
		TimeUtil.init();
		selectUtil=new SelectUtil();
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
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
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
			if(!GameViewRes.ma.update(Gdx.graphics.getFramesPerSecond())){
				loadview.logic();
				loadview.draw(batch);
			}
		}
		}
		HoverController.draw(batch);
		GameUtil.drawFPS(batch);
		TimeUtil.logic();
		AlertUtil.draw(batch);
		
		batch.end();
		
//		batch.begin();
//		Pixmap pbg=ScreenUtil.getScreenshot(0, 0, GameUtil.getScreenWidth(), GameUtil.getScreenHeight(), false);
//		Image i=new Image(new TextureRegion(new Texture(pbg),0,GameUtil.getScreenHeight(),GameUtil.getScreenWidth(),-GameUtil.getScreenHeight()));
//		i.setSize(320, 180);
//		i.draw(batch);
//		batch.end();
//		i.getTexture().dispose();
//		pbg.dispose();

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
