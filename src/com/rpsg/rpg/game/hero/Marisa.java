package com.rpsg.rpg.game.hero;

import com.rpsg.rpg.game.sc.MagicGun;
import com.rpsg.rpg.game.sc.patientBack;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.IRPGObject;

public class Marisa extends Hero{
	
	private static final long serialVersionUID = 1L;

	public void first(){
		imgPath="/walk_marisa.png";
		name="ŒÌ”Íƒß¿Ì…≥";
		jname="Kirisame Marisa";
		fgname="marisa";
		tag="ƒß∑® π";
		sc.add(new MagicGun());
		sc.add(new patientBack());
		prop.replace("hp", 50);
		prop.replace("maxhp", 150);
		prop.replace("mp", 100);
		prop.replace("maxmp", 100);
	}
	
	public void init(){
		this.images=IRPGObject.generateImages(Hero.RES_PATH+imgPath, HERO_WIDTH, HERO_HEIGHT);
	}
}
