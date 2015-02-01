package com.rpsg.rpg.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.ui.IView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.game.GameUtil;

public class LogoView extends IView{
	private Image bg,logo,flash1,flash2,logos;
	private int ani_num=-30;
	public boolean played=false;
	@Override
	public void init() {
		bg= new Image(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOGO+"bg.png")));
		bg.setSize(GameUtil.screen_width, GameUtil.screen_height);
		bg.setPosition(0,0);
		logo= new Image(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOGO+"logo.png")));
		logo.setColor(1,1,1,0);
		logo.setPosition(GameUtil.screen_width/2-logo.getWidth()/2, GameUtil.screen_height/2-logo.getHeight()/2);
		logos= new Image(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOGO+"logoshadow.png")));
		logos.setColor(1,1,1,0);
		logos.setPosition(GameUtil.screen_width/2-logos.getWidth()/2, GameUtil.screen_height/2-logos.getHeight()/2);
		flash1= new Image(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOGO+"hero.png")));
		flash1.setColor(1,1,1,0.5f);
		flash1.setScale(0.4f);
		flash2= new Image(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOGO+"bcir.png")));
		flash2.setColor(1,1,1,0.5f);
//		CMusic.playMusic("1.mp3");
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		bg.draw(batch);
		logos.draw(batch);
		logo.draw(batch);
		flash1.draw(batch);
		flash2.draw(batch);
	}

	@Override
	public void logic() {
		if(ani_num++>300)
			played=true;
		if(ani_num<100 && ani_num >0)
			logo.setColor(1, 1, 1, (float)(1f/100f)*ani_num);
		if(ani_num<200)
			flash1.setX(ani_num*30);
			flash1.setY(ani_num*-0.7f);
			flash2.setX(GameUtil.screen_width-ani_num*20);
		if(ani_num>50 && ani_num<178)
			bg.setColor(1, 1, 1, (ani_num-50)*2);
		if(ani_num>100 && ani_num<200)
			logos.setColor(1, 1, 1, (float)(1f/100f)*(ani_num-100));
	}

	@Override
	public void onkeyTyped(char character) {
		played=true;
	}

	@Override
	public void dispose() {
		
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
