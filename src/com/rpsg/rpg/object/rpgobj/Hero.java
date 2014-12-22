package com.rpsg.rpg.object.rpgobj;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.core.Setting;

public class Hero extends IRPGObject {
	
	public static final int HERO_WIDTH=48;
	public static final int HERO_HEIGHT=64;
	public static final String RES_PATH=Setting.GAME_RES_WALK+"heros/";
	
	public List<IRPGObject> ants=new ArrayList<IRPGObject>();
	
	public Hero() {
		super();
		this.waitWhenCollide=false;
	}

	public Hero(String path) {
		super(RES_PATH+path, HERO_WIDTH, HERO_HEIGHT);
		this.waitWhenCollide=false;
	}
	
	public void draw(SpriteBatch batch,float alpha){
		this.getCurrentImage().draw(batch);
	}
}
