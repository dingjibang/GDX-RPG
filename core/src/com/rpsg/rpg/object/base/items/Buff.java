package com.rpsg.rpg.object.base.items;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Buff implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public int id;
	public String name;
	public BuffType type = BuffType.buff;
	public Map<String, Prop> prop = new HashMap<>();
	public String description;
	public int turn;
	
	public static enum BuffType{
		buff,debuff
	}
	
	public Prop getProp(String key){
		return prop.get(key);
 	}
}
