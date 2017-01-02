package com.rpsg.rpg.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.rpg.core.Game;

/**
 * GDX-RPG 图片类
 */
public class Image extends com.badlogic.gdx.scenes.scene2d.ui.Image{
	public GdxQuery query(){
		return $.add(this);
	}
	
	public Image (Texture texture){
		super(texture);
		if(Game.setting.filter)
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
	
	public void setDrawable(Drawable drawable) {
		super.setDrawable(drawable);
		
		if(Game.setting.filter && drawable instanceof TextureRegionDrawable)
			((TextureRegionDrawable)drawable).getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
	
	public Image (Drawable drawable){
		super(drawable);
	}
	
	public Image(){
		super((Drawable)null);
	}
	
	public void setWidth(float width) {
		super.setWidth((int)width);
	}
	
	public void setHeight(float height) {
		super.setHeight((int)height);
	}
	
	public void setX(float x) {
		super.setX((int)x);
	}
	
	public void setY(float y) {
		super.setY((int)y);
	}
	
	public void setPosition(float x, float y) {
		super.setPosition((int)x, (int)y);
	}

}
