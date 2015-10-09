package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.rpsg.rpg.system.base.Res;


/**
 * GDX-RPG 懒加载Image组件。
 *
 */
public class ProxyImage extends Image {

	public ProxyImage(String resPath) {
		this.texturePath=resPath;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		lazyLoad();
		super.draw(batch, parentAlpha);
	}

	@Override
	public Image draw(Batch sb) {
		lazyLoad();
		super.draw(sb);
		return this;
	}
	
	public void lazyLoad() {
		if(lazy){
			lazy=false;
			TextureParameter parameter=new TextureParameter();
			parameter.loadedCallback= new AssetLoaderParameters.LoadedCallback() {
				@Override
				public void finishedLoading(AssetManager assetManager, String fileName, Class type) {
					ProxyImage.this.setDrawable(new TextureRegionDrawable(new TextureRegion((Texture) Res.ma2.get(texturePath))));
					ProxyImage.this.reGenerateSize();
					ProxyImage.this.isLoaded=true;
					ProxyImage.this.loaded.run();
				}
			};
			if(Res.ma2.isLoaded(texturePath))
				parameter.loadedCallback.finishedLoading(null, null, null);
			else
				Res.ma2.load(texturePath,Texture.class,parameter);
		}
	}


}
