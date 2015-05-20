package com.rpsg.rpg.view;

import box2dLight.RayHandler;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.*;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Bloom;
import com.bitfire.postprocessing.effects.CameraMotion;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Input;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.*;
import com.rpsg.rpg.system.base.TmxMapLoader.Parameters;
import com.rpsg.rpg.system.controller.*;
import com.rpsg.rpg.system.ui.View;
import com.rpsg.rpg.system.ui.StackView;
import com.rpsg.rpg.utils.display.*;
import com.rpsg.rpg.utils.game.Logger;

public class GameView extends View{
	
	public OrthogonalTiledMapRenderer render =  new OrthogonalTiledMapRenderer(null);
	public Stage stage = GameViewRes.stage;
	public TiledMap map;
	public boolean inited=false;
	public Global global=GameViews.global;
	public OrthographicCamera camera = GameViewRes.camera;
	public RayHandler ray = GameViewRes.ray;
	public World world = GameViewRes.world;
	public StackView stackView;
	AssetManager ma=GameViewRes.ma;
	String filename;
	Parameters parameter;
	
	public PostProcessor post;
	public Bloom bloom;
	public CameraMotion motion;
	
	@Override
	public void init() {
		inited=false;
		Logger.info("开始加载图形。");
		stage.clear();
		parameter = new Parameters();
		parameter.loadedCallback=(assetManager,fileName,type)->{
			map=ma.get(Setting.GAME_RES_MAP+global.map);
			render.setMap(map);
			render.setView(camera);
			ray.setWorld(world);
			Initialization.init(this);
			inited=true;
			
			post=GameViews.post;
			bloom=GameViews.bloom;
			motion=GameViews.motion;
			WeatherUtil.init(GameViews.global.weather);
			Logger.info("图形加载完成。");
		};
		filename=Setting.GAME_RES_MAP+global.map;
		ma.load(filename, TiledMap.class ,parameter);
	}
	
	@Override
	public void dispose() {
		GameViews.loadview.reinit();
		MapController.dispose();
		Msg.dispose();
		if(!Setting.persistence.cacheResource){
			map.dispose();
			ma.unload(filename);
		}
		if(null!=stackView){
			stackView.dispose();
			stackView=null;
			InputController.currentIOMode=IOMode.MAP_INPUT_NORMAL;
		}
		GameViewRes.ray.removeAll();
		parameter.loadedCallback=null;
		parameter=null;
		System.gc();
	}
	
	Matrix4 lastView;
	@Override
	public void draw(SpriteBatch batch) {
		if(!ma.update() || !inited)
			return;
		
		if(lastView==null)
			lastView=camera.view.cpy();
		
		motion.setMatrices(camera.invProjectionView, lastView.cpy(), camera.view);
		lastView=camera.view.cpy();
		
		motion.setBlurScale(Input.isPress(Keys.CONTROL_LEFT)?0.0035f:0);
		
		boolean menuEnable=(null==stackView || stackView.viewStack.size()==0);
		boolean postEnable=Setting.persistence.betterLight && menuEnable;
		
		if(postEnable)
			post.capture();
		
		DistantController.draw((SpriteBatch)stage.getBatch(),this);
		
		MapController.draw(this);
		
		if(Setting.persistence.betterLight && map.getProperties().get("weather")==null)
			WeatherUtil.draw((SpriteBatch) PostUtil.stage.getBatch());
		
		if(postEnable)
			post.render(true);
		
		
		PostUtil.draw(post,menuEnable);
		
		ColorUtil.draw(batch);
		
		DrawController.draw(batch);
		ThreadPool.logic();
		
		if(postEnable)
			ColorUtil.drawhover(batch);
		
		if(null!=stackView)
			stackView.draw(batch);
		
//		batch.end();
//		ShaderProgram shader = DiffuseShader.createShadowShader();
//		shader.begin();
//		shader.end();
//		shader.dispose();
//		batch.begin();
//		Pixmap pbg=ScreenUtil.getScreenshot(0, 0, GameUtil.getScreenWidth(), GameUtil.getScreenHeight(), false);
//		Image i=new Image(new TextureRegion(new Texture(pbg),0,GameUtil.getScreenHeight(),GameUtil.getScreenWidth(),-GameUtil.getScreenHeight()));
//		i.setSize(320, 180);
//		i.draw(batch);
//		batch.end();
//		i.getTexture().dispose();
//		pbg.dispose();
//		batch.begin();
		
//		RadarUtil.draw();
	}

	@Override
	public void logic() {
		if(!ma.update() || !inited)
			return;
		MapController.logic(this);
		//		stage.act();
		for(Actor i:stage.getActors())
			if(!(i instanceof Hero))
				i.act(0);
		HeroController.act();
		MoveController.logic(this);
		
		if(null!=stackView)
			stackView.logic();
//		System.out.println(HeroControler.getHeadHero().layer);
	}

	public void onkeyTyped(char character) {
		if(!ma.update() || !inited)
			return;
		InputController.keyTyped(character);
	}
	
	public void onkeyDown(int keycode) {
		if(!ma.update() || !inited)
			return;
		InputController.keyDown(keycode,this);
	}

	public void onkeyUp(int keycode) {
		if(!ma.update() || !inited)
			return;
		InputController.keyUp(keycode,this);
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		InputController.touchDown(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		InputController.touchDragged(screenX, screenY, pointer);
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		InputController.touchUp(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		InputController.scrolled(amount);
		return false;
	}
	
	public void mouseMoved(int x,int y){
		InputController.mouseMoved(x,y);
	};


}
