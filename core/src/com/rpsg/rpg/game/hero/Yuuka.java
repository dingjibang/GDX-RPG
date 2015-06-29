package com.rpsg.rpg.game.hero;


import com.rpsg.rpg.game.association.association.Sun;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.IRPGObject;

public class Yuuka extends Hero{
	
	private static final long serialVersionUID = 1L;

	public void first(){
		imgPath="/walk_yuuka.png";
		name="风见幽香";
		jname="Kazami Yuuka";
		fgname="yuuka";
		tag="妖怪";
		association=new Sun();
		color="99cc33cc";
		prop.put("hp", 100);
		prop.put("maxhp", 100);
		prop.put("mp", 100);
		prop.put("maxmp", 100);
	}
	
	public void init(){
		this.images=IRPGObject.generateImages(Hero.RES_PATH+imgPath, HERO_WIDTH, HERO_HEIGHT);
	}
}
