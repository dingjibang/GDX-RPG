package com.rpsg.rpg.utils.game;


import com.rpsg.rpg.object.base.items.Equipment;
import com.rpsg.rpg.object.base.items.Item;
import com.rpsg.rpg.object.base.items.SpellCard;
import com.rpsg.rpg.object.rpgObject.Hero;
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
		return throwItem(type, item, 1);
	}
	public static boolean throwItem(String type,Item item,int count){
		if(item.count==0){
			if(!GameViews.global.getItems(type).remove(item)){
				AlertUtil.add("·Ç·¨²Ù×÷¡£", AlertUtil.Red);
				return false;
			}
		}else{
			if(item.count>=count){
				item.count-=count;
				if(item.count<=0){
					GameViews.global.getItems(type).remove(item);
					return false;
				}
			}else{
				GameViews.global.getItems(type).remove(item);
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
	
	private static boolean include=false;
	public static void addItem(Item item){
		String type=item.getClass().getSuperclass().getSimpleName().toLowerCase();
		if(!(item instanceof Equipment || item instanceof SpellCard)){
			include=false;
			GameViews.global.getItems(type).forEach((i)->{
				if(i.getClass().equals(item.getClass())){
					include=true;
					i.count++;
				}
			});
			if(!include)
				GameViews.global.getItems(type).add(item);
		}else
			GameViews.global.getItems(type).add(item);
	}
}
