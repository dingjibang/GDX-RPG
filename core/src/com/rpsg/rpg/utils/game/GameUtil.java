package com.rpsg.rpg.utils.game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.utils.display.FontUtil;

public class GameUtil {
	public static int screen_width;
	public static int screen_height;
	public static boolean isDesktop=false;
	public static int fps=0;
	public static int getScreenWidth(){
		return Gdx.graphics.getWidth();
	}
	
	public static int getScreenHeight(){
		return Gdx.graphics.getHeight();
	}
	
	public static void drawFPS(SpriteBatch batch){
		if(Setting.persistence.showFPS){
			fps=Gdx.graphics.getFramesPerSecond();
			FontUtil.draw(batch, "FPS:"+fps, (int) (15*getScale()),Color.BLACK,13, (int) (getScreenWidth()-13*getScale()),1000,0,10);
			FontUtil.draw(batch, "FPS:" + fps, (int) (15 * getScale()), Color.WHITE, 12, (int) (getScreenHeight() - 12 * getScale()), 1000, 0, 10);
		}
	}
	
	public static SpriteBatch resetBacth(SpriteBatch batch){
		batch.end();
		batch.begin();
		return batch;
	}
	
	public static Map<String,Object> parseMapProperties(MapProperties mp){
		Iterator<String> i = mp.getKeys();
		Map<String,Object> m=new HashMap<String, Object>();
		while(i.hasNext()){
			String s=i.next();
			m.put(s, parseEsc(mp.get(s)));
		}
		return m;
	}
	
	private static String parseEsc(Object esc){
		return esc.toString().replaceAll("&quot;", "\"");
	}

	public static float getScale(){
		return (float)Gdx.graphics.getWidth()/(float)GameUtil.screen_width;
	}
	
	public static NinePatch processNinePatchFile(String fname) {
	    final Texture t = new Texture(Gdx.files.internal(fname));
	    final int width = t.getWidth() - 2;
	    final int height = t.getHeight() - 2;
	    return new NinePatch(new TextureRegion(t, 1, 1, width, height), 12, 12, 12, 12);
	}
}
