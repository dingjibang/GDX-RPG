package com.rpsg.rpg.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.rpsg.rpg.ui.AsyncLoadImage;
import com.rpsg.rpg.ui.Image;

/**
 * GDX-RPG 资源/缓存类
 */
public class Res {
	
	/**资源管理线程*/
	public static AssetManager assetManager;
	
	/**初始化*/
	public static void init(){
		assetManager = new AssetManager();
		/**增加一个TiledMap Loader给管理器*/
		assetManager.setLoader(TiledMap.class, new TmxMapLoader());
	}
	
	/**
	 * 获取一张图片，它是异步加载的，如果需要同步加载，请调用{@link #sync(String) sync}方法
	 */
	public static AsyncLoadImage get(String path){
		return new AsyncLoadImage(path);
	}
	
	/**
	 * 立即获取一张图片
	 */
	public static Image sync(String path){
		return new Image(getTexture(path));
	}
	
	/**
	 * 更新资源管理器
	 */
	public static boolean act(){
		return assetManager.update();
	}
	
	/**
	 * 立即获取一张纹理
	 */
	public static Texture getTexture(String path){
		assetManager.load(path, Texture.class);
		while(!assetManager.update());
		return assetManager.get(path, Texture.class);
	}
	
	/**卸载全部纹理*/
	public static void dispose(){
		assetManager.dispose();
	}
	
}
