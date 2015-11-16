package com.rpsg.rpg.game.hero;

import com.rpsg.rpg.object.base.Association;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.RPGObject;

public class Reimu extends Hero{
	
	private static final long serialVersionUID = 1L;

	public void first(){
		id = 3;
		imgPath="/walk_reimu.png";
		name="博丽灵梦";
		jname="Hakurei Reimu";
		fgname="reimu";
		tag="巫女";
		association=Association.read(4);
		color="d93426cc";
		prop.put("hp", 100);
		prop.put("maxhp", 100);
		prop.put("mp", 100);
		prop.put("maxmp", 100);
	}
	
	public void init(){
		this.images=RPGObject.generateImages(Hero.RES_PATH+imgPath, HERO_WIDTH, HERO_HEIGHT);
	}
}
