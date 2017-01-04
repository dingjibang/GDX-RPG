package com.rpsg.rpg.controller;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rpsg.rpg.core.File;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.Path;
import com.rpsg.rpg.core.Views;
import com.rpsg.rpg.object.map.CollideType;
import com.rpsg.rpg.object.map.MapSprite;
import com.rpsg.rpg.object.map.NPC;
import com.rpsg.rpg.util.Timer;

/**
 * 游戏地图（{@link com.badlogic.gdx.maps.tiled.TiledMap TiledMap}）控制器
 */
public class MapController {
	
	/**当前的地图*/
	TiledMap map;
	/**渲染器*/
	OrthoCachedTiledMapRenderer render;
	/**相机*/
	OrthographicCamera camera;
	/**地图资源*/
	AssetManager assetManager;
	
	/**当执行{@link #load(String, Runnable)}之前，将先调用一次，以执行一些清理工作*/
	Runnable beforeLoad;
	
	/**当前地图上所有的精灵*/
	public List<MapSprite> mapSprites = new ArrayList<>();
	
	/**脚本管理器*/
	public ScriptController script;
	
	boolean loaded = false; 
	
	
	public MapController() {
		assetManager = new AssetManager();
		/**增加一个TiledMap Loader给管理器*/
		assetManager.setLoader(TiledMap.class, new TmxMapLoader());
	}
	
	public void setBeforeLoad(Runnable beforeLoad) {
		this.beforeLoad = beforeLoad;
	}
	
	/**
	 * 从硬盘中加载一张{@link TiledMap}地图
	 * @param path 地图路径
	 * @param loadedCallback 完成后的回调
	 */
	public void load(String path) {
		loaded = false;
		Views.loadView.start("load_tmx");
		
		//如果不允许缓存就清除以前的地图
		if(!Game.setting.cache)
			assetManager.clear();
		
		if(beforeLoad != null)
			beforeLoad.run();
		
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
			camera = new OrthographicCamera(Game.STAGE_WIDTH, Game.STAGE_HEIGHT);
			
			//清空以前的地图精灵
			this.mapSprites.clear();
			
			//如果archiveMapSprites是null的话，表示从地图里载入精灵，否则从存档里载入精灵
			List<MapSprite> archiveMapSprites = Game.archive.get().getMapSprites();
			//依次把精灵（们）加入到舞台
			this.mapSprites.addAll(archiveMapSprites == null ? loadSprites() : archiveMapSprites);
			
			//从存档中读取玩家坐标点 TODO
			
			
			//创建脚本管理器
			script = new ScriptController();
			
			loaded = true;
			Views.loadView.stop("load_tmx");
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
		NPC npc = new NPC(0,0,0);
		mapSprites.add(npc);
		//给这个NPC加上一条碰撞脚本
		npc.scripts.put(CollideType.face, File.readString(Path.SCRIPT_MAP + "mytest.js"));
		//3秒之后执行一个假碰撞
		Timer.add(180, () -> script.add(npc, CollideType.face));
		
		Timer.add(240, () -> npc.move(1, 0));
		
		
		//end 测试用的代码
		return list;
	}

	/**
	 * 画图<br>
	 * 画图将从最下层画到最顶层，并且如果有精灵的话，则在画图中穿插相应图层的精灵，精灵是在当前层之上画出的<br>
	 * 穿插的精灵来自{@link Stage}里的<br>
	 * 画图时，{@link #render}将使用自己的画笔（{@link com.badlogic.gdx.graphics.g2d.SpriteCache SpriteCache}）进行画图，可能会导致画图异常（比如画不出其他的精灵）。 
	 */
	public void draw(Batch batch) {
		if(!loaded)
			return;
		
		//缓存原相机状态
		Vector3 originPosition = camera.position.cpy();
		Matrix4 combined = camera.combined.cpy();
		
		//debug
		camera.position.set(130, 130, 0);
		
		//更新相机状态
		camera.update();
		//更新渲染器坐标位置
		render.setView(camera);
		batch.setProjectionMatrix(camera.combined);
	
		//获取所有有效层
		int layersCount = map.getLayers().getCount();
		
		int skip = 0;
		//开始从最下层往上画
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
			
			List<MapSprite> drawList = new ArrayList<>();
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
		
		render.render();
		
		//还原相机坐标点
		camera.position.set(originPosition);
		camera.combined.set(combined);
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
	
	public void dispose() {
		assetManager.dispose();
		render.dispose();
	}
}
