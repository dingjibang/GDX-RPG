package com.rpsg.rpg.utils.game;


import com.rpsg.rpg.object.base.items.Equipment;
import com.rpsg.rpg.object.base.items.Item;
import com.rpsg.rpg.object.rpgobj.Hero;
import com.rpsg.rpg.utils.display.AlertUtil;
import com.rpsg.rpg.view.GameViews;

public class ItemUtil {

	public static void useEquip(Hero hero,Equipment equip){
		if(hero.equips.get(equip.equipType)!=null){
			Equipment tmp=hero.equips.get(equip.equipType);
			GameViews.global.getItems("equipment").add(tmp);
			replace(hero, equip, false);
		}
		hero.equips.replace(equip.equipType, equip);
		replace(hero, equip, true);
		GameViews.global.getItems("equipment").remove(equip);
	}
	
	public static boolean throwItem(String type,Item item){
		if(item.count==0){
			if(!GameViews.global.getItems(type).remove(item)){
				AlertUtil.add("非法操作。", AlertUtil.Red);
				return false;
			}
		}else{
			if(item.count>=1){
				item.count--;
				if(item.count==0){
					GameViews.global.getItems(type).remove(item);
					return false;
				}
			}
			else
				if(!GameViews.global.getItems(type).remove(item)){
					AlertUtil.add("非法操作。", AlertUtil.Red);
					return false;
				}
		}
		return true;
	}
	
	public static void takeOffEquip(Hero hero,String equipType){
		if(hero!=null && equipType!=null && hero.equips.get(equipType)!=null){
			Equipment tmp=hero.equips.get(equipType);
			replace(hero, tmp, false);
			GameViews.global.getItems("equipment").add(tmp);
			hero.equips.replace(equipType, null);
		}
	}
	
	private static void replace(Hero hero,Equipment equip,boolean add){
		r("maxhp",hero,equip,add);
		r("maxmp",hero,equip,add);
		r("attack",hero,equip,add);
		r("magicAttack",hero,equip,add);
		r("defense",hero,equip,add);
		r("magicDefense",hero,equip,add);
		r("speed",hero,equip,add);
		r("hit",hero,equip,add);
	}
	
	private static void r(String key,Hero hero,Equipment equip,boolean add){
		hero.prop.replace(key, add?hero.prop.get(key)+equip.prop.get(key):hero.prop.get(key)-equip.prop.get(key));
	}
}
