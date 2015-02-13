package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.rpsg.rpg.core.Setting;
/**
 * GDX-RPG Image组件
 * 允许开启抗锯齿、销毁的图形组件。
 *
 */
public class Image extends com.badlogic.gdx.scenes.scene2d.ui.Image{
	
	public boolean visible=true;
	
	public Image(String filename){
		super(new Texture(Gdx.files.internal(filename)));
		setAnti();
	}
	
	public Image(NinePatch ninePatch){
		super(ninePatch);
	}
	
	public Image(Texture txt){
		super(txt);
		setAnti();
	}
	public Image(TextureRegion txt){
		super(txt);
		setAnti();
	}
	
	public Image(Sprite txt){
		super(txt);
		setAnti();
	}
	
	public Image() {
		super();
	}
	
	public Image(Image image) {
		super(image.getDrawable());
	}
	
	public Image(Drawable dr) {
		super(dr);
	}

	public Image(NinePatchDrawable ninePatchDrawable) {
		super(ninePatchDrawable);
	}

	public void draw(Batch sb){
		if(visible)
			this.draw(sb, 1);
	}
	
	private void setAnti(){
		if(Setting.persistence.antiAliasing)
			getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
	
	public Texture getTexture(){
		return ((TextureRegionDrawable)this.getDrawable()).getRegion().getTexture();
	}
	
	public TextureRegion getRegion(){
		return ((TextureRegionDrawable)this.getDrawable()).getRegion();
	}
	
	public Image dispose(){
		getTexture().dispose();
		return this;
	}
	
	public Image size(int width,int height){
		super.setSize(width, height);
		return this;
	}
	
	public Image scale(float s){
		super.setScale(s);
		return this;
	}
	
	public Image scaleX(float s){
		super.setScaleX(s);
		return this;
	}
	
	public Image color(float r,float g,float b,float a){
		super.setColor(r, g, b, a);
		return this;
	}
	
	public Image color(Color c){
		super.setColor(c);
		return this;
	}
	
	public Image scaleY(float s){
		super.setScaleY(s);
		return this;
	}
	
	public Image position(int x,int y){
		super.setPosition(x, y);
		return this;
	}
	
	public Image X(int x){
		super.setX(x);
		return this;
	}
	
	public Image Y(int y){
		super.setY(y);
		return this;
	}
	
	public Image disableTouch(){
		super.setTouchable(null);
		return this;
	}
}
