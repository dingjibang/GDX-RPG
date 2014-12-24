package com.rpsg.rpg.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.object.rpgobj.Hero;
import com.rpsg.rpg.system.base.IView;
import com.rpsg.rpg.system.base.Initialization;
import com.rpsg.rpg.system.base.ResourcePool;
import com.rpsg.rpg.system.base.ThreadPool;
import com.rpsg.rpg.system.base.TileAtlas;
import com.rpsg.rpg.system.base.TileMapRenderer;
import com.rpsg.rpg.system.control.HeroControler;
import com.rpsg.rpg.system.control.InputControler;
import com.rpsg.rpg.system.control.MapControler;
import com.rpsg.rpg.system.control.MoveControler;
import com.rpsg.rpg.utils.display.Msg;
import com.rpsg.rpg.utils.game.GameUtil;
public class GameView extends IView{
	
	public TileMapRenderer render;
	public Stage stage;
	public static TiledMap map;
	public TileAtlas atlas;
	public static boolean inited=false;
	public Global global=GameViews.global;
	public OrthographicCamera camera;
	
	@Override
	public void init() {
		stage = new Stage(GameUtil.screen_width, GameUtil.screen_height, true);
		camera=(OrthographicCamera) stage.getCamera();
		map=TiledLoader.createMap(Gdx.files.internal(Setting.GAME_RES_MAP + global.map));
		atlas = new TileAtlas(map, Gdx.files.internal(Setting.GAME_RES_MAP 	+ "res"));
		render = new TileMapRenderer(map,atlas,10,10);
		Initialization.init(this);
		inited=true;
	}
	
	@Override
	public void dispose() {
		MapControler.dispose();
		stage.dispose();
		atlas.dispose();
		render.dispose();
		Msg.dispose();
		ResourcePool.dispose();
		System.gc();
	}

	@Override
	public void draw(SpriteBatch batch) {
//		System.out.println(ThreadPool.pool);
//		render.render(camera,0,batch);
//		render.render(camera,1,batch);
		MapControler.draw(batch,this);
		ThreadPool.logic();
//		stage.draw();
//		render.render(camera,new int[]{0});
//		render.render(camera,new int[]{0});
//		render.render(camera,new int[]{1});
	}

	@Override
	public void logic() {
		MapControler.logic(this);
//		stage.act();
		for(Actor i:stage.getActors())
			if(!(i instanceof Hero))
				i.act(0);
		HeroControler.act();
		MoveControler.logic(this);
	}

	public void onkeyTyped(char character) {
		
	}
	
	public void keyDown(int keycode) {
		InputControler.keyDown(keycode,this);
	}

	public void keyUp(int keycode) {
		InputControler.keyDown(keycode,this);
	}
}
