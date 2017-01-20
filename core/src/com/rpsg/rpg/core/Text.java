package com.rpsg.rpg.core;

import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Pools;
import com.rpsg.lazyFont.LazyBitmapFont;
import com.rpsg.rpg.ui.widget.Label;

/**
 * GDX-RPG 字体管理器<br>
 * 请在{@link Res#text}访问本类
 */
public class Text {
	private Map<Param, LazyBitmapFont> map = new TreeMap<>();

	public FreeTypeFontGenerator NORMAL_GENERATOR = new FreeTypeFontGenerator(Gdx.files.internal(Path.BASE_PATH + "font/xyj.ttf"));
	public FreeTypeFontGenerator ENGLISH_GENERATOR = new FreeTypeFontGenerator(Gdx.files.internal(Path.BASE_PATH + "font/Coold.ttf"));

	public Text() {
		LazyBitmapFont.setGlobalGenerator(NORMAL_GENERATOR);
	}

	public LazyBitmapFont get(int fontSize, FreeTypeFontGenerator gen) {
		//启用高清字体
		boolean hd = Game.setting.hdFont;

		if (hd)
			fontSize += fontSize;
		LazyBitmapFont font = map.get(new Param(fontSize, gen));
		if (font == null) {
			font = gen == null ? new LazyBitmapFont(fontSize) : new LazyBitmapFont(gen, fontSize);
			map.put(new Param(fontSize, gen), font);
		}
		if (hd)
			font.getData().setScale(.5f);

		return font;
	}

	public LazyBitmapFont get(int fontSize) {
		return get(fontSize, null);
	}

	public Label getLabel(int fontSize) {
		return getLabel("", fontSize);
	}

	public Label getLabel(Object text, int fontSize) {
		return getLabel(text, fontSize, null);
	}

	public Label getLabel(Object text, int fontSize, FreeTypeFontGenerator gen) {
		LabelStyle ls = new LabelStyle();
		ls.font = gen == null ? get(fontSize) : get(fontSize, gen);

		return new Label(text.toString(), ls);
	}


	private static class Param implements Comparable<Param> {
		public int size;
		public FreeTypeFontGenerator gen;

		public Param(int size, FreeTypeFontGenerator gen) {
			this.size = size;
			this.gen = gen;
		}

		public int compareTo(Param o) {
			return (((Param) o).size == size && ((Param) o).gen == gen) ? 0 : -1;
		}
	}

	public int getTextWidth(String str, int size) {
		return getTextWidth(get(size),str);	
		
	}
	
	public static int getTextWidth(BitmapFont font, String str){
		GlyphLayout layout = Pools.obtain(GlyphLayout.class);
		layout.setText(font, str);
		return (int) layout.width;
	}
}
