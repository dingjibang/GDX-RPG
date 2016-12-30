package com.rpsg.rpg.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.UI;
import com.rpsg.rpg.core.Views;

/**
 * Logo视窗
 */
public class LogoView extends View{

	List<Action> actions = new ArrayList<>();
	
	
	
	
	public void create() {
		stage = new Stage(new ScalingViewport(Scaling.stretch, Game.STAGE_WIDTH, Game.STAGE_HEIGHT, new OrthographicCamera()), Views.batch);
		
//		actions.add(Actions.run(() -> {
//			//bg
//			UI.empty().query().fillParent().fadeOut().colorTo("4994b9", .3f).appendTo(stage);
//		}));
//		
//		actions.add(Actions.run(() -> {
//			
//			UI.empty().query().fillParent().fadeOut().colorTo("4994b9", .3f).appendTo(stage);
//		}));
//		
//		stage.addAction(Actions.sequence(view1, Actions.delay(6), view2));
		
		UI.empty().query().fillParent().fadeOut().colorTo("4994b9", .3f).appendTo(stage);
	}
	
	@Override
	public boolean onInput() {
		
		return super.onInput();
	}
	
	public void stop() {
		
	}

	public void draw() {
		stage.draw();
	}

	public void act() {
		stage.act();
	}

}
