package com.rpsg.rpg.object.base.items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Effect implements Serializable{
	private static final long serialVersionUID = -5793312110086239037L;
	public Map<String, Prop> prop = new HashMap<>();
	public List<EffectBuff> buff = new ArrayList<EffectBuff>();
	public boolean wait = false;
	public int turn = 1;
	
	public String use = "";
	
	public static class EffectBuff implements Serializable{
		private static final long serialVersionUID = 1L;
		public Buff buff;
		public EffectBuffType type = EffectBuffType.add;
	}
	
	public static enum EffectBuffType{
		add,remove
	}
	
	public Map<String,String> asStringMap(){
		Map<String,String> map = new HashMap<>();
		for(String key : prop.keySet())
			map.put(key, prop.get(key).formula);
		return map;
	}
}
