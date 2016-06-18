package com.rpsg.rpg.object.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.controller.ItemController;

public class Task extends BaseTask{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Trigger start;
	
	public static enum TriggerType{
		auto,special
	}

	public static Task fromJSON(int id) {
		Task task = fromJSON(id, new Task());
		JsonReader reader = ItemController.reader();
		JsonValue value = reader.parse(Gdx.files.internal(Setting.SCRIPT_DATA_TASK + id + ".grd"));
		task.start = Trigger.fromJSON(value.get("start"));
		return task;
	}

	public boolean canStart() {
		return start.test();
	}
	
	public void end(){
		RPG.ctrl.task.end(this);
	}
}
