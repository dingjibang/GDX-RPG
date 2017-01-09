package com.rpsg.rpg.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.Log;
import com.rpsg.rpg.core.Path;
import com.rpsg.rpg.core.Res;
import com.rpsg.rpg.core.Sound.SEManager;
import com.rpsg.rpg.core.UI;
import com.rpsg.rpg.core.Views;
import com.rpsg.rpg.ui.Image;
import com.rpsg.rpg.ui.view.View;



/**
 * Logo视窗
 */
public class LogoView extends View{

	List<Action> stages = new ArrayList<>();
	boolean skiping = false;
	int currentStage = 0;
	Group group;
	
	SEManager seManager = new SEManager();
	
	public void create() {
		stage = Game.stage();
		
		stages.add(Actions.run(() -> {
			//bg
			UI.base().query().size(Game.STAGE_WIDTH, Game.STAGE_HEIGHT).color("2c2c2c").zIndex(0).appendTo(group);
			
			group.addAction(Actions.delay(.7f, Actions.run(() -> {
				GdxQuery point = Res.sync(Path.IMAGE_LOGO + "p_1.png").query().appendTo(group).zIndex(2).position(Game.STAGE_WIDTH / 2, Game.STAGE_HEIGHT / 2);
				
				int pointsCount = 60;
				float minScale = .15f;
				
				point.center().scale(0).action(Actions.sequence(
					Actions.scaleTo(minScale, minScale, .4f, Interpolation.bounceOut),
					Actions.delay(.3f),
					Actions.run(() -> 
						Res.sync(Path.IMAGE_LOGO + "t0.png").query().appendTo(group).zIndex(3)
							.position(Game.STAGE_WIDTH / 2 + 10, Game.STAGE_HEIGHT / 2 + 10).fadeOut().action(
									Actions.repeat(2, Actions.sequence(Actions.fadeIn(.3f), Actions.alpha(0f, .1f)))
							)
					)
				)).action(Actions.delay(1f, Actions.repeat(5, 
						Actions.sequence(Actions.rotateTo(-15f, .02f), Actions.rotateTo(0f, .02f), Actions.rotateTo(15f, .02f), Actions.rotateTo(0f, .02f))
				)));
				seManager.play(Path.MUSIC_SE + "se_1.mp3");
				
				group.addAction(Actions.delay(1.5f, Actions.run(() -> {
					for(int i = 0; i < pointsCount; i ++){
						boolean left = MathUtils.randomBoolean(), top = MathUtils.randomBoolean();
						int leftOffset = MathUtils.random(50, 300), topOffset = MathUtils.random(50, 200), size = MathUtils.random(10, 40);
						float animated = (float)MathUtils.random(1800, 2800) / 1000f;
						int tex = MathUtils.random(1, 5);
						
						Res.sync(Path.IMAGE_LOGO + "p_"+tex+".png").query().filter(TextureFilter.Nearest).appendTo(group).zIndex(1)
						.position(left ? -leftOffset : Game.STAGE_WIDTH + leftOffset, top ? -topOffset : Game.STAGE_HEIGHT + topOffset)
						.size(size, size)
						.alpha(MathUtils.random(.2f, .5f))
						.center()
						.action(Actions.sequence(
									Actions.moveTo(Game.STAGE_WIDTH / 2 - size / 2, Game.STAGE_HEIGHT / 2 - size / 2, animated, Interpolation.pow2In),
									Actions.run(() -> {
										point.scale(point.scale() + ((0.6f - minScale) / ((float)pointsCount - 1f)));
//										Sound.se(Path.MUSIC_SE + "item00.wav");
									}),
									Actions.removeActor()
								))
						.action(Actions.delay(animated - .1f, Actions.fadeOut(.1f)));
					}
				})));
				
				group.addAction(Actions.delay(2f, Actions.run(() -> {
					seManager.play(Path.MUSIC_SE + "logo.mp3");
				})));
				
				group.addAction(Actions.delay(4.8f, Actions.run(() -> point.action(Actions.scaleTo(1, 1, .3f, Interpolation.bounceOut)))));
				group.addAction(Actions.delay(5.4f, Actions.run(() -> {
					point.action(Actions.sequence(Actions.moveBy(-77, 0, .5f, Interpolation.pow2Out), Actions.delay(.7f), Actions.fadeOut(.3f)));
					
					group.addAction(Actions.delay(.7f, Actions.run(() -> {
						
						GdxQuery r = Res.sync(Path.IMAGE_LOGO + "r.png").query().zIndex(0).filter(TextureFilter.Nearest).position(Game.STAGE_WIDTH / 2 - 77, Game.STAGE_HEIGHT / 2).fadeOut().center()
							.action(Actions.moveBy(-120, 0, .8f, Interpolation.pow3Out)).fadeIn(.2f).appendTo(group);
						GdxQuery p = Res.sync(Path.IMAGE_LOGO + "p.png").query().zIndex(0).filter(TextureFilter.Nearest).position(Game.STAGE_WIDTH / 2 - 60, Game.STAGE_HEIGHT / 2).fadeOut().center()
						.fadeIn(.5f).appendTo(group);
						GdxQuery s = Res.sync(Path.IMAGE_LOGO + "s.png").query().zIndex(0).filter(TextureFilter.Nearest).position(Game.STAGE_WIDTH / 2 - 77, Game.STAGE_HEIGHT / 2).fadeOut().center()
						.action(Actions.moveBy(150, 0, .8f, Interpolation.pow3Out)).fadeIn(.2f).appendTo(group);
						GdxQuery g = Res.sync(Path.IMAGE_LOGO + "g.png").query().zIndex(0).filter(TextureFilter.Nearest).position(Game.STAGE_WIDTH / 2 - 77, Game.STAGE_HEIGHT / 2).fadeOut().center()
						.action(Actions.moveBy(285, 0, .8f, Interpolation.pow3Out)).fadeIn(.2f).appendTo(group);
						
						GdxQuery logo = $.add(r, p, s, g);
						
						group.addAction(Actions.delay(1.6f, Actions.run(() -> {
							Res.sync(Path.IMAGE_LOGO + "bottom.png").query().filter(TextureFilter.Nearest).fadeOut().position(Game.STAGE_WIDTH / 2, -100).center().action(Actions.fadeIn(.5f)).action(Actions.moveBy(0, 150, .5f, Interpolation.pow2Out)).appendTo(group);
						})));
						
						point.zIndex(23333);
						
						group.addAction(Actions.delay(.3f, Actions.run(() -> {
							Res.sync(Path.IMAGE_LOGO + "circle.png").query().color("d9bc64").position(Game.STAGE_WIDTH / 2 - 77, Game.STAGE_HEIGHT / 2).center().scale(0).action(Actions.scaleTo(30, 30, .8f, Interpolation.pow4In)).appendTo(group).zIndex(1);
							Res.sync(Path.IMAGE_LOGO + "circle.png").query().color("22ac38").position(Game.STAGE_WIDTH / 2 - 77, Game.STAGE_HEIGHT / 2).center().scale(0).action(Actions.delay(.4f, Actions.scaleTo(30, 30, .8f, Interpolation.pow4In))).appendTo(group).zIndex(2);
							Res.sync(Path.IMAGE_LOGO + "circle.png").query().color("d67ca0").position(Game.STAGE_WIDTH / 2 - 77, Game.STAGE_HEIGHT / 2).center().scale(0).action(Actions.delay(.8f, Actions.scaleTo(30, 30, .8f, Interpolation.pow4In))).appendTo(group).zIndex(3);
							Res.sync(Path.IMAGE_LOGO + "circle.png").query().color("5ca3cb").position(Game.STAGE_WIDTH / 2 - 77, Game.STAGE_HEIGHT / 2).center().scale(0).action(Actions.delay(1.2f, Actions.scaleTo(30, 30, .8f, Interpolation.pow4In))).appendTo(group).zIndex(4);
							logo.zIndex(233);
							point.zIndex(666);
						})));
					})));
					
					
				})));
				
			})));
			
			
		}));
		
		stages.add(Actions.run(() -> {
			Res.get(Path.IMAGE_LOGO + "bg2.png").query().size(Game.STAGE_WIDTH, Game.STAGE_HEIGHT).fadeOut().fadeIn(.5f).appendTo(group);
			Res.get(Path.IMAGE_LOGO + "hv.png").query().position(400, 50).fadeOut().action(Actions.delay(.5f, Actions.fadeIn(.5f))).appendTo(group);
			group.addAction(Actions.delay(5, Actions.run(this::skip)));
		}));
//		
		
		stage.addActor(group = new Group());
		
		group.addAction(stages.get(currentStage));
		group.addAction(Actions.delay(11, Actions.run(this::skip)));

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
		
		Log.i("Logo-view[created]");
	}
	
	public void skip() {
		if(skiping)
			return;
		
		skiping = true;
		
		seManager.stop();
		
		Image mask = UI.base();
		
		mask.query().fillParent().color(Color.BLACK).fadeOut().action(Actions.sequence(Actions.fadeIn(.5f), Actions.run(() -> {
			//所有stages已经播放完毕
			if(currentStage + 1 == stages.size()){
				Views.addView(TitleView.class);
				remove();
				return;
			}
			
			skiping = false;
			
			group.clear();
			
			group.addAction(stages.get(++currentStage));
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
