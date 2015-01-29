package com.rpsg.rpg.view;



import box2dLight.RayHandler;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.*;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.game.script.Teleporter;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.object.rpgobj.Hero;
import com.rpsg.rpg.system.base.*;
import com.rpsg.rpg.system.base.TmxMapLoader;
import com.rpsg.rpg.system.base.TmxMapLoader.Parameters;
import com.rpsg.rpg.system.control.*;
import com.rpsg.rpg.utils.display.*;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.utils.game.Move;

public class GameView extends IView{
	
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
	@Override
	public void init() {
		inited=false;
		Logger.info("开始加载图形。");
		stage.clear();
		Parameters parameter = new Parameters();
		parameter.loadedCallback=(assetManager,fileName,type)->{
			map=ma.get(Setting.GAME_RES_MAP+global.map);
			render.setMap(map);
			render.setView(camera);
			ray.setWorld(world);
			Initialization.init(this);
			inited=true;
			Logger.info("图形加载完成。");
		};
		filename=Setting.GAME_RES_MAP+global.map;
		ma.load(filename, TiledMap.class ,parameter);
	}
	
	@Override
	public void dispose() {
		MapControler.dispose();
		Msg.dispose();
		Res.dispose();
//		map.dispose();
//		ma.unload(filename);
		if(null!=stackView){
			stackView.dispose();
			stackView=null;
			InputControler.currentIOMode=IOMode.MAP_INPUT_NORMAL;
		}
		System.gc();
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		if(!ma.update() || !inited)
			return;
		MapControler.draw(this);
		ColorUtil.draw(batch);
		DrawControl.draw(batch);
		ThreadPool.logic();
		ColorUtil.drawhover(batch);
		if(null!=stackView)
			stackView.draw(batch);
//		RadarUtil.draw();
	}

	@Override
	public void logic() {
		if(!ma.update() || !inited)
			return;
		MapControler.logic(this);
		//		stage.act();
		for(Actor i:stage.getActors())
			if(!(i instanceof Hero))
				i.act(0);
		HeroControler.act();
		MoveControler.logic(this);
		
		RadarUtil.draw();
		if(null!=stackView)
			stackView.logic();
//		System.out.println(HeroControler.getHeadHero().layer);
	}

	public void onkeyTyped(char character) {
		
	}
	
	public void onkeyDown(int keycode) {
		if(!ma.update() || !inited)
			return;
		InputControler.keyDown(keycode,this);
	}

	public void onkeyUp(int keycode) {
		if(!ma.update() || !inited)
			return;
		InputControler.keyUp(keycode,this);
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		InputControler.touchDown(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		InputControler.touchDragged(screenX, screenY, pointer);
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		InputControler.touchUp(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		InputControler.scrolled(amount);
		return false;
	}

}
