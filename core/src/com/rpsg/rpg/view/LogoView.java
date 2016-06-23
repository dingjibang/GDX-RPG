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
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.View;
import com.rpsg.rpg.utils.game.GameUtil;

public class LogoView extends View{
	public boolean played=false;
	Stage stage;
	Action final1,final2;
	int flag=0;
	@Override
	public View init() {
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.stage_width, GameUtil.stage_height, new OrthographicCamera()));
		stage.addActor(new Image(Setting.UI_BASE_IMG).size(1024,576));
		final2=new Action(){
			public boolean act(float delta) {
				stage.addActor(new Image(Setting.UI_BASE_IMG).size(1024,576).color(0,0,0,0).action(Actions.sequence(Actions.color(new Color(0,0,0,1),0.2f),Actions.addAction(new Action(){
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
				stage.addActor(new Image(Setting.UI_BASE_IMG).size(1024,576).color(0,0,0,0).action(Actions.sequence(Actions.color(new Color(0,0,0,1),0.2f),Actions.addAction(new Action(){
					public boolean act(float delta) {
						stage.clear();
						stage.addActor(new Image(Setting.IMAGE_LOGO+"bg2.png").color(1,1,1,0).action(Actions.sequence(Actions.delay(0.1f),Actions.fadeIn(0.4f))));
						stage.addActor(new Image(Setting.IMAGE_LOGO+"hv2.png").color(1,1,1,0).action(Actions.sequence(Actions.delay(0.5f),Actions.fadeIn(0.5f))));
						stage.addActor(new Image(Setting.UI_BASE_IMG).size(1024,576).color(0,0,0,0).action(Actions.sequence(Actions.delay(4f),Actions.color(new Color(0,0,0,1),0.2f),Actions.addAction(final2))));
						return true;
					}
				}))));
				flag=1;
				return true;
			}
		};
		final1.act(0);
		stage.addActor(new Image(Setting.UI_BASE_IMG).size(1024,576).color(0,0,0,0).action(Actions.sequence(Actions.delay(6),Actions.color(new Color(0,0,0,1),0.4f),Actions.addAction(final1))));
//		Music.playMusic("logo.mp3");
		return this;
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
