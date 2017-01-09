package com.rpsg.rpg.controller;

import com.badlogic.gdx.utils.Disposable;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Bloom;
import com.bitfire.postprocessing.effects.Vignette;
import com.rpsg.rpg.core.Game;

/**
 * 画面二次处理管理器
 */
public class PostController implements Disposable{
	PostProcessor post;
	Bloom bloom;
	Vignette vignette;
	
	public PostController() {
		post = new PostProcessor(false, true, true);
		
		//bloom
		bloom = new Bloom((int)(Game.width() * 0.35f), (int)(Game.height() * 0.35f));
		bloom.setBaseIntesity(1);
		bloom.setBloomIntesity(.7f);
		bloom.setBloomSaturation(1.2f);
		bloom.setThreshold(.3f);
		post.addEffect(bloom);
		
		//黑边
		vignette = new Vignette(Game.width(), Game.height(), false);
		vignette.setIntensity(.5f);
		post.addEffect(vignette);
	}

	public void begin() {
		if(Game.setting.enablePost)
			post.capture();
	}
	
	public void end() {
		if(Game.setting.enablePost)
			post.render(true);
	}
	
	
	public void dispose() {
		post.dispose();
	}
	
	
}
