package com.rpsg.rpg.game.hero;

import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.base.Resistance;
import com.rpsg.rpg.object.base.items.Spellcard;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.RPGObject;

public class Arisu extends Hero{
	
	private static final long serialVersionUID = 1L;

	public void first(){
		imgPath="/walk_yuki.png";
		name="结成有栖";
		jname="Yuki Arisu";
		fgname="arisu";
		tag="主角";
		addSpellcard(RPG.ctrl.item.get(5,Spellcard.class));
		addSpellcard(RPG.ctrl.item.get(5,Spellcard.class));
		addSpellcard(RPG.ctrl.item.get(5,Spellcard.class));
		addSpellcard(RPG.ctrl.item.get(5,Spellcard.class));
		addSpellcard(RPG.ctrl.item.get(5,Spellcard.class));
		addSpellcard(RPG.ctrl.item.get(5,Spellcard.class));
		addSpellcard(RPG.ctrl.item.get(5,Spellcard.class));
		addSpellcard(RPG.ctrl.item.get(5,Spellcard.class));
		addSpellcard(RPG.ctrl.item.get(5,Spellcard.class));
		addSpellcard(RPG.ctrl.item.get(5,Spellcard.class));
		addSpellcard(RPG.ctrl.item.get(5,Spellcard.class));
		addSpellcard(RPG.ctrl.item.get(5,Spellcard.class));
		addSpellcard(RPG.ctrl.item.get(5,Spellcard.class));
		addSpellcard(RPG.ctrl.item.get(5,Spellcard.class));
		addSpellcard(RPG.ctrl.item.get(5,Spellcard.class));
		prop.put("hp", 150);
		prop.put("maxhp", 150);
		prop.put("exp", 3);
		prop.put("mp", 100);
		prop.put("maxmp", 100);
		resistance.put("earth", Resistance.reflect);
		resistance.put("metal", Resistance.invalid);
		resistance.put("sun", Resistance.weak);
		prop.put("dead", Hero.FALSE);
		color="59669ecc";
//		equips.put(Equipment.EQUIP_SHOES, new Shoes());
		
		lead=true;
		prop.put("courage", 100);			//勇气
		prop.put("perseverance", 44);		//毅力
		prop.put("express", 12);			//表达
		prop.put("knowledge", 74);			//知识
		prop.put("respect", 66);			//包容
		
	}
	
	public void init(){
		this.images=RPGObject.generateImages(Hero.RES_PATH+imgPath, HERO_WIDTH, HERO_HEIGHT);
	}
}
