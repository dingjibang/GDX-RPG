package com.rpsg.rpg.object.base.items;

import java.io.Serializable;

import com.badlogic.gdx.utils.JsonValue;

public class EffectBuff implements Serializable{
	private static final long serialVersionUID = 1L;
	public Buff buff;
	public EffectBuffType type = EffectBuffType.add;
	public int turn;//effectBuff的turn代表了他指向的那个buff会持续多少回合
	
	public static enum EffectBuffType{
		add,remove
	}
	
	public EffectBuff nextTurn(){
		// -1不减少turn
		if(turn > 0)
			--turn;
		return this;
	}
	
	public int turn(){
		return turn;
	}
	
	public EffectBuff turn(int turn) {
		this.turn = turn;
		return this;
	}
	
	public static EffectBuff fromJson(JsonValue value){
		EffectBuff eb = new EffectBuff();
		eb.type = EffectBuffType.valueOf(value.getString("type"));
		eb.turn = value.has("turn") ? value.getInt("turn") : 1;
		Integer buffId = value.has("buff") ? value.getInt("buff") : null;
		if(buffId != null) eb.buff = Buff.getById(value.getInt("buff"));
		return eb;
	}
	
	public static EffectBuff fromCallbackBuff(CallbackBuff buff){
		EffectBuff eb = new EffectBuff();
		eb.buff = buff;
		eb.turn = buff.turn - 1;
		return eb;
	}

	public EffectBuff cpy() {
		EffectBuff eb = new EffectBuff();
		eb.type = type;
		eb.turn = turn;
		if(buff != null)
			eb.buff = buff.cpy();
		return eb;
	}

	public EffectBuff addTurn() {
		turn ++;
		return this;
	}
}