package com.rpsg.rpg.object.base.items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.object.base.Filter;
import com.rpsg.rpg.system.controller.ItemController;

public class Effect implements Serializable{
	private static final long serialVersionUID = -5793312110086239037L;
	public Map<String, Prop> prop = new HashMap<>();
	public List<EffectBuff> buff = new ArrayList<>();
	public boolean wait = false;
	private int turn = 0;//effect的turn代表多少回合后触发这个effect
	
	public Filter filter;
	
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
	
	public static Effect fromJson(JsonValue value){
		Effect e = new Effect();
		if(value.has("prop"))
			e.prop = ItemController.getPropObject(value.get("prop"));
		e.wait = value.has("wait")?value.getBoolean("wait"):false;
		e.turn = value.has("turn")?value.getInt("turn"):0;
		
		//read buff
		List<EffectBuff> buffs = new ArrayList<EffectBuff>();
		if(value.has("buff")) for(JsonValue v : value.get("buff"))
			buffs.add(EffectBuff.fromJson(v));
		
		if(value.has("filter"))
			e.filter = Filter.fromJSON(value.get("filter"));
		
		e.buff = buffs;
		return e;
	}
	
}
