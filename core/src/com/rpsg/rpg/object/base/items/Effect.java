package com.rpsg.rpg.object.base.items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Effect implements Serializable{
	private static final long serialVersionUID = -5793312110086239037L;
	public Map<String, String> prop = new HashMap<>();
	public List<EffectBuff> buff = new ArrayList<EffectBuff>();
	public String use = "";
	
	public static class EffectBuff{
		public Buff buff;
		public EffectBuffType type = EffectBuffType.add;
		public int turn = 1;
	}
	
	public static enum EffectBuffType{
		add,remove
	}
}
