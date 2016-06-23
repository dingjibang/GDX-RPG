package com.rpsg.rpg.view;

import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
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
import com.rpsg.rpg.utils.TileRenderer;
import com.rpsg.rpg.utils.display.ColorUtil;
import com.rpsg.rpg.utils.display.GameViewRes;
import com.rpsg.rpg.utils.display.PostUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;

public class GameView extends View{
	
	public TileRenderer render ;//地图绘制器
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
	public int frame = 0;
	
	public BattleView battleView;
	
	public PostProcessor post;//高清画质用
	public Bloom bloom;//模糊用
	public boolean renderable = true;
	
	
	public static boolean showdebug = true,showplayer = true;
	public static Boolean[] 
			renderlayer = new Boolean[]{true,true,true,true,true,true,true,true,true,true},
			colorlayer = new Boolean[]{false,false,false,false,false,false,false,false,false,false};
	
	@Override
	public View init() {
		inited=false;
		Logger.info("开始加载图形。");
		stage.clear();
		if(PostUtil.first)
			PostUtil.init();
		parameter = new TmxMapLoader.Parameters();
		parameter.loadedCallback= (assetManager, fileName, type)->{
			RPG.maps.map = ma.get(Setting.MAP + global.map);
			if(render == null)
				render=new TileRenderer(RPG.maps.map);
			render.setBlending(true);
			render.setView(camera);
			ray.setWorld(world);
			Initialization.init(GameView.this);
			inited = true;
			post = GameViews.post;
			bloom = GameViews.bloom;
			RPG.ctrl.weather.init(RPG.global.weather);
			Logger.info("图形加载完成。");
		};
		filename=Setting.MAP+global.map;
		ma.load(filename, TiledMap.class ,parameter);
		return this;
	}
	
	@Override
	public void dispose() {
		RPG.maps.loader.dispose();
		if(!Setting.persistence.cacheResource){
			ma.unload(filename);//TODO 传送时会变换filename？
//			ma.clear(); //FIXME 可能导致其他纹理也被卸载。。。
		}
		
		if(null!=stackView){
			stackView.dispose();
			stackView=null;
			InputController.currentIOMode=IOMode.MapInput.normal;
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
		
		if(RPG.ctrl.battle.logic()){
			//TODO postFX?
			battleView.draw(batch);
			return;
		}
		
		boolean postable = Setting.persistence.betterDisplay;
		
		if(postable)
			post.capture();
		
		if(renderable){
			RPG.maps.distant.draw((SpriteBatch)stage.getBatch(), this);
			RPG.maps.loader.draw(this);
			//set post
			RPG.ctrl.weather.logic();
		}
		
		if(Setting.persistence.weather && RPG.maps.getProp().get("weather")==null && renderable)
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
		
		if(frame++ == Integer.MAX_VALUE - 1)
			frame = 0;
	}

	@Override
	public void logic() {
		if(!ma.update() || !inited)
			return;
		String append = "   detalTime: "+Gdx.graphics.getDeltaTime() + "\n[地图编辑器专用版] GDX-RPG Map Load Tester\n";
		
		RPG.maps.loader.logic(this);
		for(Actor i:stage.getActors())
			if(!(i instanceof Hero))
				i.act(Gdx.graphics.getDeltaTime());
		RPG.ctrl.hero.act();
		MoveController.logic(this);
		Hero hero = RPG.ctrl.hero.getHeadHero();
		append += "当前玩家坐标：[x:"+hero.mapx+", y:"+hero.mapy+", z:"+hero.layer+"] ("+(int)hero.position.x+","+(int)hero.position.y+")\n";
		append += "[Q键]渲染/不渲染玩家\n";
		append += "[数字0-9键]渲染/不渲染某个特定图层\n";
		append += "[ctrl + 数字0-9键]为某个图层上色\n";
		append += "[H键]显示/不显示调试信息\n";
		append += "当前没有渲染的图层：";
		for(int i = 0;i<renderlayer.length;i++)
			if(!renderlayer[i])
				append+= "层"+i +"   ";
		append += "\n";
		append += "当前上色的图层：";
		for(int i = 0;i<colorlayer.length;i++)
			if(colorlayer[i])
				append+= "层"+i +"   "; 
		stage.setDebugAll(false);
		if(showdebug) 
			GameUtil.append = append;
		else
			GameUtil.append = "";
	}

	public void onkeyTyped(char character) {
		if(!ma.update() || !inited)
			return;
		InputController.keyTyped(character);
	}
	
	public void onkeyDown(int keycode) {
		if(!ma.update() || !inited)
			return;
		if(keycode == Keys.H) showdebug = !showdebug;
		if(keycode == Keys.Q) showplayer = !showplayer;
		
		int num = -1;
		if(keycode >= Keys.NUM_0 && keycode <= Keys.NUM_9)
			num = keycode - Keys.NUM_0;
		if(keycode >= Keys.NUMPAD_0 && keycode <= Keys.NUMPAD_9)
			num = keycode - Keys.NUMPAD_0; 
		if(num != -1){
			if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT))
				colorlayer[num] = !colorlayer[num];
			else
				renderlayer[num] = !renderlayer[num];
		}
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
