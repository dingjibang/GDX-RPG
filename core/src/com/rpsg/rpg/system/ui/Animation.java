package com.rpsg.rpg.system.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.NPC;
import com.rpsg.rpg.object.script.ScriptCollide;
import com.rpsg.rpg.system.base.Res;

public class Animation extends NPC {
	
	private static final long serialVersionUID = 1L;
	
	com.badlogic.gdx.graphics.g2d.Animation animation;
	Map<TextureRegion, Drawable> cache = new HashMap<>();
	Image proxy;
	
	Array<TextureRegion> regions;
	public int id;
	public boolean remove = false;
	
	private Runnable played = null;
	
	float time;
	
	public boolean generated = false;
	
	public Animation(int id) {
		proxy = new Image();
		this.id = id;
	}
	
	public synchronized Animation generate(){
		generated = true;
		
		FileHandle file = Gdx.files.internal(Setting.IMAGE_ANIMATION+id+"/");
		if(!file.exists())
			return null;
		
		FileHandle[] child = list(file);
		int length = child.length;
		if(length==0)
			throw new GdxRuntimeException("has not images");
		
		boolean simple = length == 1;
		
		Array<TextureRegion> regions = new Array<>();
		
		if(simple){
			int width = Integer.valueOf(child[0].name());
			Texture tex = Res.getTexture(Setting.IMAGE_ANIMATION+id+"/"+child[0].name());
			for (int i = 0; i < tex.getWidth() / width; i++) {
				regions.add(new TextureRegion(tex, i * width, 0, width, tex.getHeight()));
			}
		}else{
			for(FileHandle image:child){
				Texture tex = Res.getTexture(Setting.IMAGE_ANIMATION+id+"/"+image.name());
				regions.add(new TextureRegion(tex));
			}
		}
		
		animation = new com.badlogic.gdx.graphics.g2d.Animation(.1f,regions);
		
		int w = 0,h = 0;
		for(TextureRegion region : regions){
			if(w < region.getRegionWidth())
				w = region.getRegionWidth();
			if(h < region.getRegionHeight())
				h = region.getRegionHeight();
		}
		
		proxy.setSize(w,h);
		
		setOrigin(Align.center);
		
		return this;
	}
	
	/**
	 * 因为jar包的结构，导致内部资源不支持list，我操你婊子妈的java，所以只能用土办法来list了，日了野狗
	 * @see http://stackoverflow.com/questions/1429172/how-do-i-list-the-files-inside-a-jar-file
	 */
	private FileHandle[] list(FileHandle file) {
		List<FileHandle> list = new ArrayList<>();
		int count = -1;
		while(file.child(++count + ".png").exists())
			list.add(file.child(count + ".png"));
		return list.toArray(new FileHandle[list.size()]);
	}

	public Animation played(Runnable callback){
		this.played = callback;
		return this;
	}
	
	@Override
	public synchronized void draw(Batch batch, float parentAlpha) {
		//async generate the texture in main loop thread (OPENGL)
		if(animation == null)
			generate();
		
		proxy.setDrawable(getDrawable(animation.getKeyFrame(time += Gdx.graphics.getDeltaTime(),true)));
		proxy.setColor(getColor());
		proxy.setPosition(getX(), getY());
		proxy.setScale(getScaleX(),getScaleY());
		proxy.setOrigin(Align.center);
		
		proxy.draw(batch, parentAlpha);
		
		if(played != null && finished())
			played.run();
	}
	
	public boolean finished(){
		return animation.isAnimationFinished(time);
	}
	
	private Drawable getDrawable(TextureRegion region) {
		Drawable d = cache.get(region);
		if(d == null)
			cache.put(region, d = new TextureRegionDrawable(region));
		return d;
	}
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
	}
	
	//remove self
	public boolean remove(){
		return remove = true;
	}

	@Override
	public void toCollide(ScriptCollide sc) {
		
	}
	
	@Override
	public float getWidth() {
		return proxy.getWidth();
	}
	
	@Override
	public float getHeight() {
		return proxy.getHeight();
	}
	
}
