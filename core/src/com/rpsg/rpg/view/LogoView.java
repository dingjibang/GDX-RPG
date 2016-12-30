package com.rpsg.rpg.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.rpg.core.Game;

/**
 * Logo视窗
 */
public class LogoView extends View{

	public void create() {
		stage = new Stage(new ScalingViewport(Scaling.stretch, Game.STAGE_WIDTH, Game.STAGE_HEIGHT, new OrthographicCamera()));
	}

	public void draw() {
		
	}

	public void logic() {
		
	}

}
