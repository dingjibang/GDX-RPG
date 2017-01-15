package com.rpsg.rpg.object.item;

import java.io.Serializable;

import com.badlogic.gdx.utils.JsonValue;

/**
 * GDX-RPG buff描述<br>
 * 他来自{@link Effect#buff}，作用是描述一个buff的持续回合，同时包含了一个buff元素。
 */
public class EffectBuff implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**buff的回合，如果是-1的话则代表无限存在，直到战斗结束或被代码消除*/
	public int turn;
	/**所描述的buff*/
	public Buff buff;
	
	public static EffectBuff fromJSON(JsonValue value) {
		EffectBuff buff = new EffectBuff();
		buff.turn = value.has("turn") ? value.getInt("turn") : 1;
		buff.buff = Buff.fromJSON(value.getInt("buff"));
		return buff;
	}
}
