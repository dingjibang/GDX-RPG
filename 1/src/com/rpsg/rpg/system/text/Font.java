package com.rpsg.rpg.system.text;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.utils.display.FontUtil;

public class Font {
	public int size;
	public BitmapFont font;
	
	public boolean include(char c,int fs){
		return fs==size && font.containsCharacter(c);
	}

	public static Font generateFont(String chars, int size) {
		Font f=new Font();
		f.size = size;
		f.font = FontUtil.generator.generateFont(size,chars,false);
		if(Setting.DISPLAY_ANTI_ALIASING)
			f.font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return f;
	}
	
}
