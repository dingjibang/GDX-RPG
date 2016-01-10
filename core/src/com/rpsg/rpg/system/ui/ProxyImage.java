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
	public float[][] position;

	public ProxyImage(String resPath) {
		this.texturePath=resPath;
	}
	public ProxyImage(String resPath,float[][] pos) {
		this.texturePath=resPath;
		this.position = pos;
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
				public void finishedLoading(AssetManager assetManager, String fileName, @SuppressWarnings("rawtypes") Class type) {
					if(ProxyImage.this.position==null||ProxyImage.this.position.length<=0){
						ProxyImage.this.setDrawable(new TextureRegionDrawable(new TextureRegion((Texture) Res.ma2.get(texturePath))));
						}
					else{
						System.out.println(ProxyImage.this.position[0][0]+"-----"+ProxyImage.this.position[0][1]+"-----"+ProxyImage.this.position[1][0]+"-----"+ProxyImage.this.position[1][1]);
						ProxyImage.this.setDrawable(new TextureRegionDrawable(new TextureRegion((Texture) Res.ma2.get(texturePath),(int)ProxyImage.this.position[0][0],(int)ProxyImage.this.position[0][1],(int)ProxyImage.this.position[1][0],(int)ProxyImage.this.position[1][1])));
						//ProxyImage.this.setDrawable(new TextureRegionDrawable(new TextureRegion((Texture) Res.ma2.get(texturePath),500,500,1200,1200)));
					}
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
