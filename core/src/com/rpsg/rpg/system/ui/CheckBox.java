
package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.rpsg.rpg.utils.display.FontUtil;

/**
 * GDXRPG引擎 checkbox组件
 * 使用fontUtil文字缓存技术，无需预设定文字。 
 */
public class CheckBox extends TextButton {
	private Image image;
	private CheckBoxStyle style;
	private String text;
	private int fontSize;


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public CheckBox (String text, CheckBoxStyle style,int fontsize) {
		super(text, style);
		clearChildren();
		image = new Image(style.checkboxOff);
		setText(text);
		setFontSize(fontsize);
		setSize(image.getWidth(), image.getHeight());
	}

	public void setStyle (ButtonStyle style) {
		if (!(style instanceof CheckBoxStyle)) throw new IllegalArgumentException("style must be a CheckBoxStyle.");
		super.setStyle(style);
		this.style = (CheckBoxStyle)style;
	}
	
	Image fg,other;
	public CheckBox setForeground(Image draw){
		fg=draw;
		return this;
	}
	
	public CheckBox setOther(Image draw){
		other=draw;
		return this;
	}
	int fgoff=0;
	
	public CheckBox setFgOff(int off){
		fgoff=off;
		return this;
	}
	
	public CheckBoxStyle getStyle () {
		return style;
	}
	int otherX,otherY;
	public CheckBox setOtherPosition(int x,int y){
		otherX=x;otherY=y;
		return this;
	}
	
	public void draw (Batch batch, float parentAlpha) {
		Drawable checkbox = null;
		if (isDisabled()) {
			if (isChecked() && style.checkboxOnDisabled != null)
				checkbox = style.checkboxOnDisabled;
			else
				checkbox = style.checkboxOffDisabled;
		}
		if (checkbox == null) {
			if (isChecked() && style.checkboxOn != null)
				checkbox = style.checkboxOn;
			else if (isOver() && style.checkboxOver != null && !isDisabled())
				checkbox = style.checkboxOver;
			else
				checkbox = style.checkboxOff;
		}
		image.setDrawable(checkbox);
		image.draw(batch);
		image.setPosition(getX(), getY());
//		super.draw(batch, parentAlpha);
		FontUtil.draw((SpriteBatch)batch, getText(), fontSize, style.fontColor==null?Color.WHITE:style.fontColor,(int)(getX()+image.getWidth()), (int)(image.getY()+fontSize), 1000);
		if(fg!=null){
			fg.setX(getX()+(getPrefWidth()/2-fg.getWidth()/2)+fgoff);
			fg.setY(getY()+(getHeight()/2-fg.getHeight()/2));
			fg.setColor(getColor());
			fg.draw(batch);
		}
		if(other!=null){
			other.setX(getX()+otherX);
			other.setY(getY()+otherY);
			other.setColor(getColor());
			other.draw(batch);
		}
	}
//	Runnable run;
//	public CheckBox onClick(Runnable r){
//		this.run=r;
//		return this;
//	}

	public Image getImage () {
		return image;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	/** The style for a select box, see {@link CheckBox}.
	 * @author Nathan Sweet */
	static public class CheckBoxStyle extends TextButtonStyle {
		public Drawable checkboxOn, checkboxOff;
		/** Optional. */
		public Drawable checkboxOver, checkboxOnDisabled, checkboxOffDisabled;

		public CheckBoxStyle () {
		}

		public CheckBoxStyle (Drawable checkboxOff, Drawable checkboxOn, BitmapFont font, Color fontColor) {
			this.checkboxOff = checkboxOff;
			this.checkboxOn = checkboxOn;
			this.font = font;
			this.fontColor = fontColor;
		}

		public CheckBoxStyle (CheckBoxStyle style) {
			this.checkboxOff = style.checkboxOff;
			this.checkboxOn = style.checkboxOn;
			this.checkboxOver = style.checkboxOver;
			this.checkboxOffDisabled = style.checkboxOffDisabled;
			this.checkboxOnDisabled = style.checkboxOnDisabled;
			this.font = style.font;
			this.fontColor = new Color(style.fontColor);
		}
	}
}
