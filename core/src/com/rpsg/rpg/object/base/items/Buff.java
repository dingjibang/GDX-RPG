package com.rpsg.rpg.object.base.items;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Buff implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public int id;
	public String name;
	public BuffType type = BuffType.buff;
	public Map<String, String> prop = new HashMap<>();
	public String description;
	
	public static enum BuffType{
		buff,debuff
	}
	
	public String getProp(String key){
		return prop.get(key) == null ? "0" : prop.get(key);
 	}
}
