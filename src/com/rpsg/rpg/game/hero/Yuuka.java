package com.rpsg.rpg.game.hero;

import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.IRPGObject;

public class Yuuka extends Hero{
	
	private static final long serialVersionUID = 1L;

	public void first(){
		imgPath="/walk_yuuka.png";
		name="·ç¼ûÓÄÏã";
		jname="Kazami Yuuka";
		fgname="yuuka";
		tag="Ñý¹Ö";
	}
	
	public void init(){
		this.images=IRPGObject.generateImages(Hero.RES_PATH+imgPath, HERO_WIDTH, HERO_HEIGHT);
	}
}
