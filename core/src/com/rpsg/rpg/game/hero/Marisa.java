package com.rpsg.rpg.game.hero;

import com.rpsg.rpg.game.association.association.Star;
import com.rpsg.rpg.game.sc.MagicGun;
import com.rpsg.rpg.game.sc.patientBack;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.IRPGObject;

public class Marisa extends Hero{
	
	private static final long serialVersionUID = 1L;

	public void first(){
		imgPath="/walk_marisa.png";
		name="雾雨魔理沙";
		jname="Kirisame Marisa";
		fgname="marisa";
		tag="魔法使";
		sc.add(new MagicGun());
		sc.add(new patientBack());
		prop.put("hp", 50);
		prop.put("maxhp", 150);
		prop.put("mp", 100);
		prop.put("maxmp", 100);
		association=new Star();
	}
	
	public void init(){
		this.images=IRPGObject.generateImages(Hero.RES_PATH+imgPath, HERO_WIDTH, HERO_HEIGHT);
	}
}
