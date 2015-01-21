package com.rpsg.rpg.object.base;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.view.GameViews;

public abstract class RenderAble {
	public abstract void logic();

	public boolean dispose = false;
	public SpriteBatch batch = GameViews.batch;

	public static List<RenderAble> renderList=new ArrayList<RenderAble>();
	public static void put(RenderAble able) {
		renderList.add(able);
	}
	
	public static void render(){
		renderList.get(renderList.size()-1).logic();
	}
}
