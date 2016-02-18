package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * 血条等动画组件
 */
public class Progress extends Actor {
	public Image bg, fg, cache;// 背景图片，前景图片和缓冲动画图片
	public int min,max,value,current;//最小值 最大值 值 和 缓冲值 
	private Actor proxy = new Actor();
	
	public Progress(Image bg, Image fg, Image cache,int min,int max) {
		super();
		this.bg = bg;
		this.fg = fg;
		this.cache = cache;
		this.max = max;
		this.min = min;
		current = value = 0;
		setSize(bg.getWidth(), bg.getHeight());
	}
	
	public Progress value(int value){
		if(this.value == value) return this;
		
		int offset = this.value - value;// + (int)(proxy.getWidth() / per());
		this.value = value;
		if(offset < 0) offset = 0;
		proxy.setWidth((float)offset / max * getWidth());
		proxy.clearActions();
		proxy.addAction(Actions.sizeTo(0, getHeight(),1.5f,Interpolation.pow4Out));
		return this;
	}
	
	public int value(){
		return value;
	}
	
	public int currentValue(){
		return current;
	}
	
	public void draw(Batch batch, float parentAlpha) {
		bg.position(getX(), getY()).size(getWidth(),getHeight()).draw(batch);
		fg.position(getX(), getY()).size(getWidth() * per(), getHeight()).getRegion().setRegion(0, 0, (int)fg.getWidth(), (int)getHeight());
		fg.draw(batch,parentAlpha);
		cache.position(getX()+fg.getWidth()*fg.getScaleX(), getY()).size(proxy.getWidth(), getHeight()).getRegion().setRegion((int)fg.getWidth(),0,(int)proxy.getWidth(),(int)cache.getHeight());
		cache.draw(batch,parentAlpha);
	}
	
	public float per(){
		return (float)(value) / (max - min);
	}
	
	public void act(float delta) {
		proxy.act(delta);
	}

}
