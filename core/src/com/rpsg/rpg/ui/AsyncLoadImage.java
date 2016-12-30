package com.rpsg.rpg.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Res;

/**
 * GDX-RPG 异步加载纹理类<br>
 * 启用另一个OpenGL线程来达到异步加载纹理的效果，在加载的过程中，当前的图片是无法读取宽/高度的。
 */
@SuppressWarnings("rawtypes")
public class AsyncLoadImage extends Image{
	
	private boolean loaded = false, loading = false;
	private int originAlignment;
	private String texturePath;
	
	public AsyncLoadImage(String texturePath) {
		this.texturePath = texturePath;
	}
	

	/**
	 * 初始化
	 */
	private void init(){
		/**如果开启纹理过滤，则设置*/
		if(RPG.setting.filter)
			((TextureRegionDrawable)getDrawable()).getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		/**重置自身大小*/
		if (getWidth() == 0)
			setWidth(getDrawable().getMinWidth());
		if (getHeight() == 0)
			setHeight(getDrawable().getMinHeight());
		if (originAlignment != -1)
			setOrigin(originAlignment);
	}
	
	/**当该纹理被调用绘制时，才开始懒加载纹理，否则歇着*/
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		loadTexture();
	}
	
	TextureParameter parameter = new TextureParameter();
	{
		/**当纹理加载完成后的回调*/
		parameter.loadedCallback = (AssetManager assetManager, String fileName, Class type) -> {
			setDrawable(new TextureRegionDrawable(new TextureRegion(assetManager.get(fileName))));
			loaded = true;
			init();
		};
	}
	
	/**加载纹理**/
	public void loadTexture(){
		if(loading) 
			return;
		
		loading = true;
		
		Res.am.load(texturePath, Texture.class, parameter);
	}
	
	/**是否加载完成*/
	public boolean loaded(){
		return loaded;
	}
	
	public void setOrigin(int alignment) {
		super.setOrigin(originAlignment = alignment);
	}
}
