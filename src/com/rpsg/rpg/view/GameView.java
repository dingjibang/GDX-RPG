package com.rpsg.rpg.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rpsg.rpg.object.heros.HeaderHero;
import com.rpsg.rpg.object.heros.Hero;
import com.rpsg.rpg.system.Setting;
import com.rpsg.rpg.utils.GameUtil;
import com.rpsg.rpg.utils.MoveControler;
//import com.rpsg.rpg.utils.TileMapRendererX;
public class GameView extends IView{
	
//	public TileMapRendererX render;
	public TileMapRenderer render;
	public Stage stage;
	public TiledMap map;
	public TileAtlas atlas;
	public static boolean inited=false;
	public OrthographicCamera camera;
	public HeaderHero hero;
	@Override
	public void init() {
		stage = new Stage(GameUtil.screen_width, GameUtil.screen_height, true);
		camera=(OrthographicCamera) stage.getCamera();
		map=TiledLoader.createMap(Gdx.files.internal(Setting.GAME_RES_MAP + "test/map.tmx"));
		atlas = new TileAtlas(map, Gdx.files.internal(Setting.GAME_RES_MAP 	+ "test"));
//		render = new TileMapRendererX(map,atlas,camera);
		render = new TileMapRenderer(map,atlas,10,10);
		hero=new HeaderHero("/walk_marisa.png");
		hero.position=new Vector2(100,100);
		stage.addActor(hero);
		inited=true;
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void draw(SpriteBatch batch) {
//		render.render(camera,0,batch);
//		render.render(camera,1,batch);
		render.render(camera,new int[]{0,1});
		stage.draw();
	}

	@Override
	public void logic() {
		stage.act();
		MoveControler.logic(this);
	}

	@Override
	public void onkeyTyped(char character) {
		
	}
	
	public int walkInput=0;
	public boolean isPressWalk_l=false,isPressWalk_r=false,isPressWalk_u=false,isPressWalk_d=false;
	public boolean isPressCtrl=false;
	
	public void keyDown(int keycode) {
		MoveControler.keyDown(keycode, this);
	}

	public void keyUp(int keycode) {
		MoveControler.keyUp(keycode,this);
	}
}
