package com.rpsg.rpg.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Image extends com.badlogic.gdx.scenes.scene2d.ui.Image{
	
	public Image(String filename){
		super(new Texture(Gdx.files.internal(filename)));
		setAnti();
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

	public void draw(SpriteBatch sb){
		this.draw(sb, 1);
	}
	
	private void setAnti(){
		if(Setting.DISPLAY_ANTI_ALIASING)
			getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
	
	public Texture getTexture(){
		return ((TextureRegionDrawable)this.getDrawable()).getRegion().getTexture();
	}
}
