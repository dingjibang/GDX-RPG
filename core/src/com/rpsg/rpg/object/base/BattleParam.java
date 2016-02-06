package com.rpsg.rpg.object.base;

import java.io.Serializable;

/**
 * 战斗参数
 * 
 * @author dingjibang
 */
public class BattleParam implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public int enemy;
	
	public Runnable startCallback,stopCallback;

	@Override
	public String toString() {
		return "BattleParam [enemy=" + enemy + "]";
	}

}
