package com.rpsg.rpg.game.hero;

import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.IRPGObject;

public class Flandre extends Hero{
	
	private static final long serialVersionUID = 1L;

	public void first(){
		imgPath="/walk_flandre.png";
		name="Ü½À¼¶äÂ¶";
		tag="¶ñÄ§";
		jname="Flandre Scarlet";
		fgname="flandre";
	}
	
	public void init(){
		this.images=IRPGObject.generateImages(Hero.RES_PATH+imgPath, HERO_WIDTH, HERO_HEIGHT);
	}
}
