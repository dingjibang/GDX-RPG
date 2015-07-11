package com.rpsg.rpg.game.hero;

import com.rpsg.rpg.game.items.equipment.Shoes;
import com.rpsg.rpg.game.sc.MagicGun;
import com.rpsg.rpg.game.sc.patientBack;
import com.rpsg.rpg.object.base.Resistance;
import com.rpsg.rpg.object.base.items.Equipment;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.IRPGObject;

public class Arisu extends Hero{
	
	private static final long serialVersionUID = 1L;

	public void first(){
		imgPath="/walk_flandre.png";
		name="结成有栖";
		jname="Yuki Arisu";
		fgname="arisu";
		tag="主角";
		sc.add(new MagicGun());
		sc.add(new patientBack());
		prop.put("hp", 150);
		prop.put("maxhp", 150);
		prop.put("exp", 3);
		prop.put("mp", 100);
		prop.put("maxmp", 100);
		resistance.put("earth", Resistance.reflect);
		resistance.put("metal", Resistance.invalid);
		resistance.put("sun", Resistance.weak);
		prop.put("chop", Hero.TRUE);
		prop.put("prick", Hero.TRUE);
		color="59669ecc";
		equips.put(Equipment.EQUIP_SHOES, new Shoes());
		
		lead=true;
		prop.put("courage", 100);			//勇气
		prop.put("perseverance", 44);		//毅力
		prop.put("express", 12);			//表达
		prop.put("knowledge", 74);			//知识
		prop.put("respect", 66);			//包容
		
	}
	
	public void init(){
		this.images=IRPGObject.generateImages(Hero.RES_PATH+imgPath, HERO_WIDTH, HERO_HEIGHT);
	}
}
