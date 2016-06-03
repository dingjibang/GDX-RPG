package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.SizeToAction;

public class NumberLabel extends Label{

	Actor proxy = new Actor();
	public String name = "";
	String before = "";
	private int num;
	
	public int num(){
		return num;
	}
	
	public NumberLabel(int text, int fontsize,String name) {
		super(text, fontsize);
		
		this.name = name;
		this.proxy.setWidth(text);
	}
	
	public void toNumber(float f){
		resetProxy();
		proxy.setWidth(Integer.valueOf(getText().toString()));
		SizeToAction action = new SizeToAction();
		action.setDuration(1.5f);
		action.setInterpolation(Interpolation.pow3);
		action.setWidth(f);
		action.setHeight(0);
		
		proxy.addAction(action);
	}
	
	@Override
	public void act(float delta) {
		proxy.act(delta);
		super.act(delta);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		setText(before + (int)proxy.getWidth());
		super.draw(batch, parentAlpha);
	}
	
	public void setNumber(float maxexp){
		resetProxy();
		proxy.setWidth(maxexp);
		setText(((Float)maxexp).intValue() + "");
	}
	
	private void resetProxy(){
		proxy.clear();
		proxy.setWidth(0);
	}

	public NumberLabel before(String string) {
		before = string;
		return this;
	}

}
