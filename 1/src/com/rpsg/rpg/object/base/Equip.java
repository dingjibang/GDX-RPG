package com.rpsg.rpg.object.base;

import java.util.HashMap;
import java.util.Map;

import com.rpsg.rpg.object.rpgobj.Hero;

public abstract class Equip extends Item implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	public Map<String,Integer> prop=new HashMap<String, Integer>();
	{
		prop.put("maxhp", 0);
		prop.put("maxmp", 0);
		prop.put("attack", 0);
		prop.put("magicAttack", 0);
		prop.put("defense", 0);
		prop.put("magicDefense", 0);
		prop.put("speed", 0);
		prop.put("hit", 0);
	}
	
	public Class<? extends Hero> onlyFor;
	
	public String statusName;
	
	public String type;
	
	public static String EQUIP_SHOES="shoes";
	public static String EQUIP_CLOTHES="clothes";
	public static String EQUIP_WEAPON="weapon";
	public static String EQUIP_ORNAMENT1="ornament1";
	public static String EQUIP_ORNAMENT2="ornament2";
	
}
