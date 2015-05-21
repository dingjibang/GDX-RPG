package com.rpsg.gdxQuery;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.*;

public class $ {
	
	public static GdxQuery add (Object... a){
		return new GdxQuery(a);
	}
	
	public static Texture getTexture (Drawable d){
		if(d instanceof TextureRegionDrawable)
			return ((TextureRegionDrawable)d).getRegion().getTexture();
		return null;
	}
	
	public static GdxQuery image (String filePath){
		return image(new Texture(Gdx.files.internal(filePath)));
	}
	
	public static GdxQuery image (String filePath,AssetManager manager){
		if(!manager.isLoaded(filePath)){
			manager.load(filePath, Texture.class);
			while(manager.update());
		}
		return image((Texture)manager.get(filePath));
	}
	
	public static GdxQuery image (Texture texture){
		return new GdxQuery(new Image(texture));
	}
	

}
