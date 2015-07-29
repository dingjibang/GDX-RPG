package com.rpsg.rpg.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Music;
import com.rpsg.rpg.system.ui.View;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.game.GameUtil;

public class LogoView extends View{
	public boolean played=false;
	Stage stage;
	Action final1,final2;
	int flag=0;
	@Override
	public void init() {
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
		stage.addActor(new Image(Setting.GAME_RES_IMAGE_LOGO+"wbg.png"));
		stage.addActor(new Image(Setting.GAME_RES_IMAGE_LOGO+"bg.png").color(1,1,1,0).action(Actions.sequence(Actions.delay(0.2f),Actions.fadeIn(0.2f))));
		stage.addActor(new Image(Setting.GAME_RES_IMAGE_LOGO+"logo.png").color(1,1,1,0).position(351, 295).action(Actions.sequence(Actions.delay(1f),Actions.parallel(Actions.fadeIn(0.2f),Actions.moveBy(0, 45,0.3f)))));
		stage.addActor(new Image(Setting.GAME_RES_IMAGE_LOGO+"hr.png").color(1,1,1,0).size(3, 36).position(124, 300).action(Actions.sequence(Actions.delay(0.4f),Actions.parallel(Actions.fadeIn(0.4f),Actions.sizeTo(774, 36,0.4f)))));
		stage.addActor(new Image(Setting.GAME_RES_IMAGE_LOGO+"info.png").color(1,1,1,0).position(290, 280).action(Actions.sequence(Actions.delay(2.6f),Actions.fadeIn(0.4f))));
		final2=new Action(){
			public boolean act(float delta) {
				stage.addActor(new Image(Setting.GAME_RES_IMAGE_LOGO+"wbg.png").color(0,0,0,0).action(Actions.sequence(Actions.color(new Color(0,0,0,1),0.2f),Actions.addAction(new Action(){
					public boolean act(float delta) {
						flag=2;
						played=true;
						return false;
					}
				}))));
				return true;
			}
		};
		final1=new Action(){
			public boolean act(float delta) {
				Music.stopCurrentMusic();
				stage.addActor(new Image(Setting.GAME_RES_IMAGE_LOGO+"wbg.png").color(0,0,0,0).action(Actions.sequence(Actions.color(new Color(0,0,0,1),0.2f),Actions.addAction(new Action(){
					public boolean act(float delta) {
						stage.clear();
						stage.addActor(new Image(Setting.GAME_RES_IMAGE_LOGO+"bg2.png").color(1,1,1,0).action(Actions.sequence(Actions.delay(0.1f),Actions.fadeIn(0.4f))));
						stage.addActor(new Image(Setting.GAME_RES_IMAGE_LOGO+"hv.png").color(1,1,1,0).action(Actions.sequence(Actions.delay(0.5f),Actions.fadeIn(0.5f))));
						stage.addActor(new Image(Setting.GAME_RES_IMAGE_LOGO+"wbg.png").color(0,0,0,0).action(Actions.sequence(Actions.delay(4f),Actions.color(new Color(0,0,0,1),0.2f),Actions.addAction(final2))));
						return true;
					}
				}))));
				flag=1;
				return true;
			}
		};
		stage.addActor(new Image(Setting.GAME_RES_IMAGE_LOGO+"wbg.png").color(0,0,0,0).action(Actions.sequence(Actions.delay(6),Actions.color(new Color(0,0,0,1),0.4f),Actions.addAction(final1))));
//		Music.playMusic("logo.mp3");
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		stage.draw();
	}

	@Override
	public void logic() {
		stage.act();
		if(Gdx.input.justTouched())
			onkeyTyped(' ');
	}

	@Override
	public void onkeyTyped(char character) {
		if(flag==0)
			final1.act(0);
		else
			final2.act(0);
	}

	@Override
	public void dispose() {
//		stage.dispose();
	}

	@Override
	public void onkeyDown(int keyCode) {
		
	}

	@Override
	public void onkeyUp(int keyCode) {
		
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	
}
