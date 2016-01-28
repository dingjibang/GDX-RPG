package com.rpsg.rpg.object.base;

/**
 * 战斗参数
 * 
 * @author dingjibang
 */
public class BattleParam {
	
	public int enemy;
	
	public Runnable startCallback,stopCallback;

	@Override
	public String toString() {
		return "BattleParam [enemy=" + enemy + "]";
	}

}
