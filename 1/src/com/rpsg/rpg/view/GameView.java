package com.rpsg.rpg.view;

import shaders.DiffuseShader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.*;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.*;
import com.rpsg.rpg.system.base.TmxMapLoader.Parameters;
import com.rpsg.rpg.system.box2dLight.LightMap;
import com.rpsg.rpg.system.box2dLight.RayHandler;
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
	@Override
	public void init() {
		inited=false;
		Logger.info("��ʼ����ͼ�Ρ�");
		stage.clear();
		parameter = new Parameters();
		parameter.loadedCallback=(assetManager,fileName,type)->{
			map=ma.get(Setting.GAME_RES_MAP+global.map);
			render.setMap(map);
			render.setView(camera);
			ray.setWorld(world);
			Initialization.init(this);
			inited=true;
			Logger.info("ͼ�μ�����ɡ�");
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
		Res.dispose();
		System.gc();
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		if(!ma.update() || !inited)
			return;
		MapController.draw(this);
		
		ColorUtil.draw(batch);
		DrawController.draw(batch);
		ThreadPool.logic();
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

}
