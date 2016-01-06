package com.rpsg.rpg.object.base;

/**
 * 战斗参数
 * 
 * @author dingjibang
 */
public class BattleParam {
	
	public BattleParam(Object obj) {
		System.out.println(obj);
	}

	private int enemy;

	public int getEnemy() {
		return enemy;
	}

	public void setEnemy(int enemy) {
		this.enemy = enemy;
	}

}
