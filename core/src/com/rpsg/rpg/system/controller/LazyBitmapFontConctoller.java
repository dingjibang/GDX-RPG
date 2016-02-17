package com.rpsg.rpg.system.controller;

import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Pools;
import com.rpsg.lazyFont.LazyBitmapFont;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.ui.Label;

/**
 * LazyBitmapFont中央控制器
 * @author Administrator
 *
 */
public class LazyBitmapFontConctoller {
	
	private Map<Integer, LazyBitmapFont> map = new TreeMap<>();
	
	public LazyBitmapFontConctoller() {
		LazyBitmapFont.setGlobalGenerator(new FreeTypeFontGenerator(Gdx.files.internal(Setting.BASE_PATH+"font/xyj.ttf")));
	}
	
	public LazyBitmapFont get(int fontSize){
		boolean hd = Setting.persistence.hdFont;
		if(hd) fontSize += fontSize;
		LazyBitmapFont font = map.get(fontSize);
		if(font==null){
			font = new LazyBitmapFont(fontSize);
			map.put(fontSize, font);
		}
		if(hd) font.getData().setScale(.5f);
		
		return font;
	}
	
	public Label getLabel(int fontSize){
		return getLabel("",fontSize);
	}
	
	public Label getLabel(Object text,int fontSize){
		LabelStyle ls = new LabelStyle();
		ls.font = get(fontSize);
		
		return new Label(text.toString(),ls);
	}

	public int getTextWidth(String str, int size) {
		return getTextWidth(get(size),str);	
		
	}
	
	public int getTextWidth(BitmapFont font,String str){
		GlyphLayout layout = Pools.obtain(GlyphLayout.class);
		layout.setText(font, str);
		return (int) layout.width;
	}
}
