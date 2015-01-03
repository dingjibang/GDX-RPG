package com.rpsg.rpg.view;



import box2dLight.RayHandler;

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
import com.rpsg.rpg.system.control.*;
import com.rpsg.rpg.utils.display.*;

public class GameView extends IView{
	
	public OrthogonalTiledMapRenderer render;
	public Stage stage;
	public TiledMap map;
	public static boolean inited=false;
	public Global global=GameViews.global;
	public OrthographicCamera camera;
	public RayHandler ray;
	public World world;
	
	public StackView stackView;
	
	@Override
	public void init() {
		stage = new Stage();
		camera=(OrthographicCamera) stage.getCamera();
		map=new TmxMapLoader().load(Setting.GAME_RES_MAP + global.map);
		render = new OrthogonalTiledMapRenderer(map);
		render.setView(camera);
		world=new World(new Vector2(0,0),true);
		ray=new RayHandler(world);
		Initialization.init(this);
		
		inited=true;
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
