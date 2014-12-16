package com.rpsg.rpg.object.heros;

import com.rpsg.rpg.object.IRPGObject;
import com.rpsg.rpg.system.Image;
import com.rpsg.rpg.system.text.Setting;

public class Hero extends IRPGObject{

	public static final int HERO_WIDTH=48;
	public static final int HERO_HEIGHT=64;
	public static final String RES_PATH=Setting.GAME_RES_WALK+"heros/";
	
	public Hero() {
		super();
		this.waitWhenCollide=false;
	}

	public Hero(Image txt) {
		super(txt, HERO_WIDTH, HERO_HEIGHT);
		this.waitWhenCollide=false;
	}

	public Hero(String path) {
		super(RES_PATH+path, HERO_WIDTH, HERO_HEIGHT);
		this.waitWhenCollide=false;
	}
}
