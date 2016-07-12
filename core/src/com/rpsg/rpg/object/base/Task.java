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
	
	public boolean giveup;
	
	public String by;
	
	public TaskType type;
	
	public static enum TriggerType{
		auto,special
	}
	
	public static enum TaskType{
		main,secondly
	}

	public static Task fromJSON(int id) {
		Task task = fromJSON(id, new Task());
		JsonReader reader = ItemController.reader();
		JsonValue value = reader.parse(Gdx.files.internal(Setting.SCRIPT_DATA_TASK + id + ".grd"));
		task.start = Trigger.fromJSON(value.get("start"));
		task.giveup = value.has("giveup") ? value.getBoolean("giveup") : true;
		task.type = TaskType.valueOf(value.getString("type"));
		task.by = value.has("by") ? value.getString("by") : "";
		
		return task;
	}

	public boolean canStart() {
		return start.test();
	}
	
	public void end(){
		RPG.ctrl.task.end(this);
	}
}
