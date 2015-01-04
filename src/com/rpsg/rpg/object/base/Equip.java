package com.rpsg.rpg.object.base;

import com.rpsg.rpg.object.rpgobj.Hero;

public abstract class Equip extends Item{
	public int attack;
	public int magicAttack;
	public int defense;
	public int magicDefense;
	public int speed;
	public int hit;
	public int hp;
	public int mp;
	
	public Class<? extends Hero> onlyFor;
	
	public String statusName;
	
	public int type;
	
	public static int EQUIP_SHOES=0;
	public static int EQUIP_CLOTHES=1;
	public static int EQUIP_WEAPON=2;
	public static int EQUIP_ORNAMENT1=3;
	public static int EQUIP_ORNAMENT2=4;
}
