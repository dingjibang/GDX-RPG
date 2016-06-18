package com.rpsg.rpg.object.base;

import com.rpsg.rpg.core.Setting;

public class Achievement extends BaseTask{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String fileName = Setting.PERSISTENCE+"achievement.es";
	
	public static Achievement fromJSON(int id) {
		return fromJSON(id, new Achievement());
	}
	
	public boolean canEnd() {
		return end.test();
	}
	
}
