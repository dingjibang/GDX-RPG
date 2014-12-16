package com.rpsg.rpg.system;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.rpsg.rpg.utils.FontUtil;

public class Font {
	public String chars;
	public int size;
	public BitmapFont font;
	
	public boolean include(char c,int fs){
		return fs==size && chars.indexOf(new String(new char[]{c}))!=-1;
	}

	public static Font generateFont(String chars, int size) {
		Font f=new Font();
		f.chars = chars;
		f.size = size;
		f.font = FontUtil.generator.generateFont(size,chars,false);
		if(Setting.DISPLAY_ANTI_ALIASING)
			f.font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return f;
	}
}
