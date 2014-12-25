package com.rpsg.rpg.view;


import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.object.rpgobj.Hero;
import com.rpsg.rpg.system.base.IView;
import com.rpsg.rpg.system.base.Initialization;
import com.rpsg.rpg.system.base.ResourcePool;
import com.rpsg.rpg.system.base.ThreadPool;
import com.rpsg.rpg.system.control.HeroControler;
import com.rpsg.rpg.system.control.InputControler;
import com.rpsg.rpg.system.control.MapControler;
import com.rpsg.rpg.system.control.MoveControler;
import com.rpsg.rpg.utils.display.Msg;
public class GameView extends IView{
	
	public OrthogonalTiledMapRenderer render;
	public Stage stage;
	public TiledMap map;
	public static boolean inited=false;
	public Global global=GameViews.global;
	public OrthographicCamera camera;
	public RayHandler ray;
	public PointLight pl;
	public World world;
	public BodyDef def;
	public PolygonShape p;
	public Box2DDebugRenderer ren;
	@Override
	public void init() {
//		RayHandler.setGammaCorrection(true);
		RayHandler.useDiffuseLight(true);
		stage = new Stage();
		camera=(OrthographicCamera) stage.getCamera();
		map=new TmxMapLoader().load(Setting.GAME_RES_MAP + global.map);
		render = new OrthogonalTiledMapRenderer(map);
		render.setView(camera);
		world=new World(new Vector2(0,0),true);
		ren=new Box2DDebugRenderer();
		def=new BodyDef();
		def.type=BodyType.StaticBody;
		Body b=world.createBody(def);
		p=new PolygonShape();
		
		p.setAsBox(16,16);
		FixtureDef fd = new FixtureDef();
		fd.shape = p;
		b.createFixture(fd);
		ray=new RayHandler(world);
		pl=new PointLight(ray,30);
		inited=true;
		
		Initialization.init(this);
		
	}
	
	@Override
	public void dispose() {
		MapControler.dispose();
		stage.dispose();
		render.dispose();
		Msg.dispose();
		ResourcePool.dispose();
		map.dispose();
		System.gc();
	}

	@Override
	public void draw(SpriteBatch batch) {
		//		System.out.println(ThreadPool.pool);
//		render.render(camera,0,batch);
//		render.render(camera,1,batch);
//		b.setTransform(HeroControler.getHeadHero().position.x+20,HeroControler.getHeadHero().position.y,0);
		MapControler.draw(batch,this);
		ray.setBlur(true);
		pl.setDistance(800);
		pl.setSoft(false);;
//		ray.setBlur(true);
		pl.setPosition(145,310);
//		ray.setShadows(true);
		ray.setAmbientLight(0.3f,0.3f,0.6f,0.3f);
		ray.setCombinedMatrix(camera.combined);
		ray.updateAndRender();
//		ThreadPool.logic();
		ren.render(world, camera.combined);
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
