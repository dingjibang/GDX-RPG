package com.rpsg.rpg.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Bloom;
import com.bitfire.postprocessing.effects.Vignette;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.util.SimpleAction;

/**
 * 画面二次处理管理器
 */
public class PostController implements Disposable{
	
	private PostProcessor post;
	private Bloom bloom;
	private Vignette vignette;
	
	private Status status;
	private SimpleAction[] actions = new SimpleAction[5];
	
	public static enum Status {
		normal(1, 1, .7f, 1.2f, .3f),
		menu(.1f, .9f, 1.2f, .8f, 0f);
		
		float baseIntesity, baseSaturation, bloomIntesity, bloomSaturation, threshold;
		private Status(float baseIntesity, float baseSaturation, float bloomIntesity, float bloomSaturation, float threshold){
			this.baseIntesity = baseIntesity;
			this.baseSaturation = baseSaturation;
			this.bloomIntesity = bloomIntesity;
			this.bloomSaturation = bloomSaturation;
			this.threshold = threshold;
		}
	}
	
	public PostController() {
		post = new PostProcessor(false, true, true);
		
		//bloom
		bloom = new Bloom((int)(Game.width() * 0.35f), (int)(Game.height() * 0.35f));
		setStatus(Status.normal, true);
		post.addEffect(bloom);
		
		//黑边
		vignette = new Vignette(Game.width(), Game.height(), false);
		vignette.setIntensity(.5f);
		post.addEffect(vignette);
	}

	public void begin() {
		if(Game.setting.enablePost){
			for(SimpleAction action : actions) 
				action.act(Gdx.graphics.getDeltaTime());
			
			bloom.setBaseIntesity(actions[0].get());
			bloom.setBaseSaturation(actions[1].get());
			bloom.setBloomIntesity(actions[2].get());
			bloom.setBloomSaturation(actions[3].get());
			bloom.setThreshold(actions[4].get());
			
			post.capture();
			
		}
	}
	
	public void end() {
		if(Game.setting.enablePost)
			post.render(true);
	}
	
	public void dispose() {
		post.dispose();
	}
	
	public void setStatus(Status status, boolean now) {
		if(status == this.status) return;
		if(this.status == null) this.status = Status.normal;
		
		actions[0] = new SimpleAction(this.status.baseIntesity, status.baseIntesity, now ? 0 : .5f, Interpolation.pow3Out);
		actions[1] = new SimpleAction(this.status.baseSaturation, status.baseSaturation, now ? 0 : .5f, Interpolation.pow3Out);
		actions[2] = new SimpleAction(this.status.bloomIntesity, status.bloomIntesity, now ? 0 : .5f, Interpolation.pow3Out);
		actions[3] = new SimpleAction(this.status.bloomSaturation, status.bloomSaturation, now ? 0 : .5f, Interpolation.pow3Out);
		actions[4] = new SimpleAction(this.status.threshold, status.threshold, now ? 0 : .5f, Interpolation.pow3Out);
		
		this.status = status;
	}
	
	public void resize(Viewport viewport){
		post.setViewport(new Rectangle(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight()));
	}

	
}
