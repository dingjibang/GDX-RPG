package com.rpsg.rpg.view;

import com.rpsg.rpg.core.Views;

/**
 *	GDX-RPG 标题视图
 *	TODO 等待制作，目前直接跳转到GameView
 */
public class TitleView extends View{

	public void create() {
		Views.addView(GameView.class);
		remove();
	}

	public void draw() {
		
	}

	public void act() {
		
	}
	
}
