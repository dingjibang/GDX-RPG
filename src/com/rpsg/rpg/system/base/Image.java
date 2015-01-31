package com.rpsg.rpg.system.base;

import com.badlogic.gdx.Gdx;
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
		if(Setting.DISPLAY_ANTI_ALIASING)
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
}
