package com.rpsg.rpg.utils;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.rpsg.rpg.system.Setting;
import com.rpsg.rpg.system.text.Font;

public class FontUtil {
	public static FreeTypeFontGenerator generator=new FreeTypeFontGenerator(Gdx.files.internal("data/font/msyh.ttf"));
	
	static int x,y,w;
	
	public static void init(){
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
	
	private static BitmapFont getFont(int fontsize,char str){
		for(Font f: fontlist)
			if(f.include(str, fontsize))
				return (BitmapFont) f.font;
		return null;
	}
	
	public static List<Font> fontlist=new ArrayList<Font>();
	static char enter="\n".toCharArray()[0];
	public static void draw(SpriteBatch sb,String str,int fontsize,Color color,int x,int y,int width,int paddinglr,int paddingtb){
		GameUtil.resetBacth(sb);
		char[] dstr=StringUtil.dereplication(str).toCharArray();
		String addStr="";
		for(char c:dstr){
			boolean include=false;
			for(Font f:fontlist)
				if(f.include(c,fontsize))
					include=true;
			if(!include)
				addStr+=c;
		}
		if(addStr.length()>0)
			fontlist.add(Font.generateFont(addStr, fontsize));
		
		int currentX=x;
		int currentY=y;
		for(char c:str.toCharArray()){
			if(currentX-x>width || c==enter){
				currentX=x;
				currentY-=fontsize+paddingtb;
			}
			if(c==enter)
				continue;
			currentX+=fontsize+paddinglr;
			BitmapFont f=getFont(fontsize, c);
			f.setColor(color);
			f.draw(sb, String.valueOf(c), currentX, currentY);
		}
	}
	public static void draw(SpriteBatch sb,String str,int fontsize,Color color,int x,int y,int width){
		draw(sb,str,fontsize,color,x,y,width,Setting.STRING_PADDING_LR*2,Setting.DRAW_MULTI_STRING_PADDING_TB*2);
	}
}
