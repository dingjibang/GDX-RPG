package com.rpsg.rpg.object.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.controller.ItemController;

public class Achievement extends BaseTask{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String fileName = Setting.PERSISTENCE+"achievement.es";
	
	public boolean hide = false;
	
	public static Achievement fromJSON(int id) {
		Achievement ach = fromJSON(id, new Achievement());
		JsonReader reader = ItemController.reader();
		JsonValue value = reader.parse(Gdx.files.internal(Setting.SCRIPT_DATA_TASK + id + ".grd"));
		ach.hide = value.has("hide") ? value.getBoolean("hide") : false;
		return ach;
	}
	
	public boolean canEnd() {
		return end.test();
	}

	public boolean gained() {
		return RPG.ctrl.task.isAchievementGained(this);
	}
	
}
