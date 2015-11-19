package com.rpsg.rpg.view;

import box2dLight.RayHandler;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Bloom;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Initialization;
import com.rpsg.rpg.system.controller.InputController;
import com.rpsg.rpg.system.controller.MoveController;
import com.rpsg.rpg.system.ui.StackView;
import com.rpsg.rpg.system.ui.View;
import com.rpsg.rpg.utils.display.ColorUtil;
import com.rpsg.rpg.utils.display.GameViewRes;
import com.rpsg.rpg.utils.display.PostUtil;
import com.rpsg.rpg.utils.game.Logger;

public class GameView extends View{
	
	public OrthoCachedTiledMapRenderer render ;//地图绘制器
	public Stage stage = GameViewRes.stage;//舞台
	public boolean inited=false;//是否加载完成的状态标志
	public Global global=RPG.global;//游戏存档
	public OrthographicCamera camera = GameViewRes.camera;//摄像机
	public RayHandler ray = GameViewRes.ray;//灯光
	public World world =GameViewRes.world;//box2d world（没用）
	public StackView stackView;//菜单视图
	AssetManager ma=GameViewRes.ma;//资源管理器
	String filename;//地图文件名（卸载地图纹理时候用到）
	TmxMapLoader.Parameters parameter;//地图加载完成的回调
	
	public PostProcessor post;//高清画质用
	public Bloom bloom;//模糊用
	public boolean renderable = true;
	
	@Override
	public View init() {
		inited=false;
		Logger.info("开始加载图形。");
		stage.clear();
		if(PostUtil.first)
			PostUtil.init();
		parameter = new TmxMapLoader.Parameters();
		parameter.loadedCallback= new AssetLoaderParameters.LoadedCallback() {
			public void finishedLoading(AssetManager assetManager, String fileName, Class type) {
				RPG.maps.map = ma.get(Setting.MAP + global.map);
				if(render == null)
					render=new OrthoCachedTiledMapRenderer(RPG.maps.map);
				render.setBlending(true);
				render.setView(camera);
				ray.setWorld(world);
				Initialization.init(GameView.this);
				inited = true;
				post = GameViews.post;
				bloom = GameViews.bloom;
				RPG.ctrl.weather.init(RPG.global.weather);
				Logger.info("图形加载完成。");
			}
		};
		filename=Setting.MAP+global.map;
		ma.load(filename, TiledMap.class ,parameter);
		return this;
	}
	
	@Override
	public void dispose() {
		RPG.maps.loader.dispose();
		
		if(!Setting.persistence.cacheResource){
			ma.unload(filename);
//			ma.clear(); FIXME 可能导致其他纹理也被卸载。。。
		}
		
		if(null!=stackView){
			stackView.dispose();
			stackView=null;
			InputController.currentIOMode=IOMode.MapInput.NORMAL;
		}
		GameViewRes.ray.removeAll();
		
		parameter.loadedCallback=null;
		parameter=null;
		
		render.dispose();

		System.gc();
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		if(!ma.update() || !inited){
			PostUtil.draw(false);
			return;
		}
		
		boolean postable = Setting.persistence.betterLight;
		
		if(postable)
			post.capture();
		
		RPG.maps.distant.draw((SpriteBatch)stage.getBatch(), this);
		
		if(renderable)
			RPG.maps.loader.draw(this);
		
		if(Setting.persistence.betterLight && RPG.maps.getProp().get("weather")==null && renderable)
			RPG.ctrl.weather.draw((SpriteBatch)batch);
		
		if(postable)
			post.render(true);
		
		ColorUtil.draw();
		
		if(renderable)
			PostUtil.draw(true);

		RPG.ctrl.draw.draw();
		
		if(null!=stackView)
			stackView.draw(batch);
		else
			RPG.ctrl.thread.logic();
	}

	@Override
	public void logic() {
		if(!ma.update() || !inited)
			return;
		if(null==stackView){
			RPG.maps.loader.logic(this);
			for(Actor i:stage.getActors())
				if(!(i instanceof Hero))
					i.act(0);
			RPG.ctrl.hero.act();
			MoveController.logic(this);
		}else{
			stackView.logic();
		}
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
