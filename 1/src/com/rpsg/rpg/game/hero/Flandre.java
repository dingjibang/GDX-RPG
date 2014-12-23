package com.rpsg.rpg.game.hero;

import com.rpsg.rpg.object.rpgobj.Hero;
import com.rpsg.rpg.object.rpgobj.IRPGObject;

public class Flandre extends Hero{
	
	private static final long serialVersionUID = 1L;

	public void first(){
		imgPath="/walk_flandre.png";
	}
	
	public void init(){
		this.images=IRPGObject.generateImages(Hero.RES_PATH+imgPath, HERO_WIDTH, HERO_HEIGHT);
	}
}
