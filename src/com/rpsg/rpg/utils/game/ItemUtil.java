package com.rpsg.rpg.utils.game;

import com.rpsg.rpg.object.base.Equip;
import com.rpsg.rpg.object.rpgobj.Hero;
import com.rpsg.rpg.view.GameViews;

public class ItemUtil {

	public static void useEquip(Hero hero,Equip equip){
		if(hero.equips.get(equip.type)!=null){
			Equip tmp=hero.equips.get(equip.type);
			GameViews.global.equips.add(tmp);
			replace(hero, equip, false);
		}
		hero.equips.replace(equip.type, equip);
		replace(hero, equip, true);
		GameViews.global.equips.remove(equip);
	}
	
	public static void throwEquip(Equip equip){
		GameViews.global.equips.remove(equip);
	}
	
	public static void takeOffEquip(Hero hero,String equipType){
		if(hero!=null && equipType!=null && hero.equips.get(equipType)!=null){
			Equip tmp=hero.equips.get(equipType);
			replace(hero, tmp, false);
			GameViews.global.equips.add(tmp);
			hero.equips.replace(equipType, null);
		}
	}
	
	private static void replace(Hero hero,Equip equip,boolean add){
		r("maxhp",hero,equip,add);
		r("maxmp",hero,equip,add);
		r("attack",hero,equip,add);
		r("magicAttack",hero,equip,add);
		r("defense",hero,equip,add);
		r("magicDefense",hero,equip,add);
		r("speed",hero,equip,add);
		r("hit",hero,equip,add);
	}
	
	private static void r(String key,Hero hero,Equip equip,boolean add){
		hero.prop.replace(key, add?hero.prop.get(key)+equip.prop.get(key):hero.prop.get(key)-equip.prop.get(key));
	}
}
