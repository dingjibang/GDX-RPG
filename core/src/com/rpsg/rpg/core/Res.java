package com.rpsg.rpg.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.rpsg.rpg.ui.AsyncLoadImage;
import com.rpsg.rpg.ui.Image;

/**
 * GDX-RPG 资源/缓存类
 */
public class Res {
	
	/**资源管理线程*/
	public static AssetManager am;
	
	/**初始化*/
	public static void init(){
		am = new AssetManager();
	}
	
	/**
	 * 获取一张图片，它是异步加载的，如果需要同步加载，请调用{@link #sync(String path) sync}方法
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
	public static void act(){
		am.update();
	}
	
	/**
	 * 立即获取一张纹理
	 */
	public static Texture getTexture(String path){
		am.load(path, Texture.class);
		while(!am.update());
		return am.get(path, Texture.class);
	}
	
	/**卸载全部纹理*/
	public static void dispose(){
		am.dispose();
	}
	
}
