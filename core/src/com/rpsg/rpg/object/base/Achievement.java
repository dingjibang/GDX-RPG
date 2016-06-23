package com.rpsg.rpg.object.base;

import com.rpsg.rpg.core.Setting;

public class Achievement extends BaseTask{
	
	public static String fileName = Setting.PERSISTENCE + "achievement.es";

	public static Achievement fromJSON(int value) {
		return fromJSON(value, new Achievement());
	}

	public boolean canEnd() {
		return end.test();
	}
	
}
