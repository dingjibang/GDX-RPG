package com.rpsg.rpg.utils;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontUtil {
	public static  FreeTypeFontGenerator generator;
	public static BitmapFont bf;
	public static BitmapFontCache bfc;
	static int x,y,w;
	static String str="¸úÍß¸ñ°¡ÎÒ¸ÁÍÛ¸Â¸Â";
	public static void init(){
		generator=GameUtil.generator;
	}
	
	public static void drawString(String str,int x,int y,int width,SpriteBatch batch){
		FontUtil.x=x;FontUtil.y=y;w=width;
		if(FontUtil.str.equals(str)){
			bf.setColor(0,0,0,1);
			bf.drawMultiLine(batch, resize(str), x+1, y+1);
			bf.setColor(1,1,1,1);
			bf.drawMultiLine(batch, resize(str), x, y);
		}else{
			FontUtil.str=str;
			if(bf!=null){
				bf.setOwnsTexture(true);
				bf.dispose();
			}
			bf=generator.generateFont(20, GameUtil.RemoveReString(str),false);
			bf.setColor(0,0,0,1);
			bf.drawMultiLine(batch, resize(str), x+1, y+1);
			bf.setColor(1,1,1,1);
			bf.drawMultiLine(batch, resize(str), x, y);
		}
	}
	
	
	public static String resize(String s){
		char[] ca=s.toCharArray();
		int len=0;
		for(int i=0;i<ca.length;i++){
			if (ca[i]=='\n')len=0; else len++;
			if(len>w/20) {
				s=s.substring(0,i)+"\n"+s.substring(i,s.length());
				len=0;
			}
		}
		return s;
	}
}
