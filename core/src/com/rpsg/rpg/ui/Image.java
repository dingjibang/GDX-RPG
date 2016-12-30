package com.rpsg.rpg.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.GdxQuery;

/**
 * GDX-RPG 图片类
 */
public class Image extends com.badlogic.gdx.scenes.scene2d.ui.Image{
	public GdxQuery query(){
		return $.add(this);
	}
	
	public Image (Texture texture){
		super(texture);
	}
	
	public Image (Drawable drawable){
		super(drawable);
	}
	
	public Image(){
		super((Drawable)null);
	}

}
