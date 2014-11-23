package com.rpsg.rpg.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Image extends Sprite{
	
	public Image(String filename){
		super(new Texture(Gdx.files.internal(filename)));
		setA();
	}
	
	public Image(Texture txt){
		super(txt);
		setA();
	}
	public Image(TextureRegion txt){
		super(txt);
		setA();
	}
	
	private void setA(){
		if(Setting.DISPLAY_ANTI_ALIASING)
			this.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
}
