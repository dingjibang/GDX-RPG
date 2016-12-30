package com.rpsg.rpg.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.Log;
import com.rpsg.rpg.core.Music;
import com.rpsg.rpg.core.Path;
import com.rpsg.rpg.core.Res;
import com.rpsg.rpg.core.UI;
import com.rpsg.rpg.core.Views;
import com.rpsg.rpg.ui.Image;



/**
 * Logo视窗
 */
public class LogoView extends View{

	List<Action> stages = new ArrayList<>();
	boolean skiping = false;
	int currentStage = 0;
	Group group;
	
	public void create() {
		stage = new Stage(new ScalingViewport(Scaling.stretch, Game.STAGE_WIDTH, Game.STAGE_HEIGHT, new OrthographicCamera()), Views.batch);
		stages.add(Actions.run(() -> {
			//bg
			UI.empty().query().size(Game.STAGE_WIDTH, Game.STAGE_HEIGHT).fadeOut().colorTo("6892b5", .3f).zIndex(0).appendTo(group);
			
			group.addAction(Actions.delay(.7f, Actions.run(() -> {
				GdxQuery point = Res.sync(Path.IMAGE_LOGO + "p_1.png").query().appendTo(group).zIndex(2).position(Game.STAGE_WIDTH / 2, Game.STAGE_HEIGHT / 2);
				
				int pointsCount = 30;
				float minScale = .2f;
				
				point.center().scale(0).action(Actions.sequence(
					Actions.scaleTo(minScale, minScale, .4f, Interpolation.bounceOut),
					Actions.delay(.3f),
					Actions.run(() -> 
						Res.get(Path.IMAGE_LOGO + "t0.png").query().appendTo(group).zIndex(3)
							.position(Game.STAGE_WIDTH / 2 + 10, Game.STAGE_HEIGHT / 2 + 10).fadeOut().action(
									Actions.repeat(2, Actions.sequence(Actions.fadeIn(.3f), Actions.alpha(0f, .1f)))
							)
					)
				));
				Music.se(Path.MUSIC_SE + "se_1.mp3");
				
				group.addAction(Actions.delay(1.3f, Actions.run(() -> {
					for(int i = 0; i < pointsCount; i ++){
						boolean left = MathUtils.randomBoolean(), top = MathUtils.randomBoolean();
						int leftOffset = MathUtils.random(50, 300), topOffset = MathUtils.random(50, 200), size = MathUtils.random(10, 40);
						float animated = (float)MathUtils.random(1000, 2000) / 1000f;
						
						Res.get(Path.IMAGE_LOGO + "p_1.png").query().appendTo(group).zIndex(1)
						.position(left ? -leftOffset : Game.STAGE_WIDTH + leftOffset, top ? -topOffset : Game.STAGE_HEIGHT + topOffset)
						.size(size, size)
						.alpha(MathUtils.random(.2f, .9f))
						.center()
						.action(Actions.sequence(
									Actions.moveTo(Game.STAGE_WIDTH / 2, Game.STAGE_HEIGHT / 2, animated, Interpolation.pow2In),
									Actions.run(() -> {
										Log.i(point.scale());
										point.scale(point.scale() + ((.8f - minScale) / ((float)pointsCount - 1f)));
										Music.se(Path.MUSIC_SE + "item00.wav", .3f);
									}),
									Actions.removeActor()
								))
						.action(Actions.delay(animated - .1f, Actions.fadeOut(.1f)));
					}
				})));
				
			})));
			
			
		}));
		
		stages.add(Actions.run(() -> {
			
			UI.empty().query().size(Game.STAGE_WIDTH, Game.STAGE_HEIGHT).fadeOut().colorTo("4994b9", .3f).appendTo(group);
		}));
//		
		
		stage.addActor(group = new Group());
		
		group.addAction(stages.get(currentStage));
//		group.addAction(Actions.delay(6, Actions.run(this::skip)));

		//当接受到任意按键或触屏时，跳过当前场景
		stage.addListener(new InputListener(){
			public boolean keyDown(InputEvent event, int keycode) {
				skip();
				return super.keyDown(event, keycode);
			}
			
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				skip();
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		
	}
	
	public void skip() {
		if(skiping)
			return;
		
		skiping = true;
		
		Image mask = UI.empty();
		
		mask.query().fillParent().color(Color.BLACK).fadeOut().action(Actions.sequence(Actions.fadeIn(.5f), Actions.run(() -> {
			//所有stages已经播放完毕
			if(currentStage + 1 == stages.size()){
				//callback
				Log.i("done");
				return;
			}
			
			skiping = false;
			
			group.clear();
			
			group.addAction(stages.get(++currentStage));
			group.addAction(Actions.delay(6, Actions.run(this::onInput)));
			
			mask.addAction(Actions.sequence(Actions.fadeOut(.5f), Actions.removeActor()));
			
		}))).appendTo(stage);
		
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
