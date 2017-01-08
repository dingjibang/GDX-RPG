package com.rpsg.rpg.view.game;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.ui.Image;

/**
 * GDX-RPG 立绘显示器
 */
public class FG {
	
	Image leftImage, rightImage;
	
	public static final int left = 0, right = 1;
	
	public FG() {
		leftImage = new Image();
		rightImage = new Image();
		
		
	}
	
	private Image get(Integer position) {
		return position == left ? leftImage : rightImage;
	}
	
	public void show(Integer position, String fgPath, Actions withAction) {
		
	}
	
	public void hide(Integer position) {
		if(position == null){
			$.add(leftImage, rightImage).cleanActions().fadeOut(.3f);
		}
	}
}
