package com.rpsg.rpg.system.base;

import java.lang.reflect.Method;
import java.util.Arrays;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.display.GameViewRes;
import com.rpsg.rpg.utils.game.Logger;

public class Res {
	public static AssetManager ma = GameViewRes.ma;
	public static AssetManager ma2 = GameViewRes.ma2;
	public static Texture NO_TEXTURE;
	public static Image get(String resPath) {
		Logger.info("伪装加载纹理：" + resPath);
		
		Enhancer en=new Enhancer();
		en.setSuperclass(Image.class);
		en.setCallback((MethodInterceptor)(Object obj, Method method, Object[] args, MethodProxy proxy)->{
			if(method.getName().equals("getTexture") || method.getName().equals("getDrawable"))
				return proxy.invokeSuper(obj, args);
			if(((Image)obj).lazy && ((Image)obj).getTexture()==NO_TEXTURE){
				Image img=(Image)obj;
				img.lazy=false;
				if (!ma2.isLoaded(resPath)){
					Logger.info("尝试读取纹理：" + resPath);
					TextureParameter param=new TextureParameter();
					param.loadedCallback=(AssetManager assetManager, String fileName, @SuppressWarnings("rawtypes") Class type)->{
						img.setDrawable(new TextureRegionDrawable(new TextureRegion((Texture) ma2.get(resPath))));
						img.reGenerateSize();
					};
					ma2.load(resPath, Texture.class, param);
				}else{
					img.setDrawable(new TextureRegionDrawable(new TextureRegion((Texture) ma2.get(resPath))));
					img.reGenerateSize();
				}
			}
			return proxy.invokeSuper(obj, args);
		});
		generateTempTexture();
		return (Image) en.create(new Class[]{Texture.class},new Object[]{NO_TEXTURE});
	}
	
	public static void logic(){
	}
	

	private static void generateTempTexture() {
		if(NO_TEXTURE==null || NO_TEXTURE.isDispose())
			NO_TEXTURE=new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_GLOBAL+"noTexture.png"));
	}


	public static Image getNewImage(String resPath) {
		return new Image(get(resPath));
	}

	public static Drawable getDrawable(String resPath) {
		return get(resPath).getDrawable();
	}

	public static TextureRegion getRegion(String resPath) {
		return get(resPath).getRegion();
	}

	public static Texture getTexture(String resPath) {
		return get(resPath).getTexture();
	}

	public static int CACHE_MAX_SIZE = 30;

	public static void dispose(String resPath) {
		try {
			ma.unload(resPath);
			while (!ma.update());
		} catch (Exception e) {
			Logger.error("无法卸载纹理 - " + resPath);
		}
	}
}
