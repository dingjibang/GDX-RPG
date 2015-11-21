package com.rpsg.rpg.utils.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.core.Setting;

public class TouchParticle {
	ParticleEffect p;
	boolean flag;
	
	public TouchParticle() {
		p = new ParticleEffect();
		p.load(Gdx.files.internal(Setting.PARTICLE+"touch.p"), Gdx.files.internal(Setting.PARTICLE));
		
		p.start();
	}
	
	public void draw(SpriteBatch batch){
		logic();
		p.draw(batch);
	}
	
	public void logic(){
		if(p.isComplete() && flag)
			p.reset();
		
		if(!flag)
			p.allowCompletion();
	}
	
	public void setPosition(int x,int y){
		p.setPosition(x, y);
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
