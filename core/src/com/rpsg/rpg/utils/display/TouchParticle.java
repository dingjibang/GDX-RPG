package com.rpsg.rpg.utils.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.view.GameViews;

public class TouchParticle {
	ParticleEffect p;
	boolean flag;
	private Stage stage;

	
	public TouchParticle() {
		stage = new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()), GameViews.batch);
		
		p = new ParticleEffect();
		p.load(Gdx.files.internal(Setting.PARTICLE+"touch.p"), Gdx.files.internal(Setting.PARTICLE));
		
		p.start();
	}
	
	public void draw(){
		if(Setting.persistence.touchParticle){
			logic();
			stage.getBatch().end();
			stage.draw();
			stage.getBatch().begin();
			p.draw(stage.getBatch(),Gdx.graphics.getDeltaTime());
		}
	}
	
	public void logic(){
		if(p.isComplete() && flag){
			p.reset();
		}
		
		if(!flag)
			p.allowCompletion();
	}
	
	public void setPosition(int x,int y){
		p.setPosition(x,GameUtil.screen_height - y);
	}
	
	public TouchParticle start(){
		flag = true;
		return this; 
	}
	
	public TouchParticle stop(){
		flag = false;
		return this;
	}
}
