package com.rpsg.rpg.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class IView {
	public abstract void init();
	public abstract void draw(SpriteBatch batch);
	public abstract void logic();
	public abstract void onkeyTyped(char character);
	public abstract void dispose();
}