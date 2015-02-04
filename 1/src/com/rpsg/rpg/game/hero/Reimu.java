package com.rpsg.rpg.game.hero;

import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.IRPGObject;

public class Reimu extends Hero{
	
	private static final long serialVersionUID = 1L;

	public void first(){
		imgPath="/walk_reimu.png";
		name="≤©¿ˆ¡È√Œ";
	}
	
	public void init(){
		this.images=IRPGObject.generateImages(Hero.RES_PATH+imgPath, HERO_WIDTH, HERO_HEIGHT);
	}
}
