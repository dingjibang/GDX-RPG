package com.rpsg.rpg.game.hero;


import com.rpsg.rpg.object.base.Association;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.RPGObject;

public class Yuuka extends Hero{
	
	private static final long serialVersionUID = 1L;

	public void first(){
		imgPath="/walk_yuuka.png";
		name="风见幽香";
		jname="Kazami Yuuka";
		fgname="yuuka";
		tag="妖怪";
		id=4;
		association=Association.read(2);
		color="99cc33cc";
		prop.put("hp", 100);
		prop.put("maxhp", 100);
		prop.put("mp", 100);
		prop.put("maxmp", 100);
	}
	
	public void init(){
		this.images=RPGObject.generateImages(Hero.RES_PATH+imgPath, HERO_WIDTH, HERO_HEIGHT);
	}
}
