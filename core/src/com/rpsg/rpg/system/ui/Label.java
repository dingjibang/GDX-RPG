
package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.StringBuilder;
import com.rpsg.rpg.utils.display.FontUtil;
/**
 * GDX-RPG Label组件。
 * 使用FontUtil技术，无需缓存字体。
 *
 */
public class Label extends Widget {
	static private final Color tempColor = new Color();
	
	private int width,pad;
	
	private final StringBuilder text = new StringBuilder();
	public int fontSize;

	public Label (CharSequence text,int fontSize) {
		if (text != null) this.text.append(text);
		this.fontSize=fontSize;
		setSize(getWidth(), getHeight());
	}



	/** @param newText May be null. */
	public void setText (CharSequence newText) {
		if (newText instanceof StringBuilder) {
			if (text.equals(newText)) return;
			text.setLength(0);
			text.append((StringBuilder)newText);
		} else {
			if (newText == null) newText = "";
			if (textEquals(newText)) return;
			text.setLength(0);
			text.append(newText);
		}
		invalidateHierarchy();
	}

	public boolean textEquals (CharSequence other) {
		int length = text.length;
		char[] chars = text.chars;
		if (length != other.length()) return false;
		for (int i = 0; i < length; i++)
			if (chars[i] != other.charAt(i)) return false;
		return true;
	}

	public String getText () {
		return text.toString();
	}

	public void invalidate () {
		super.invalidate();
	}



	public void layout () {

	}
	
	int yoff=0;
	public Label setYOffset(int yoff){
		this.yoff=yoff;
		return this;
	}
	
	public void draw (Batch batch, float parentAlpha) {
		validate();
		Color color = tempColor.set(getColor());
		color.a *= parentAlpha;
//		cache.tint(color);
//		cache.setPosition(getX(), getY());
//		cache.draw(batch);
		if(alignX==0)
			FontUtil.draw((SpriteBatch)batch, getText().toString(), fontSize, color, !rightAble?(int)getX():(int)getX()-FontUtil.getTextWidth(getText(), fontSize,pad), (int)getY(),(int)getWidth(),pad,yoff);
		else
			FontUtil.draw((SpriteBatch)batch, getText().toString(), fontSize, color, alignX-FontUtil.getTextWidth(getText(), fontSize,pad)/2, (int)getY(),(int)getWidth(),pad,yoff);
	}
	
	public float getWidth() {
		return width;
	}
	public Label setWidth(int width) {
		this.width = width;
		return this;
	}
	
	public Label addAct(Action act) {
		super.addAction(act);
		return this;
	}
	
	public Label setPad(int pad) {
		this.pad = pad;
		return this;
	}
	
	public Label userObj(Object obj){
		setUserObject(obj);
		return this;
	}
	
	int alignX;
	public int getAlignX() {
		return alignX;
	}



	public Label setAlignX(int alignX) {
		this.alignX = alignX;
		return this;
	}

	public Label color(float r,float g,float b,float a){
		super.setColor(r,g,b,a);
		return this;
	}

	public Label align(int x){
		setAlignX(x);
		return this;
	}
	
	public Label align(int x,int y){
		setAlignX(x);
		setY(y);
		return this;
	}
	
	public int getPad() {
		return pad;
	}
	
	public Label setPos(int x,int y){
		setPosition(x, y);
		return this;
	}
	
	boolean rightAble=false;
	public Label right(boolean r){
		rightAble=r;
		return this;
	}
}
