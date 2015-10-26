package com.rpsg.rpg.system.text;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.utils.display.FontUtil;

public class Font {
	public int size;
	public BitmapFont font;
	public String chars;
	
	public boolean include(char c,int fs){
		if(fs!=size)
			return false;
		for(char _char:chars.toCharArray())
			if(_char==c)
				return true;
		return false;
	}

	@SuppressWarnings("deprecation")
	public static Font generateFont(String chars, int size) {
		Font f=new Font();
		f.size = size;
		f.font = FontUtil.generator.generateFont(size,chars,false);
		f.chars=chars;
		if(Setting.persistence.antiAliasing){
			f.font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		return f;
	}
	
}
