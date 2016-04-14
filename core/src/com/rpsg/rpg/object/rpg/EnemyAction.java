package com.rpsg.rpg.object.rpg;

import com.rpsg.rpg.object.base.items.Spellcard;

public class EnemyAction {
	public int propbabitly;
	public Spellcard act;
	public String formula;
	public RemoveType remove;

	public static enum RemoveType {
		no, // 不删除
		success, // 当触发成功后删除
		always// 无论是否成功，只触发一次
	}
	
	public static EnemyAction attack(){
		EnemyAction action = new EnemyAction();
		action.propbabitly = 100;
		action.act = Spellcard.attack();
		action.formula = "true";
		action.remove = RemoveType.no;
		return action;
	}
	
	public static EnemyAction defense(){
		EnemyAction action = new EnemyAction();
		action.propbabitly = 100;
		action.act = Spellcard.defense();
		action.formula = "true";
		action.remove = RemoveType.no;
		return action;
	}

}
