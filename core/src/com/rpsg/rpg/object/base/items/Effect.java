package com.rpsg.rpg.object.base.items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.system.controller.ItemController;

public class Effect implements Serializable{
	private static final long serialVersionUID = -5793312110086239037L;
	public Map<String, Prop> prop = new HashMap<>();
	public List<EffectBuff> buff = new ArrayList<>();
	public boolean wait = false;
	private int turn = 0;
	
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

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}
	
	public static Effect fromJson(JsonValue json){
		Effect e = new Effect();
		if(json.has("prop"))
			e.prop = ItemController.getPropObject(json.get("prop"));
		e.use = json.has("use")?json.getString("use"):"";
		e.wait = json.has("wait")?json.getBoolean("wait"):false;
		e.turn = json.has("turn")?json.getInt("turn"):0;
		
		List<EffectBuff> buffs = new ArrayList<EffectBuff>();
		if(json.has("buff")) for(JsonValue value : json.get("buff")){
			EffectBuff eb = new EffectBuff();
			eb.type = EffectBuffType.valueOf(value.getString("type"));
			Integer buffId = value.has("buff") ? value.getInt("buff") : null;
			if(buffId != null) eb.buff = Buff.getById(value.getInt("buff"));
			buffs.add(eb);
		}
		
		e.buff = buffs;
		return e;
	}
	
}
