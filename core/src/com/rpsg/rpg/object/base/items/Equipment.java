package com.rpsg.rpg.object.base.items;

import com.rpsg.rpg.object.rpg.Hero;

public class Equipment extends BaseItem implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	public Class<? extends Hero> onlyFor;
	
	public String description2;
	
	public String equipType;
	
	public int animation;
	
	public static String EQUIP_SHOES="EQUIP_SHOES";
	public static String EQUIP_CLOTHES="EQUIP_CLOTHES";
	public static String EQUIP_WEAPON="EQUIP_WEAPON";
	public static String EQUIP_ORNAMENT1="EQUIP_ORNAMENT1";
	public static String EQUIP_ORNAMENT2="EQUIP_ORNAMENT2";
	
}
