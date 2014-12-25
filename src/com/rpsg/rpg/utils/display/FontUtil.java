package com.rpsg.rpg.utils.display;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.text.Font;
import com.rpsg.rpg.utils.display.shader.DistanceFieldShader;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.StringUtil;

public class FontUtil {
	public static final FreeTypeFontGenerator generator=new FreeTypeFontGenerator(Gdx.files.internal("data/font/msyh.ttf"));
	
	static int x,y,w;
	static DistanceFieldShader distanceFieldShader = new DistanceFieldShader();
	public static void init(){
	}
	
	private static BitmapFont getFont(int fontsize,char str){
//		System.out.print(fontlist.size()+"[");
//		for(Font f: fontlist)
//			System.out.print(f.toString()+",");
//		System.out.println("]");
		for(Font f: fontlist)
			if(f.include(str, fontsize))
				return (BitmapFont) f.font;
		return null;
	}
	
	public static List<Font> fontlist=new ArrayList<Font>();
	static final char enter="\n".toCharArray()[0];
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
			fontlist.add(Font.generateFont((addStr.length()==5 && fontsize==22)?addStr+" ":addStr, fontsize));
		
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
			distanceFieldShader.setSmoothing(0.4f);
//			sb.setShader(distanceFieldShader);
			f.draw(sb, new String(new char[]{c}), currentX, currentY);
//			sb.setShader(null);
		}
	}
	public static void draw(SpriteBatch sb,String str,int fontsize,Color color,int x,int y,int width){
		draw(sb,str,fontsize,color,x,y,width,Setting.STRING_PADDING_LR*2,Setting.DRAW_MULTI_STRING_PADDING_TB*2);
	}
	
	public static int getTextWidth(String txt,int size,int paddinglr){
		return (size+(paddinglr*2))*txt.length();
	}
}