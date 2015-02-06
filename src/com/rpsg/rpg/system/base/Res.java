package com.rpsg.rpg.system.base;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.display.GameViewRes;


public class Res {
//	private static Map<String,Image> pool=new HashMap<String, Image>();
	public static AssetManager ma=GameViewRes.ma;
	public static Image get(String resPath){
//		if(pool.size()>CACHE_MAX_SIZE){
//			Iterator<String> it=pool.keySet().iterator();
//			int poi=0;
//			while(it.hasNext()){
//				String key=it.next();
//				if(++poi>CACHE_MAX_SIZE)
//					pool.remove(key);
//			}
//		}
//		if(pool.get(resPath)==null){
			ma.load(resPath,Texture.class);
			while(!ma.update());
//			pool.put(resPath, new Image((Texture)ma.get(resPath)));
//		}
		return new Image((Texture)ma.get(resPath));
	}
	
	public static Image getNewImage(String resPath){
		return new Image(get(resPath));
	}
	
	public static Drawable getDrawable(String resPath){
		return get(resPath).getDrawable();
	}
	
	public static TextureRegion getRegion(String resPath){
		return get(resPath).getRegion();
	}
	
	public static Texture getTexture(String resPath){
		return get(resPath).getTexture();
	}
	
	public static int CACHE_MAX_SIZE=30;
	
	public static void dispose(){
//		for(String s:pool.keySet()){
//			pool.get(s).dispose();
//		}
//		pool.clear();
	}
}
