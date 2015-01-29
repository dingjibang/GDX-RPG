package com.rpsg.rpg.view;



import box2dLight.RayHandler;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.*;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.object.rpgobj.Hero;
import com.rpsg.rpg.system.base.*;
import com.rpsg.rpg.system.base.TmxMapLoader;
import com.rpsg.rpg.system.base.TmxMapLoader.Parameters;
import com.rpsg.rpg.system.control.*;
import com.rpsg.rpg.utils.display.*;
import com.rpsg.rpg.utils.game.Logger;

public class GameView extends IView{
	
	public OrthogonalTiledMapRenderer render =  new OrthogonalTiledMapRenderer(null);
	public Stage stage = new Stage();
	public TiledMap map;
	public boolean inited=false;
	public Global global=GameViews.global;
	public OrthographicCamera camera = (OrthographicCamera) stage.getCamera();
	public RayHandler ray = new RayHandler(null);
	public World world = new World(new Vector2(0,0),true);
	private TmxMapLoader mapLoader=new TmxMapLoader(); 
	public StackView stackView;
	AssetManager ma;
	
	@Override
	public void init() {
		Logger.info("开始加载图形。");
		stage.clear();
		ma=new AssetManager();
		Parameters parameter = new Parameters();
		parameter.loadedCallback=(assetManager,fileName,type)->{
			System.out.println("back");
			map=ma.get(Setting.GAME_RES_MAP+global.map);
			Logger.info("图形加载完成。");
			LoadUtil.load=false;
			render.setMap(map);
			render.setView(camera);
			ray.setWorld(world);
			Initialization.init(this);
			inited=true;
		};
//		mapLoader.loadAsync(ma,, mapLoader.resolve(Setting.GAME_RES_MAP + global.map), parameter);
		ma.setLoader(TiledMap.class, mapLoader);
		ma.load(Setting.GAME_RES_MAP+global.map, TiledMap.class ,parameter);
	}
	
	@Override
	public void dispose() {
		MapControler.dispose();
		stage.dispose();
		render.dispose();
		Msg.dispose();
		Res.dispose();
		map.dispose();
		ray.dispose();
		world.dispose();
		if(null!=stackView){
			stackView.dispose();
			stackView=null;
			InputControler.currentIOMode=IOMode.MAP_INPUT_NORMAL;
		}
		System.gc();
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		if(!ma.update())
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
		if(!ma.update())
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
		InputControler.keyDown(keycode,this);
	}

	public void onkeyUp(int keycode) {
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
