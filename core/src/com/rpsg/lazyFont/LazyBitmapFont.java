package com.rpsg.lazyFont;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Face;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.SizeMetrics;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.GlyphAndBitmap;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.rpsg.rpg.core.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * GDX-LAZY-FONT for LibGDX 1.5.0+<br/>
 * <b>Auto generate & manage your bitmapfont without pre-generate.</b>
 *
 * @author dingjibang
 * @version 2.1.5
 * @see https://github.com/dingjibang/GDX-LAZY-FONT
 */
public class LazyBitmapFont extends BitmapFont {

	private FreeTypeFontGenerator generator;
	private FreeTypeBitmapFontData data;
	private FreeTypeFontParameter parameter;

	private static FreeTypeFontGenerator GLOBAL_GEN = null;

	public final int fontSize;

	public static void setGlobalGenerator(FreeTypeFontGenerator generator) {
		GLOBAL_GEN = generator;
	}

	public LazyBitmapFont(int fontSize) {
		this(GLOBAL_GEN, fontSize);
	}

	public LazyBitmapFont(FreeTypeFontGenerator generator, int fontSize) {
		this.fontSize = fontSize;

		if (generator == null)
			throw new GdxRuntimeException("lazyBitmapFont global generator must be not null to use this constructor.");
		this.generator = generator;
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		param.size = fontSize;
		this.parameter = param;
		this.data = new LazyBitmapFontData(generator, fontSize, this);
		try {
			Field f = getClass().getSuperclass().getDeclaredField("data");
			f.setAccessible(true);
			f.set(this, data);
		} catch (Exception e) {
			e.printStackTrace();
		}

		genrateData();
	}

	private void genrateData() {
		Face face = null;
		try {
			Field field = generator.getClass().getDeclaredField("face");
			field.setAccessible(true);
			face = (Face) field.get(generator);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// set general font data
		SizeMetrics fontMetrics = face.getSize().getMetrics();

		// Set space glyph.
		Glyph spaceGlyph = data.getGlyph(' ');
		if (spaceGlyph == null) {
			spaceGlyph = new Glyph();
			spaceGlyph.xadvance = (int) data.spaceWidth;
			spaceGlyph.id = (int) ' ';
			data.setGlyph(' ', spaceGlyph);
		}
		if (spaceGlyph.width == 0)
			spaceGlyph.width = (int) (spaceGlyph.xadvance + data.padRight);

		// set general font data
		data.flipped = parameter.flip;
		data.ascent = FreeType.toInt(fontMetrics.getAscender());
		data.descent = FreeType.toInt(fontMetrics.getDescender());
		data.lineHeight = FreeType.toInt(fontMetrics.getHeight());

		// determine x-height
		for (char xChar : data.xChars) {
			if (!face.loadChar(xChar, FreeType.FT_LOAD_DEFAULT))
				continue;
			data.xHeight = FreeType.toInt(face.getGlyph().getMetrics().getHeight());
			break;
		}
		if (data.xHeight == 0)
			throw new GdxRuntimeException("No x-height character found in font");
		for (char capChar : data.capChars) {
			if (!face.loadChar(capChar, FreeType.FT_LOAD_DEFAULT))
				continue;
			data.capHeight = FreeType.toInt(face.getGlyph().getMetrics().getHeight());
			break;
		}

		// determine cap height
		if (data.capHeight == 1)
			throw new GdxRuntimeException("No cap character found in font");
		data.ascent = data.ascent - data.capHeight;
		data.down = -data.lineHeight;
		if (parameter.flip) {
			data.ascent = -data.ascent;
			data.down = -data.down;
		}

	}

	@Override
	public void dispose() {
		setOwnsTexture(true);
		super.dispose();
		data.dispose();

	}

	public static class LazyBitmapFontData extends FreeTypeBitmapFontData {

		private FreeTypeFontGenerator generator;
		private int fontSize;
		private LazyBitmapFont font;
		private int page = 1;

		//modified by STH99 on 2017-8-8

		LazyBitmapFontTexture lazyBitmapFontTexture = new LazyBitmapFontTexture();

		public LazyBitmapFontData(FreeTypeFontGenerator generator, int fontSize, LazyBitmapFont lbf) {
			this.generator = generator;
			this.fontSize = fontSize;
			this.font = lbf;

		}

		public Glyph getGlyph(char ch) {
			Glyph glyph = super.getGlyph(ch);
			if (glyph == null && ch != 0){
				Log.i("no glyph ["+ch+", "+fontSize+"], generate↓");
				glyph = generateGlyph(ch);
			}
			return glyph;
		}

		protected Glyph generateGlyph(char ch) {
			GlyphAndBitmap gab = generator.generateGlyphAndBitmap(ch, fontSize, false);
			if (gab == null || gab.bitmap == null)
				return null;

			//modified by STH99 on 2017-6-10
			Pixmap map = gab.bitmap.getPixmap(Format.RGBA8888, Color.WHITE, 1.0f);

			TextureRegion rg = lazyBitmapFontTexture.draw(map);
			if(rg == null){
				Log.i("new texture!!!!!!!!!!!");
				rg = (lazyBitmapFontTexture = new LazyBitmapFontTexture()).draw(map);
			}

			map.dispose();

			font.getRegions().add(rg);

			gab.glyph.page = page++;
			super.setGlyph(ch, gab.glyph);
			setGlyphRegion(gab.glyph, rg);

			return gab.glyph;
		}

	}


	private static class LazyBitmapFontTexture{

		private static final int WIDTH = 0xff;
		private static final int HEIGHT = 60;
		//加入5像素间距，防止纹理放大后产生花边
		private static final int PADDING = 5;

		private Texture tex = new Texture(new Pixmap(WIDTH, HEIGHT, Format.RGBA8888));

		private int currX = 0, currY = 0, lineHeight = 0;

		LazyBitmapFontTexture(){
			tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		}

		//modified by STH99 on 2017-8-8
		//所有生成的分开的材质整合进一个大材质
		//GL在绘制时不用再切换材质了，节省每个字符间切换材质花费的时间
		//性能提升1倍多（改前30帧，现在60帧）
		TextureRegion draw(Pixmap map){
			int w = map.getWidth();
			int h = map.getHeight();

			Log.i(hashCode() + " w:" + w + ", h:" + h + ", x:" + currX + ", y:" + currY);


			if (currX + w + PADDING >= WIDTH) {

				currX = 0;
				currY += lineHeight + PADDING;

				lineHeight = 0;

				if(currY + h + PADDING >= HEIGHT)
					return null;
			}

			tex.draw(map, currX, currY);
			TextureRegion reg = new TextureRegion(tex, currX, currY, w, h);

			Log.i(hashCode() + " w:" + w + ", h:" + h + ", x:" + currX + ", y:" + currY);

			{
				currX += w + PADDING;
				lineHeight = lineHeight > h ? lineHeight : h;
			}

			Log.i(hashCode() + " w:" + w + ", h:" + h + ", x:" + currX + ", y:" + currY);

			return reg;
		}

	}

}