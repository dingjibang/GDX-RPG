package com.rpsg.rpg.view;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Bloom;
import com.bitfire.postprocessing.effects.CameraMotion;
import com.bitfire.postprocessing.effects.Vignette;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Input;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.HoverController;
import com.rpsg.rpg.utils.display.AlertUtil;
import com.rpsg.rpg.utils.display.GameViewRes;
import com.rpsg.rpg.utils.display.MouseUtil;
import com.rpsg.rpg.utils.display.PostUtil;
import com.rpsg.rpg.utils.display.RadarUtil;
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
	
	public static PostProcessor post;
	public static Bloom bloom;
	public static CameraMotion motion;
	
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
		batch = new SpriteBatch();
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
		
		post=new PostProcessor(false, true, true);
		bloom=new Bloom((int)(Gdx.graphics.getWidth() * 0.25f), (int)(Gdx.graphics.getHeight() * 0.25f));
		post.addEffect(bloom);
		
		Vignette v=new Vignette(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		v.setIntensity(0.5f);
		post.addEffect(v);
		
		motion =new CameraMotion(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		motion.setBlurPasses(10);
		motion.setDepthScale(0);
		post.addEffect(motion);
		
		PostUtil.init();
		
		Logger.info("Gdx-RPG引擎初始化成功。");
	}

	@Override
	public void dispose() {
		batch.dispose();
		System.exit(0);
	}

	@Override
	public void render() {
		Res.logic();
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
			loadview.draw();
		}
		case STATE_GAME:{
			gameview.logic();
			gameview.draw(batch);
			loadview.logic();
			loadview.draw();
			if(!GameViewRes.ma.update()){
				loadview.start();
			}else{
				loadview.stop();
			}
			if(!GameViewRes.ma2.update()){
				loadview.start();
			}else{
				loadview.stop();
			}
		}
		}
	
		HoverController.draw(batch);
		GameUtil.drawFPS(batch);
		TimeUtil.logic();
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
