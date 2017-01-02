package com.rpsg.rpg.controller;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.rpg.core.Path;
import com.rpsg.rpg.core.Res;
import com.rpsg.rpg.object.game.Archive;
import com.rpsg.rpg.object.map.MapSprite;

/**
 * 游戏地图（{@link com.badlogic.gdx.maps.tiled.TiledMap TiledMap}）控制器
 */
public class MapController {
	
	/**当前的地图*/
	TiledMap map;
	/**渲染器*/
	OrthoCachedTiledMapRenderer render;
	
	/**
	 * 从硬盘中加载一张{@link TiledMap}地图
	 * @param path 地图路径
	 * @param loadedCallback 完成后的回调
	 * @param andLoadSprites 是否加载完成后，把地图里的精灵也读出来，在新存档/进入到下一个地图时候，精灵是被重新加载的，当读档时候，精灵是不读取的，而是直接从{@link Archive#mapSprites} 里读取
	 */
	public void load(String path, boolean andLoadSprites, CustomRunnable<TiledMap> loadedCallback) {
		Parameters param = new Parameters();
		
		//使用Nearest过滤纹理，防止游戏窗口放大时，地图的每个小块中间出现缝隙
		param.textureMinFilter = param.textureMagFilter = TextureFilter.Nearest;
		
		//加载完毕后的回调
		param.loadedCallback = (am, fileName, type) -> {
			map = am.get(fileName, TiledMap.class);
			
			//销毁之前的render
			if(render != null)
				render.dispose();
			
			render = new OrthoCachedTiledMapRenderer(map);
			render.setBlending(true);
			
			
			if(andLoadSprites)
				loadSprites();
			
			//回调回调（←233）
			loadedCallback.run(map);
		};
		Res.assetManager.load(Path.MAP + path, TiledMap.class, param);
	}
	
	/**
	 * 从map里读取并加载精灵
	 */
	private void loadSprites() {
		
	}

	/**
	 * 画图<br>
	 * 画图将从最下层画到最顶层，并且如果有精灵的话，则在画图中穿插相应图层的精灵，精灵是在当前层之上画出的<br>
	 * 穿插的精灵来自{@link Stage}里的
	 * 画图时，{@link　#render}将使用自己的画笔（{@link com.badlogic.gdx.graphics.g2d.SpriteCache SpriteCache}）进行画图，可能会导致画图异常（比如画不出其他的精灵）。 
	 */
	public void draw(Stage stage) {
		OrthographicCamera camera = (OrthographicCamera) stage.getCamera();
		//缓存原相机坐标点
		Vector3 originPosition = camera.position.cpy();
		
		//debug
		camera.position.set(130, 130, 0);
		
		//更新相机状态
		camera.update();
		//更新渲染器坐标位置
		render.setView(camera);
	
		//获取所有有效层
		int layersCount = map.getLayers().getCount();
		
		Batch batch = stage.getBatch();
		
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
			
			List<Actor> drawList = new ArrayList<>();
			//遍历stage里所有的当前ZIndex的MapSprite并画出
			for(Actor actor : stage.getRoot().getChildren())
				if(actor.getZIndex() == i - skip && actor instanceof MapSprite)
					drawList.add(actor);
			
			if(!drawList.isEmpty()){
				batch.begin();
				for(Actor sprite : drawList)
					sprite.draw(batch, stage.getRoot().getColor().a);
				batch.end();
			}
		}
		
		render.render();
		
		//还原相机坐标点
		camera.position.set(originPosition);
	}
	
	/**获取当前的地图*/
	public TiledMap map() {
		return map;
	}
}
