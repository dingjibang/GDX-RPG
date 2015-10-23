
package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.StringBuilder;
import com.rpsg.rpg.utils.display.FontUtil;
/**
 * GDX-RPG Label组件。
 * 使用FontUtil技术，无需缓存字体。
 *
 */
public class Label extends Actor{
	static private final Color tempColor = new Color();
	
	private int width,pad;
	
	private final StringBuilder text = new StringBuilder();
	public int fontSize;

	public Label (CharSequence text,int fontSize) {
		if (text != null) this.text.append(text);
		this.fontSize=fontSize;
		setWidth(1000);
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
	}
	
	public Label text(CharSequence txt){
		setText(txt);
		return this;
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

	int yoff=0;
	public Label setYOffset(int yoff){
		this.yoff=yoff;
		return this;
	}
	
	boolean xoff=false;
	public Label setXOffset(boolean b){
		this.xoff=b;
		return this;
	}
	
	public void draw (Batch batch, float parentAlpha) {
		Color color = tempColor.set(getColor());
		color.a *= parentAlpha;
		int x = 0,y=(int) getY();
		if(alignX==0)
			x= !rightAble?(int)getX():(int)getX()-FontUtil.getTextWidth(getText(), fontSize,pad);
		else
			x=alignX+(xoff?(int)getX():0)-FontUtil.getTextWidth(getText(), fontSize,pad)/2;
		
		if(layout2){//fix position
			x-=fontSize/2;//-8;
			y-=yoff-getHeight();
		}
		FontUtil.draw((SpriteBatch)batch, getText().toString(), fontSize, color,x , y,(int)getWidth(),pad,yoff);
	}
	
	public float getWidth() {
		return width;
	}
	public Label setWidth(int width) {
		this.width = width;
		sizeChanged();
		return this;
	}
	
	@Override
	public void setWidth(float width) {
		super.setWidth(width);
		sizeChanged();
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

	boolean layout2=false;
	public Label layout() {
		layout2=true;
		setHeight(FontUtil.getTextHeight(getText(),fontSize,(int)getWidth(),pad,yoff));
		setOrigin(Align.topLeft);
		setSize(getWidth(), getHeight());
		
		return this;
	}
}
