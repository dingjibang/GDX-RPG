package com.rpsg.rpg.utils.game;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.utils.display.AlertUtil;

/**
 * 遊戲工具類
 * 並沒有軟用
 * TODO 可以把它合并到其他随便什么类里。。。
 * 为啥变成繁体字了，你妹哦
 * @author Administrator
 *
 */
public class GameUtil {
	//注意，screen_width、screen_height和getScreenWidth、getScreenHeight完全不同，
	//前者是常量（1024,576），他负责UI组件的拉抻完整，请勿修改！
	//后者是变量，表明了游戏当前openGL窗口的物理尺寸
	public static final int screen_width = 1024;
	public static final int screen_height = 576;
	public static boolean isDesktop=false;
	public static int fps=0;
	private static BitmapFont font;
	
	public static void init(){
		font = Res.font.get((int) (15*getScale()));
	}
	
	public static int getScreenWidth(){
		return Gdx.graphics.getWidth();
	}
	
	public static int getScreenHeight(){
		return Gdx.graphics.getHeight();
	}
	
	public static void drawFPS(SpriteBatch batch){
		if(Setting.persistence.showFPS){
			fps=Gdx.graphics.getFramesPerSecond();
			
			font.setColor(Color.BLACK);
			font.draw(batch,"FPS:"+fps,13,(int) (getScreenWidth()-13*getScale()));
			font.setColor(Color.WHITE);
			font.draw(batch,"FPS:"+fps,12,(int) (getScreenWidth()-12*getScale()));
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
	
	public static float getScaleW(){
		return (float)Gdx.graphics.getWidth()/(float)GameUtil.screen_width;
	}
	
	public static float getScaleH(){
		return (float)Gdx.graphics.getHeight()/(float)GameUtil.screen_height;
	}
	
	public static NinePatch processNinePatchFile(String fname) {
	    final Texture t = new Texture(Gdx.files.internal(fname));
	    final int width = t.getWidth() - 2;
	    final int height = t.getHeight() - 2;
	    return new NinePatch(new TextureRegion(t, 1, 1, width, height), 12, 12, 12, 12);
	}
	
	public static void openURL(String httpURL){
		Gdx.net.openURI(httpURL);
	}
}
