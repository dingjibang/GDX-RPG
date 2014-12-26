package com.rpsg.rpg.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.IView;
import com.rpsg.rpg.system.base.Image;
import com.rpsg.rpg.utils.game.GameUtil;

public class LogoView extends IView{
	private Image bg,logo,flash1,flash2,logos;
	private int ani_num=-30;
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
//		logo= new Image(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOGO+"logo.png")));
//		logo.setColor(1,1,1,0);
//		logo.setPosition(GameUtil.screen_width/2-logo.getWidth()/2, GameUtil.screen_height/2-logo.getHeight()/2);
//		logos= new Image(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOGO+"logoshadow.png")));
//		logos.setColor(1,1,1,0);
//		logos.setPosition(GameUtil.screen_width/2-logos.getWidth()/2, GameUtil.screen_height/2-logos.getHeight()/2);
//		flash1= new Image(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOGO+"hero.png")));
//		flash1.setColor(1,1,1,0.5f);
//		flash1.setScale(0.4f);
//		flash2= new Image(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOGO+"bcir.png")));
//		flash2.setColor(1,1,1,0.5f);
//		CMusic.playMusic("1.mp3");
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		s.draw();
//		bg.draw(batch);
//		logos.draw(batch);
//		logo.draw(batch);
//		flash1.draw(batch);
//		flash2.draw(batch);
	}

	@Override
	public void logic() {
		s.act();
//		if(ani_num++>300)
//			played=true;
//		if(ani_num<100 && ani_num >0)
//			logo.setColor(1, 1, 1, (float)(1f/100f)*ani_num);
//		if(ani_num<200)
//			flash1.setX(ani_num*30);
//			flash1.setY(ani_num*-0.7f);
//			flash2.setX(GameUtil.screen_width-ani_num*20);
//		if(ani_num>50 && ani_num<178)
//			bg.setColor(1, 1, 1, (ani_num-50)*2);
//		if(ani_num>100 && ani_num<200)
//			logos.setColor(1, 1, 1, (float)(1f/100f)*ani_num);
	}

	@Override
	public void onkeyTyped(char character) {
		played=true;
	}

	@Override
	public void dispose() {
		bg.dispose();
		s.dispose();
//		logo.dispose();
//		flash1.dispose();
//		flash2.dispose();
//		logos.dispose();
	}

	
}
