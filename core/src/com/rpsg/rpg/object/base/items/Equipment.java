package com.rpsg.rpg.object.base.items;

import java.io.Serializable;

import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.controller.ItemController;


public class Equipment extends BaseItem implements Serializable{
	private static final long serialVersionUID = 1L;

	//public Class<? extends Hero> onlyFor;
	//There is no Class differecnce
	public String onlyFor;
	
	public String description2;
	
	public String equipType;
	
	public int animation;
	
	public static String EQUIP_SHOES="EQUIP_SHOES";
	public static String EQUIP_CLOTHES="EQUIP_CLOTHES";
	public static String EQUIP_WEAPON="EQUIP_WEAPON";
	public static String EQUIP_ORNAMENT1="EQUIP_ORNAMENT1";
	public static String EQUIP_ORNAMENT2="EQUIP_ORNAMENT2";
	@Override
	public Result use(Context ctx) {
		if(ctx.self == null)
			return Result.faild();
		Hero hero = ctx.self.parentHero;
		
		RPG.ctrl.item.takeOff(hero, equipType);
		
		hero.equips.put(equipType, this);
		ItemController.replace(hero, this, true);//计算穿上装备后的Hero属性数值变化
		
		RPG.ctrl.item.remove(this);
		
		return Result.success();
	}
	
}
