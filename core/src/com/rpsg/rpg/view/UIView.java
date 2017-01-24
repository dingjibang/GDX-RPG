package com.rpsg.rpg.view;

import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.ui.view.View;

/**
 * 快速View
 */
public abstract class UIView extends View{
	public UIView() {
		this.stage = Game.stage();
	}

	public void act() {
		stage.act();
	}

	public void draw() {
		stage.draw();
	}
}
