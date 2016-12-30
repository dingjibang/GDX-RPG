package com.rpsg.rpg.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.rpsg.rpg.ui.AsyncLoadImage;

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
	 * 获取一张图片，它是异步加载的，如果需要同步加载，请调用getSync()
	 */
	public static Image get(String path){
		return new AsyncLoadImage(path);
	}
	
	/**
	 * 立即获取一张图片
	 */
	public static Image getSync(String path){
		return new Image(getTexture(path));
	}
	
	/**
	 * 立即获取一张纹理
	 */
	public static Texture getTexture(String path){
		return am.get(path, Texture.class);
	}
	
	/**卸载全部纹理
	 * @return */
	public static void dispose(){
		am.dispose();
	}
	
}
