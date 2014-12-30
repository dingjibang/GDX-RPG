package com.rpsg.rpg.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.IView;
import com.rpsg.rpg.system.base.Image;
import com.rpsg.rpg.utils.game.GameUtil;

public class LogoView extends IView{
	private Image bg;
	public boolean played=false;
	public Stage s;
	@Override
	public void init() {
		s=new Stage();
		bg= new Image(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOGO+"bg.png")));
		bg.setSize(GameUtil.screen_width-100, GameUtil.screen_height-70);
		bg.setPosition(0,0);
		bg.addAction(Actions.parallel(Actions.rotateTo(1,1),Actions.moveTo(40, 40,1)));
		
		s.addActor(bg);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		s.draw();
	}

	@Override
	public void logic() {
		s.act();
	}

	@Override
	public void onkeyTyped(char character) {
		played=true;
	}

	@Override
	public void dispose() {
		bg.dispose();
		s.dispose();
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
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
	
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	
}
