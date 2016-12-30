package com.rpsg.rpg.core;

import com.badlogic.gdx.Gdx;

/**
 *	GDX-RPG 游戏工具类 
 */
public class Game {
	public static final int STAGE_WIDTH = 1280, STAGE_HEIGHT = 720;
	
	public static int width(){
		return Gdx.graphics.getWidth();
	}
	
	public static int height(){
		return Gdx.graphics.getHeight();
	}
}
