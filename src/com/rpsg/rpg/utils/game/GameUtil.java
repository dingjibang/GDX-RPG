package com.rpsg.rpg.utils.game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.utils.display.FontUtil;

public class GameUtil {
	public static int screen_width;
	public static int screen_height;
	
	public static int getScreenWidth(){
		return Gdx.graphics.getWidth();
	}
	
	public static int getScreenHeight(){
		return Gdx.graphics.getHeight();
	}
	
	public static void drawFPS(SpriteBatch batch){
		if(Setting.persistence.showFPS){
			FontUtil.draw(batch, "FPS:"+Gdx.graphics.getFramesPerSecond(),15,Color.BLACK,13,GameUtil.screen_height-13,1000,0,10);
			FontUtil.draw(batch, "FPS:"+Gdx.graphics.getFramesPerSecond(),15,Color.WHITE,12,GameUtil.screen_height-12,1000,0,10);
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
			m.put(s, mp.get(s));
		}
		return m;
	}
}
