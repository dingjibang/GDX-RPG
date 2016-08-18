package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.rpg.system.base.Res;

public class Label extends com.badlogic.gdx.scenes.scene2d.ui.Label {
	
	private boolean overflow = false,warp = false;
	private CharSequence oldText = "",overflowd = "";
	private boolean enableMarkup = false;
	private Integer maxWidth;
	
	public Label(Object text, LabelStyle style) {
		super(text.toString(), style);
		setTouchable(Touchable.disabled);
	}
	
	public Label (Object text,int fontsize){
		super(text.toString(),genStyle(fontsize));
		setTouchable(Touchable.disabled);
	}
	
	public Label markup(boolean enable){
		this.enableMarkup = enable;
		return this;
	}
	
	private static LabelStyle genStyle(int fontSize){
		LabelStyle style = new LabelStyle();
		style.font=Res.font.get(fontSize);
		return style;
	}
	
	public Label position(int x,int y){
		setPosition(x, y);
		return this;
	}
	
	public Label color(float r,float g,float b,float a){
		setColor(r,g,b,a);
		return this;
	}
	
	public Label color(Color c){
		setColor(c);
		return this;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(warp && maxWidth != null){
			int width = Res.font.getTextWidth(getStyle().font,oldText.toString());
			if(width > maxWidth)
				setWidth(maxWidth);
		}
		if(overflow && !oldText.equals(getText().toString())){
			oldText = getText().toString();//cpy
			if(Res.font.getTextWidth(getStyle().font, oldText.toString())>getWidth())
				for(int i=0;i<oldText.length();i++){
					CharSequence sub = oldText.subSequence(0, oldText.length()-i);
					int width = Res.font.getTextWidth(getStyle().font,sub.toString());
					if(width < getWidth()){
						overflowd = (sub.length()<=3?sub:sub.subSequence(0, sub.length()-3)+"…");
						break;
					}
				}
			else
				overflowd = oldText;
		}
		
		getStyle().font.getData().markupEnabled = enableMarkup;
		
		if(overflow){
			setText(overflowd);
			super.draw(batch, parentAlpha);
			setText(oldText);
		}else{
			super.draw(batch, parentAlpha);
		}
	}
	
	public Label action(Action act){
		addAction(act);
		return this;
		
	}
	
	public Label userObject(Object o){
		setUserObject(o);
		return this;
	}
	
	public Label width(int w){
		super.setSize(w,getHeight());
		return this;
	}
	
	public Label warp(boolean warp){
		setWrap(warp);
		this.warp = warp;
		return this;
	}
	
	public void setWarp(boolean warp) {
		this.warp = warp;
		super.setWrap(warp);
	}
	
	public Label overflow(boolean hidden){
		this.overflow = hidden;
		return this;
	}
	
	public boolean overflow(){
		return overflow;
	}
	
	public Label right(){
		setAlignment(Align.right);
		return this;
	}

	public Label align(int x, int y) {
		setPosition(x,y);
		setAlignment(Align.center);
		setText(getText());
		return this;
	}
	
	public Label center(){
		setAlignment(Align.center);
		return this;
	}
	
	public Label disableTouch(){
		super.setTouchable(null);
		return this;
	}
	
	public Label align(int align){
		setAlignment(align);
		return this;
	}
	
	public Label left(){
		setAlignment(Align.left);
		return this;
	}
	
	public Label text(String text){
		setText(text);
		return this;
	}

	public Label height(int i) {
		setHeight(i);
		return this;
	}

	public Label x(int i) {
		setX(i);
		return this;
	}
	
	public Label x(float x){
		setX(x);
		return this;
	}
	
	public Label y(int i){
		setY(i);
		return this;
	}

	public Label a(float f) {
		setColor(getColor().r,getColor().g,getColor().b,f);
		return this;
	}

	public Label size(int w, int h) {
		setSize(w,h);
		return this;
	}

	public Label maxWidth(int i) {
		maxWidth = i;
		return this;
	}

	public GdxQuery query() {
		return new GdxQuery(this);
	}

}
