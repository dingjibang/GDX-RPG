package com.rpsg.rpg.utils.game;


import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.base.items.Equipment;
import com.rpsg.rpg.object.base.items.Item;
import com.rpsg.rpg.object.base.items.SpellCard;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.utils.display.AlertUtil;

public class ItemUtil {
	
	/**
	 * 给某个角色穿上装备
	 * @param hero 角色 
	 * @param equip 装备
	 */
	public static void useEquip(Hero hero,Equipment equip){
		if(hero.equips.get(equip.equipType)!=null){
			Equipment tmp=hero.equips.get(equip.equipType);
			RPG.global.getItems("equipment").add(tmp);
			replace(hero, equip, false);
		}
		hero.equips.put(equip.equipType, equip);
		replace(hero, equip, true);
		RPG.global.getItems("equipment").remove(equip);
	}
	/**
	 * 扔掉某个道具
	 * @param type 道具类型（比如"equipment"、"spellcard"等）
	 * @param item 道具
	 * @return 是否成功丢弃
	 */
	public static boolean throwItem(String type,Item item){
		return throwItem(type, item, 1);
	}
	
	/**
	 * 扔掉某个道具
	 * @param type 道具类型（比如"equipment"、"spellcard"等）
	 * @param item 道具
	 * @param count 扔掉的数量
	 * @return 是否成功丢弃
	 */
	public static boolean throwItem(String type,Item item,int count){
		if(item.count==0){
			if(!RPG.global.getItems(type).remove(item)){
				RPG.putMessage("非法操作。", AlertUtil.Red);
				return false;
			}
		}else{
			if(item.count>=count){
				item.count-=count;
				if(item.count<=0){
					RPG.global.getItems(type).remove(item);
					return false;
				}
			}else{
				RPG.global.getItems(type).remove(item);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 从某个Hero上脱下某件装备
	 * @param hero 角色
	 * @param equipType 装备类型（是类型不是装备 ）
	 * @see  {@link Equipment}
	 */
	public static void takeOffEquip(Hero hero,String equipType){
		if(hero!=null && equipType!=null && hero.equips.get(equipType)!=null){
			Equipment tmp=hero.equips.get(equipType);
			replace(hero, tmp, false);
			RPG.global.getItems("equipment").add(tmp);
			hero.equips.put(equipType, null);
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
		hero.prop.put(key, add?hero.prop.get(key)+equip.prop.get(key):hero.prop.get(key)-equip.prop.get(key));
	}
	
	private static boolean include=false;
	
	/**
	 * 增加一个道具到仓库
	 * @param item 道具
	 */
	public static void addItem(Item item){
		String type=item.getClass().getSuperclass().getSimpleName().toLowerCase();
		if(!(item instanceof Equipment || item instanceof SpellCard)){
			include=false;
			for (Item i : RPG.global.getItems(type)) {
				if(i.getClass().equals(item.getClass())){
					include=true;
					i.count++;
				}
			}

			if(!include)
				RPG.global.getItems(type).add(item);
		}else
			RPG.global.getItems(type).add(item);
	}
}
