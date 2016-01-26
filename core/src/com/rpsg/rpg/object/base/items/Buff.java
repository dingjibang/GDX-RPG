package com.rpsg.rpg.object.base.items;

import java.util.HashMap;
import java.util.Map;

public class Buff {
	
	public int id;
	public String name;
	public BuffType type = BuffType.buff;
	public Map<String, String> prop = new HashMap<>();
	public String description;
	
	public static enum BuffType{
		buff,debuff
	} 
}
