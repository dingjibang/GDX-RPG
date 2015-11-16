package com.rpsg.rpg.game.hero;

import com.rpsg.rpg.object.base.Association;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.RPGObject;

public class Marisa extends Hero{
	
	private static final long serialVersionUID = 1L;

	public void first(){
		id = 2;
		imgPath="/walk_marisa.png";
		name="雾雨魔理沙";
		jname="Kirisame Marisa";
		fgname="marisa";
		tag="魔法使";
		prop.put("hp", 50);
		prop.put("maxhp", 150);
		prop.put("mp", 100);
		prop.put("maxmp", 100);
		association=Association.read(3);
		color="ffcf70cc";
	}
	
	public void init(){
		this.images=RPGObject.generateImages(Hero.RES_PATH+imgPath, HERO_WIDTH, HERO_HEIGHT);
	}
}
