package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public abstract class View implements Disposable{
	public abstract void init();
	public abstract void draw(SpriteBatch batch);
	public abstract void logic();
	public abstract void onkeyTyped(char character);
	public abstract void onkeyDown(int keyCode);
	public abstract void onkeyUp(int keyCode);
	public abstract boolean touchDown(int screenX, int screenY, int pointer, int button);
	public abstract boolean touchUp(int screenX, int screenY, int pointer, int button);
	public abstract boolean touchDragged(int screenX, int screenY, int pointer);
	public abstract boolean scrolled(int amount);
	public void mouseMoved(int x,int y){};
	public abstract void dispose();
	
	public boolean disposed=false;
}