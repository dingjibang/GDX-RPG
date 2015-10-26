package com.rpsg.rpg.utils.display;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.text.Font;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.utils.game.StringUtil;

public class FontUtil {
	public static final FreeTypeFontGenerator generator;
	static{
		generator=new FreeTypeFontGenerator(Gdx.files.internal(Setting.BASE_PATH+"font/xyj.ttf"));
	}
	
	private static BitmapFont getBitFont(int fontsize,char str){
		for(Font f: fontlist)
			if(f.include(str, fontsize))
				return (BitmapFont) f.font;
		return null;
	}
	
	public static List<Font> fontlist=new ArrayList<Font>();
	static final char enter="\n".toCharArray()[0];
	static Color lastColor = null;
	public static Vector2 draw(SpriteBatch sb,String str,int fontsize,Color color,int x,int y,int width,int paddinglr,int paddingtb){
		return draw(sb, str, fontsize, color, x, y, width, paddinglr, paddingtb, false);
	}
	
	public static Vector2 draw(SpriteBatch sb,String str,int fontsize,Color color,int x,int y,int width,int paddinglr,int paddingtb,boolean calc){
		Vector2 vec2 = new Vector2();
		if(fontsize==0)
			return vec2;
		
		if(!calc)
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
		if(addStr.length()>0){
			Logger.info("文字贴图[\""+addStr+"\"]生成成功");
			fontlist.add(Font.generateFont((addStr.length()==5 && fontsize==22)?addStr+" ":addStr, fontsize));
		}
		
		int currentX=x;
		int currentY=y;
		int maxX = 0;
		int skip = 0;
		char[] carr = str.toCharArray();
		for(int i=0;i<carr.length;i++){
			char c = carr[i];
			
			if(skip>0){
				skip--;
				continue;
			}
			
			if(c=='<' && i+8 < carr.length && carr[i+1]=='!' && carr[i+2]=='-'){
				String _c = (carr[i+3]+"")+(carr[i+4]+"")+(carr[i+5]+"")+(carr[i+6]+"")+(carr[i+7]+"")+(carr[i+8]+"");
				lastColor = Color.valueOf(_c);
				skip = 8;
				continue;
			}
			
			if(lastColor!=null && c=='>'){
				lastColor=null;
				continue;
			}
			
			
			if(x>=0 && (currentX-x>width || c==enter)){
				maxX=currentX;
				currentX=x;
				currentY-=fontsize+paddingtb;
			}
			
			if(c==enter)
				continue;
			
			currentX+=fontsize+paddinglr;
			
			if(!calc){
				BitmapFont f=getBitFont(fontsize, c);
				f.setColor(lastColor==null?color:lastColor);
				String tstr=new String(new char[]{c});
				int bound=(int) f.getData().getGlyph(c).width;
				f.draw(sb, tstr, currentX-bound/2, currentY);
				f.setColor(Color.WHITE);
			};
		}
		return vec2.set(x<0?currentX-x:maxX,( y-(currentY-fontsize)));
	}
	
	public static void draw(SpriteBatch sb,String str,int fontsize,Color color,int x,int y,int width){
		draw(sb,str,fontsize,color,x,y,width,Setting.STRING_PADDING_LR*2,Setting.STRING_PADDING_TB*2);
	}
	
	public static int getTextWidth(String txt,int size,int paddinglr){
		return (int) draw(null, txt, size, null, -1, 0, 0, paddinglr, 0, true).x;
	}
	
	public static int getTextHeight(String txt, int size, int width,int padlr,int padtb) {
		return (int) draw(null,txt, size, null, 0, 0, width, padlr, padtb, true).y;
	}
	public static int getTextWidth(String txt,int size){
		return getTextWidth(txt,size,Setting.STRING_PADDING_LR);
	}
	
	public static BitmapFont generateFont(char str,int size){
		BitmapFont f;
		if((f=getBitFont(size, str))==null){
			Font font= Font.generateFont(String.valueOf(str), size);
			fontlist.add(font);
			return font.font;
		}else
			return f;
	}
}
