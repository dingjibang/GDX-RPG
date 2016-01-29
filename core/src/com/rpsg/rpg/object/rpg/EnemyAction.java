package com.rpsg.rpg.object.rpg;

import java.util.List;
import java.util.Map;

import com.rpsg.rpg.object.base.items.Item.ItemForward;
import com.rpsg.rpg.object.base.items.Item.ItemRange;
import com.rpsg.rpg.object.base.items.Spellcard;

public class EnemyAction {
	public int turn;
	public float propbabitly;
	public Spellcard act;
	public List<EnemyActionProp> prop;
	public int[] buff;
	public String special;
	public RemoveType remove;
	public ItemForward forawrd;
	public ItemRange range;

	public static enum RemoveType {
		no, // 不删除
		success, // 当触发成功后删除
		always// 无论是否成功，只触发一次
	}

	public static enum PropType {
		max, min, avg, sum
	}

	public static class EnemyActionProp {
		public PropType type;
		public ItemForward forward;
		public Map<String, String> prop;
	}
}
