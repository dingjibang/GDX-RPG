package com.rpsg.rpg.utils.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;

/**
 * 遊戲工具類
 * 並沒有軟用
 * TODO 可以把它合并到其他随便什么类里。。。
 * 为啥变成繁体字了，你妹哦
 * @author Administrator
 *
 */
public class GameUtil {
	/**UI stage width*/
	public static final int stage_width = 1024;
	/**UI stage height*/
	public static final int stage_height = 576;
	public static boolean isDesktop=false;
	public static int fps=0;
	private static BitmapFont font;
	public static String append = "";
	
	public static void init(){
		font = Res.font.get(14);
	}
	
	/**current screen width*/
	public static int getScreenWidth(){
		return Gdx.graphics.getWidth();
	}
	
	/**current screen height*/
	public static int getScreenHeight(){
		return Gdx.graphics.getHeight();
	}
	
	public static void drawFPS(SpriteBatch batch){
		if(Setting.persistence.showFPS){
			batch.end();
			batch.begin();
			fps=Gdx.graphics.getFramesPerSecond();
			
			font.setColor(Color.BLACK);
			font.draw(batch,"FPS:"+fps+append,13,(int) (stage_height-13));
			font.setColor(Color.WHITE);
			font.draw(batch,"FPS:"+fps+append,12,(int) (stage_height-12));
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
		return (float)Gdx.graphics.getWidth()/(float)GameUtil.stage_width;
	}
	
	public static float getScaleW(){
		return (float)Gdx.graphics.getWidth()/(float)GameUtil.stage_width;
	}
	
	public static float getScaleH(){
		return (float)Gdx.graphics.getHeight()/(float)GameUtil.stage_height;
	}
	
	public static NinePatch processNinePatchFile(String fname) {
		return processNinePatchFile(fname,2,2,1,1,12,12,12,12);
	}
	
	public static NinePatch processNinePatchFile(String fname,int wpad,int hpad,int woff,int hoff,int left,int right,int top,int bottom) {
	    final Texture t = new Texture(Gdx.files.internal(fname));
	    final int width = t.getWidth() - wpad;
	    final int height = t.getHeight() - hpad;
	    return new NinePatch(new TextureRegion(t, woff, hoff, width, height), left, right, top, bottom);
	}
	
	public static void openURL(String httpURL){
		Gdx.net.openURI(httpURL);
	}
	
	public static <T> List<T> randomSwap(List<T> list){
		for(int i=0;i<2333;i++)
			Collections.swap(list, MathUtils.random(0,list.size() - 1), MathUtils.random(0,list.size() - 1));
		return list;
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("asd");list.add("asd");
		for(int i=list.size();i<4;i++) list.add("append");
		System.out.println(list);
	}
}
