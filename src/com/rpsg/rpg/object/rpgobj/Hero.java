package com.rpsg.rpg.object.rpgobj;


import com.rpsg.rpg.core.Setting;

public abstract class Hero extends IRPGObject {
	
	private static final long serialVersionUID = 1L;
	public static final int HERO_WIDTH=48;
	public static final int HERO_HEIGHT=64;
	public static final String RES_PATH=Setting.GAME_RES_WALK+"heros/";
	
	public Hero() {
		super();
		this.waitWhenCollide=false;
	}
	
	public abstract void first();
	public abstract void init();
	
	public Hero(String path) {
		super(RES_PATH+path, HERO_WIDTH, HERO_HEIGHT);
		this.waitWhenCollide=false;
	}
	
}
