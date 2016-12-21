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
	private int turn = 0;//effect的turn代表多少回合后触发这个effect
	
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
		e.wait = json.has("wait")?json.getBoolean("wait"):false;
		e.turn = json.has("turn")?json.getInt("turn"):0;
		
		//read buff
		List<EffectBuff> buffs = new ArrayList<EffectBuff>();
		if(json.has("buff")) for(JsonValue value : json.get("buff"))
			buffs.add(EffectBuff.fromJson(value));
		
		e.buff = buffs;
		return e;
	}
	
}
