package com.rpsg.rpg.system.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.controller.LazyBitmapFontConctoller;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.system.ui.ProxyImage;
import com.rpsg.rpg.utils.display.GameViewRes;
import com.rpsg.rpg.utils.game.Logger;

public class Res {
	public static AssetManager ma = GameViewRes.ma;//线程1用来加载异步代理图片
	public static AssetManager ma2 = GameViewRes.ma2;//线程2用来同步加载纹理
	public static Texture NO_TEXTURE;

	public static LazyBitmapFontConctoller font;

	public static Image get(String resPath) {
		Logger.info("伪装加载纹理：" + resPath);

		generateTempTexture();
		return new ProxyImage(resPath);
	}
	
	public static Image base(){
		return get(Setting.UI_BASE_IMG);
	}
	
	public static Image getSync(String resPath){
		return ((ProxyImage) get(resPath)).sync();
	}

	public static Image get(String resPath, float[][] pos) {
		Logger.info("伪装加载纹理：" + resPath);
		generateTempTexture();

		return new ProxyImage(resPath, pos);
	}

	public static Label get(Object text, int fontSize) {
		return font.getLabel(text.toString(), fontSize);
	}

	public static boolean exist(String path) {
		return Gdx.files.internal(path).exists();
	}

	public static Image getNP(String resPath) {
		return new Image(getTexture(resPath));
	}

	public static synchronized Image fuckOPENGL(String resPath) {
		return new ProxyImage(resPath);
	}

	private static void generateTempTexture() {

		if (NO_TEXTURE == null)
			NO_TEXTURE = new Texture(Gdx.files.internal(Setting.IMAGE_GLOBAL + "noTexture.png"));

	}

	public static Drawable getDrawable(String resPath) {
		return new TextureRegionDrawable(new TextureRegion(getTexture(resPath)));
	}

	public static TextureRegion getRegion(String resPath) {
		return new TextureRegion(getTexture(resPath));
	}

	public static Texture getTexture(String resPath) {
		if (!ma.containsAsset(resPath)) {
			ma.load(resPath, Texture.class);
			while (!ma.update())
				;
		}
		Texture txt = ma.get(resPath);
		if (Setting.persistence.scaleAliasing)
			txt.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return txt;
	}

	public static int CACHE_MAX_SIZE = 30;

	public static void dispose(String resPath) {
		try {
			ma.unload(resPath);
			while (!ma.update())
				;
		} catch (Exception e) {
			Logger.error("纹理已被卸载或不存在，无法卸载纹理：" + resPath);
		}
	}

	public static void init() {
		font = new LazyBitmapFontConctoller();
		Res.base();
	}
}
