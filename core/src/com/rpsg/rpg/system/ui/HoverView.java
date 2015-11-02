package com.rpsg.rpg.system.ui;


import java.util.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.rpg.utils.game.GameUtil;

public abstract class HoverView{
	public boolean disposed=false;
	public Stage stage;
	protected Map<Object,Object> param;
	
	public HoverView superInit(){
		return this.superInit(null);
	}
	
	public HoverView superInit(Map<Object, Object> initParam){
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
		this.param = initParam;
		init();
		return this;
	}
	
	public Runnable run;
	public HoverView setExitCallBack(Runnable run){
		this.run=run;
		return this;
	}
	
	public void dispose(){
		close();
		stage.dispose();
		if(run!=null)
			run.run();
	}
	
	public abstract void init();
	public void logic(){
		stage.act();
	};
	
	public void draw(){
		stage.draw();
	};
	
	public void close(){};

	
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return stage.touchDown(screenX, screenY, pointer, button);
	}

	
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return stage.touchDragged(screenX, screenY, pointer);
	}
	
	
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return stage.touchUp(screenX, screenY, pointer, button);
	}
	
	
	public boolean scrolled(int amount) {
		return stage.scrolled(amount);
	}

	
	public boolean keyDown(int keycode) {
		return stage.keyDown(keycode);
	}

	
	public boolean keyUp(int keycode) {
		return stage.keyUp(keycode);
	}

	
	public boolean keyTyped(char character) {
		return stage.keyTyped(character);
	}

	
	public boolean mouseMoved(int screenX, int screenY) {
		return stage.mouseMoved(screenX, screenY);
	}
}
