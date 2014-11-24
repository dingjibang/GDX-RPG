package com.rpsg.rpg.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rpsg.rpg.object.Hero;
import com.rpsg.rpg.system.Image;
import com.rpsg.rpg.system.Setting;
import com.rpsg.rpg.utils.GameUtil;
import com.rpsg.rpg.utils.TileMapRendererX;
public class GameView extends IView{
	
	public TileMapRendererX render;
	public Stage stage;
	public TiledMap map;
	public TileAtlas atlas;
	public static boolean inited=false;
	public OrthographicCamera camera;
	public Hero hero;
	
	@Override
	public void init() {
		stage = new Stage(GameUtil.screen_width, GameUtil.screen_height, true);
		camera=(OrthographicCamera) stage.getCamera();
		map=TiledLoader.createMap(Gdx.files.internal(Setting.GAME_RES_MAP + "test/map.tmx"));
		atlas = new TileAtlas(map, Gdx.files.internal(Setting.GAME_RES_MAP 	+ "test"));
		render = new TileMapRendererX(map,atlas,camera);
		hero=new Hero("heros/walk_marisa.png");
		inited=true;
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void draw(SpriteBatch batch) {
		render.render(camera,0,batch);
		render.render(camera,1,batch);
		hero.images[3].draw(batch);
	}

	@Override
	public void logic() {
//		System.out.println(camera.position.x+" "+camera.position.y);
	}

	@Override
	public void onkeyTyped(char character) {
		
	}

	public void keyDown(int keycode) {
		if(keycode==21)
			camera.position.x-=30;
		if(keycode==22)
			camera.position.x+=30;
		if(keycode==19)
			camera.position.y+=30;
		if(keycode==20)
			camera.position.y-=30;
	}

	public void keyUp(int keycode) {
		
	}
}
