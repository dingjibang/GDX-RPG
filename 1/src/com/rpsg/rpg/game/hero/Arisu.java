package com.rpsg.rpg.game.hero;

import com.rpsg.rpg.game.sc.MagicGun;
import com.rpsg.rpg.game.sc.patientBack;
import com.rpsg.rpg.object.base.Resistance;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.IRPGObject;

public class Arisu extends Hero{
	
	private static final long serialVersionUID = 1L;

	public void first(){
		imgPath="/walk_flandre.png";
		name="结成有栖";
		jname="Yuki Arisu";
		fgname="arisu";
		tag="人类，主角";
		sc.add(new MagicGun());
		sc.add(new patientBack());
		prop.replace("hp", 50);
		prop.replace("maxhp", 150);
		prop.replace("mp", 100);
		prop.replace("maxmp", 100);
		resistance.replace("earth", Resistance.reflect);
		resistance.replace("metal", Resistance.invalid);
		resistance.replace("sun", Resistance.weak);
		prop.replace("chop", Hero.TRUE);
		prop.replace("prick", Hero.TRUE);
		
		lead=true;
		prop.put("courage", 15);			//勇气
		prop.put("perseverance", 44);		//毅力
		prop.put("express", 12);			//表达
		prop.put("knowledge", 74);			//知识
		prop.put("respect", 66);			//包容
		
	}
	
	public void init(){
		this.images=IRPGObject.generateImages(Hero.RES_PATH+imgPath, HERO_WIDTH, HERO_HEIGHT);
	}
}
