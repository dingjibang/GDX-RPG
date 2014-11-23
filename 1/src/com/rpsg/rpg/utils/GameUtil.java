package com.rpsg.rpg.utils;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class GameUtil {
	public static int screen_width;
	public static int screen_height;
	public static FreeTypeFontGenerator generator=new FreeTypeFontGenerator(Gdx.files.internal("data/font/msyh.ttf"));
	public static BitmapFont font=generator.generateFont(12, "FPS:0123456789", false);
	public static void debugDrawString(SpriteBatch sb,String s,float x,float y){
		font.setColor(Color.BLACK);
		font.draw(sb, s, x, y);
	}
	
	public static void debugDrawString(SpriteBatch sb,String s,float x,float y,Color color){
		font.setColor(color);	
		font.draw(sb, s, x, y);
	}
	
	public static String RemoveReString(String str){
		 List<String> data = new ArrayList<String>();
	        for (int i = 0; i < str.length(); i++) {
	            String s = str.substring(i, i + 1);
	            if (!data.contains(s))
	                data.add(s);
	        }
	        String result = "";
	        for (String s : data) 
	            result += s;
	        return result;
	}
}
