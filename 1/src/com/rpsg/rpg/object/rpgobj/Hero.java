package com.rpsg.rpg.object.rpgobj;


import java.util.HashMap;
import java.util.Map;

import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Equip;

public abstract class Hero extends IRPGObject {
	
	private static final long serialVersionUID = 1L;
	public static final int HERO_WIDTH=48;
	public static final int HERO_HEIGHT=64;
	public static final String RES_PATH=Setting.GAME_RES_WALK+"heros/";
	
	public String name;
	
	public Map<String,Integer> prop=new HashMap<String, Integer>();
	{
		prop.put("level", 1);
		prop.put("hp", 0);
		prop.put("maxhp", 1);
		prop.put("mp", 0);
		prop.put("maxmp", 1);
		prop.put("attack", 0);
		prop.put("magicAttack", 0);
		prop.put("defense", 0);
		prop.put("magicDefense", 0);
		prop.put("speed", 0);
		prop.put("hit", 0);
	}
	
	public Map<String,Equip> equips=new HashMap<String, Equip>();
	{
		equips.put(Equip.EQUIP_SHOES, null);
		equips.put(Equip.EQUIP_CLOTHES, null);
		equips.put(Equip.EQUIP_WEAPON, null);
		equips.put(Equip.EQUIP_ORNAMENT1, null);
		equips.put(Equip.EQUIP_ORNAMENT2, null);
	}
	
	public String getEquipName(String name){
		return equips.get(name)==null?"нч":equips.get(name).name;
	}
	
	public int getEquipValue(String name,String value){
		return equips.get(name)==null?0:equips.get(name).prop.get(value);
	}
	
	public Hero() {
		super();
		this.waitWhenCollide=false;
	}
	
	public abstract void first();
	public abstract void init();
	
	public Hero(String path) {
		super(RES_PATH+path, HERO_WIDTH, HERO_HEIGHT);
		this.waitWhenCollide=false;
	}
	
}
