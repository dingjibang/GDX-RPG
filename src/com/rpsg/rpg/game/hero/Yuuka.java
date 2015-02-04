package com.rpsg.rpg.game.hero;

import com.rpsg.rpg.game.sc.MagicGun;
import com.rpsg.rpg.object.rpgObject.Hero;
import com.rpsg.rpg.object.rpgObject.IRPGObject;

public class Yuuka extends Hero{
	
	private static final long serialVersionUID = 1L;

	public void first(){
		imgPath="/walk_yuuka.png";
		name="·ç¼ûÓÄÏã";
	}
	
	public void init(){
		this.images=IRPGObject.generateImages(Hero.RES_PATH+imgPath, HERO_WIDTH, HERO_HEIGHT);
	}
}
