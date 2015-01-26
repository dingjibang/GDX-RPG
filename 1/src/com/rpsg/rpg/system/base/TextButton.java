package com.rpsg.rpg.system.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.rpsg.rpg.utils.display.FontUtil;

/** A button with a child {@link Label} to display text.
 * @author Nathan Sweet */
public class TextButton extends Button {
	private TextButtonStyle style;
	private String text;
	private int fontsize;
	public TextButton (String text, TextButtonStyle style,int fontsize) {
		super();
		init(text, style, fontsize);
	}
	
	public TextButton (String text, TextButtonStyle style) {
		super();
		init(text, style, 22);
	}
	Runnable run;
	public TextButton onClick(Runnable run){
		this.run=run;
		return this;
	}
	private void init(String text,TextButtonStyle style,int fontsize){
		setStyle(style);
		this.style = style;
		this.text=text;
		this.fontsize=fontsize;
		setSize(getPrefWidth(), getPrefHeight());
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				if(x>0 && x<getWidth() && y>0 && y<getHeight())
					if(run!=null)
						run.run();
			}
		});
	}

	public void setStyle (ButtonStyle style) {
		if (style == null) {
			throw new NullPointerException("style cannot be null");
		}
		if (!(style instanceof TextButtonStyle)) throw new IllegalArgumentException("style must be a TextButtonStyle.");
		super.setStyle(style);
		this.style = (TextButtonStyle)style;
	}

	public TextButtonStyle getStyle () {
		return style;
	}

	public void draw (Batch batch, float parentAlpha) {
		Color fontColor;
		if (isDisabled() && style.disabledFontColor != null)
			fontColor = style.disabledFontColor;
		else if (isPressed() && style.downFontColor != null)
			fontColor = style.downFontColor;
		else if (isChecked() && style.checkedFontColor != null)
			fontColor = (isOver() && style.checkedOverFontColor != null) ? style.checkedOverFontColor : style.checkedFontColor;
		else if (isOver() && style.overFontColor != null)
			fontColor = style.overFontColor;
		else
			fontColor = style.fontColor;
		if(null==fontColor)
			fontColor=Color.WHITE;
		super.draw(batch, parentAlpha);
		FontUtil.draw((SpriteBatch)batch, text,fontsize , fontColor,(int)(getX()+getWidth()/3.7-FontUtil.getTextWidth(text, fontsize)/2), (int)(getY()+getHeight()/2+fontsize/2), 1000);
	}


	public void setText (String text) {
		this.text=text;
	}

	public CharSequence getText () {
		return text;
	}
	
	public TextButton setWH(int width,int height){
		this.setSize(width, height);
		this.invalidate();
		return this;
	}
	
	

	/** The style for a text button, see {@link TextButton}.
	 * @author Nathan Sweet */
	static public class TextButtonStyle extends ButtonStyle {
		public BitmapFont font;
		/** Optional. */
		public Color fontColor, downFontColor, overFontColor, checkedFontColor, checkedOverFontColor, disabledFontColor;

		public TextButtonStyle () {
		}

		public TextButtonStyle (Drawable up, Drawable down, Drawable checked, BitmapFont font) {
			super(up, down, checked);
			this.font = font;
		}

		public TextButtonStyle (TextButtonStyle style) {
			super(style);
			this.font = style.font;
			if (style.fontColor != null) this.fontColor = new Color(style.fontColor);
			if (style.downFontColor != null) this.downFontColor = new Color(style.downFontColor);
			if (style.overFontColor != null) this.overFontColor = new Color(style.overFontColor);
			if (style.checkedFontColor != null) this.checkedFontColor = new Color(style.checkedFontColor);
			if (style.checkedOverFontColor != null) this.checkedFontColor = new Color(style.checkedOverFontColor);
			if (style.disabledFontColor != null) this.disabledFontColor = new Color(style.disabledFontColor);
		}
	}
}
