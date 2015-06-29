package com.rpsg.rpg.game.hero;

import com.rpsg.rpg.game.association.association.Devil;
import com.rpsg.rpg.game.association.support.AnimaUp;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.IRPGObject;

public class Flandre extends Hero{
	
	private static final long serialVersionUID = 1L;

	public void first(){
		imgPath="/walk_flandre.png";
		name="芙兰朵露";
		tag="恶魔";
		jname="Flandre Scarlet";
		fgname="flandre";
		association=new Devil();
		support=new AnimaUp();
		color="d55d41cc";
	}
	
	public void init(){
		this.images=IRPGObject.generateImages(Hero.RES_PATH+imgPath, HERO_WIDTH, HERO_HEIGHT);
	}
}
