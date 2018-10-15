package com.rpsg.rpg.controller;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.Log;
import com.rpsg.rpg.core.Path;
import com.rpsg.rpg.core.Views;
import com.rpsg.rpg.object.map.MapSprite;
import com.rpsg.rpg.object.map.NPC;
import com.rpsg.rpg.object.map.PlayerQueue;
import com.rpsg.rpg.util.Timer;

/**
 * 游戏地图（{@link com.badlogic.gdx.maps.tiled.TiledMap TiledMap}）控制器<br>
 */
public class MapController {
	
	/**当前的地图*/
	TiledMap map;
	/**渲染器*/
	OrthoCachedTiledMapRenderer render;
	/**相机*/
	ScalingViewport viewport;
	/**地图资源*/
	AssetManager assetManager;
	
	/**当前地图上所有的精灵*/
	public List<MapSprite> mapSprites = new ArrayList<>();
	
	/**脚本管理器*/
	public MapScriptController script;
	
	boolean loaded = false; 
	/**是否允许资源加载完成后就在屏幕画图，默认为true，他可以在JS脚本加载完毕后经由JS设置为false，这样可以在画图之前搞些事情*/
	public boolean renderable = true;
	
	/**玩家队列*/
	public PlayerQueue queue;

	/**地图名称*/
	private String name;

	/**post*/
	public PostController post;
	
	public MapController() {
		assetManager = new AssetManager();
		/**增加一个TiledMap Loader给管理器*/
		assetManager.setLoader(TiledMap.class, new TmxMapLoader());
		
		post = new PostController();
	}
	
	/**
	 * 从硬盘中加载一张{@link TiledMap}地图
	 * @param path 地图路径
	 */
	public void load(String path) {
		loaded = false;
		renderable = true;
		Views.loadView.start("load_tmx");
		
		//如果不允许缓存就清除以前的地图
		if(!Game.setting.cache)
			assetManager.clear();
		
		Parameters param = new Parameters();
		
		//使用Nearest过滤纹理，防止游戏窗口放大时，地图的每个小块中间出现缝隙
		param.textureMinFilter = param.textureMagFilter = TextureFilter.Nearest;

		//加载完毕后的回调
		param.loadedCallback = (am, fileName, type) -> {
			map = am.get(fileName, TiledMap.class);
			
			//销毁之前的render
			if(render != null)
				render.dispose();
			
			//创建地图画笔
			render = new OrthoCachedTiledMapRenderer(map);
			render.setBlending(true);
			
			//创建相机
			viewport = Game.viewport();
			
			//清空以前的地图精灵
			this.mapSprites.clear();
			
			//如果archiveMapSprites是null的话，表示从地图里载入精灵，否则从存档里载入精灵
			List<MapSprite> archiveMapSprites = Game.archive.get().getMapSprites();
			//依次把精灵（们）加入到舞台
			this.mapSprites.addAll(archiveMapSprites == null ? loadSprites() : archiveMapSprites);
			
			//从存档中读取玩家坐标点 TODO
			queue = new PlayerQueue();
			
			
			//创建脚本管理器
			script = new MapScriptController();
			
			Game.archive.get().mapName = path;

			Object _name = map.getProperties().get("name");
			name = $.isEmpty(_name) ? "未知地图" : _name.toString();
			
			resize();
			
			loaded = true;
			Views.loadView.stop("load_tmx");
			Log.i("A map loaded[" + fileName + "]");
		};
		
		//开始加载
		assetManager.load(Path.MAP + path, TiledMap.class, param);
	}
	
	/**
	 * 从map里读取并加载精灵
	 */
	public List<MapSprite> loadSprites() {
		//测试用的代码
		List<MapSprite> list = new ArrayList<>();
		//创建一个NPC
		NPC npc = new NPC(0, 0, 0, Path.WALK_HERO + "walk_wriggle.png");
		mapSprites.add(npc);
		//给这个NPC加上一条碰撞脚本
//		npc.scripts.put(CollideType.face, Game.script.map.create("mytest.js"));
		//3秒之后执行一个假碰撞
//		Timer.add(Timer.TimeType.frame, 10, () -> script.add(npc, CollideType.face));
		
		Timer.add(Timer.TimeType.frame, 240, () -> npc.walk(MapSprite.Facing.RIGHT));
		Timer.add(Timer.TimeType.frame, 252, () -> npc.walk(MapSprite.Facing.RIGHT));
		Timer.add(Timer.TimeType.frame, 264, () -> npc.walk(MapSprite.Facing.RIGHT));
		Timer.add(Timer.TimeType.frame, 276, () -> npc.walk(MapSprite.Facing.UP));
		Timer.add(Timer.TimeType.frame, 288, () -> npc.walk(MapSprite.Facing.UP));
		Timer.add(Timer.TimeType.frame, 300, () -> npc.walk(MapSprite.Facing.UP));
		Timer.add(Timer.TimeType.frame, 312, () -> npc.walk(MapSprite.Facing.LEFT));
		Timer.add(Timer.TimeType.frame, 324, () -> npc.walk(MapSprite.Facing.LEFT));
		Timer.add(Timer.TimeType.frame, 336, () -> npc.walk(MapSprite.Facing.LEFT));
		Timer.add(Timer.TimeType.frame, 348, () -> npc.walk(MapSprite.Facing.DOWN));
		Timer.add(Timer.TimeType.frame, 360, () -> npc.walk(MapSprite.Facing.DOWN));
		Timer.add(Timer.TimeType.frame, 372, () -> npc.walk(MapSprite.Facing.DOWN));

		Timer.add(Timer.TimeType.frame, 400, () -> npc.setSpeed(1));
		
		Timer.add(Timer.TimeType.frame, 400 + 48 * 0, () -> npc.walk(MapSprite.Facing.RIGHT));
		Timer.add(Timer.TimeType.frame, 400 + 48 * 1, () -> npc.walk(MapSprite.Facing.RIGHT));
		Timer.add(Timer.TimeType.frame, 400 + 48 * 2, () -> npc.walk(MapSprite.Facing.RIGHT));
		Timer.add(Timer.TimeType.frame, 400 + 48 * 3, () -> npc.walk(MapSprite.Facing.UP));
		Timer.add(Timer.TimeType.frame, 400 + 48 * 4, () -> npc.walk(MapSprite.Facing.UP));
		Timer.add(Timer.TimeType.frame, 400 + 48 * 5, () -> npc.walk(MapSprite.Facing.UP));
		Timer.add(Timer.TimeType.frame, 400 + 48 * 6, () -> npc.walk(MapSprite.Facing.LEFT));
		Timer.add(Timer.TimeType.frame, 400 + 48 * 7, () -> npc.walk(MapSprite.Facing.LEFT));
		Timer.add(Timer.TimeType.frame, 400 + 48 * 8, () -> npc.walk(MapSprite.Facing.LEFT));
		Timer.add(Timer.TimeType.frame, 400 + 48 * 9, () -> npc.walk(MapSprite.Facing.DOWN));
		Timer.add(Timer.TimeType.frame, 400 + 48 * 10, () -> npc.walk(MapSprite.Facing.DOWN));
		Timer.add(Timer.TimeType.frame, 400 + 48 * 11, () -> npc.walk(MapSprite.Facing.DOWN));
		
		
		//end 测试用的代码
		return list;
	}
	
	/**瞬移到当前地图或某地图（取决mapName是否为空）*/
	public void teleport(int x, int y, int z, String mapName) {
		Game.archive.get().position.set(x, y, z);
		
		if(mapName == null){//重置queue坐标
			queue.reload();
		}else{//重载地图
			load(mapName);
		}
	}

	/**
	 * 画图<br>
	 * 画图将从最下层画到最顶层，并且如果有精灵的话，则在画图中穿插相应图层的精灵，精灵是在当前层之上画出的<br>
	 * 穿插的精灵来自{@link #mapSprites}里的<br>
	 * 画图时，{@link #render}将使用自己的画笔（{@link com.badlogic.gdx.graphics.g2d.SpriteCache SpriteCache}）进行画图，可能会导致画图异常（比如画不出其他的精灵）。 
	 */
	public void draw(Batch batch) {
		if(!loaded)
			return;
		
		post.begin();
		
		
		Camera camera = viewport.getCamera();
		camera.update();
		
		//debug
		camera.position.set(730, 430, 0);
		
		//更新渲染器坐标位置，缓存画笔projection状态
		Matrix4 projectionCpy = batch.getProjectionMatrix().cpy();
		batch.setProjectionMatrix(camera.combined);
	
		//获取所有有效层
		int layersCount = map.getLayers().getCount();
		
		int skip = 0;
		//开始从最下层往上画
		List<MapSprite> drawList = new ArrayList<>(10);
		for(int i = 0; i < layersCount; i ++){
			MapLayer layer = map.getLayers().get(i);
			
			//如果该层不是块层，而是对象层，就跳过该层的绘制
			if(!(layer instanceof TiledMapTileLayer)){
				skip ++;
				continue;
			}
			
			//画地图
			render.render(new int[]{i});
			
			/**
			 * 留给草刺猬爷爷：可能你第一个遇到的坑是各种坐标系问题=。= 研究一下以熟悉gdx吧
			 */
			
			drawList.clear();
			
			//遍历stage里所有的当前ZIndex的MapSprite并画出
			for(MapSprite mapSprite : mapSprites)
				if(mapSprite.getZIndex() == i - skip)
					drawList.add(mapSprite);
			
			if(!drawList.isEmpty()){
				batch.begin();
				for(MapSprite sprite : drawList)
					sprite.draw(batch);
				batch.end();
			}
		}
		
		//还原画笔projection状态
		batch.setProjectionMatrix(projectionCpy);
		
		post.end();
	}
	
	/**获取当前的地图*/
	public TiledMap map() {
		return map;
	}
	
	/**每帧被执行*/
	public void act() {
		assetManager.update();
		
		if(!loaded)
			return;
		
		script.act();
		for(MapSprite sprite : mapSprites) {
			sprite.act();
		}
	}

	/**销毁地图控制器，将释放所有地图纹理等资源*/
	public void dispose() {
		assetManager.dispose();
		render.dispose();
		post.dispose();
	}

	/**返回当前地图的名称，如果没有则返回“未知地图”*/
	public String name() {
		return name;
	}

	public boolean loaded() {
		return loaded;
	}
	
	public void resize() {
		if(viewport != null && render != null){
			viewport.update(Game.width(), Game.height(), true);
			render.setView((OrthographicCamera)viewport.getCamera());
			
			post.resize(viewport);
		}
		
	}
}
