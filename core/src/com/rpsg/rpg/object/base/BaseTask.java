package com.rpsg.rpg.object.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.ItemController;
import com.rpsg.rpg.system.ui.Image;

public abstract class BaseTask {
	public int id;
	public String name,description;
	public Integer icon;
	
	public Trigger end,gain;
	
	public static <T extends BaseTask> T fromJSON(int id,T task){
		JsonReader reader = ItemController.reader();
		JsonValue value = reader.parse(Gdx.files.internal((task instanceof Task ? Setting.SCRIPT_DATA_TASK : Setting.SCRIPT_DATA_ACHIEVEMENT) + id + ".grd"));
		task.id = id;
		task.name = value.getString("name");
		task.description = value.getString("description");
		task.icon = value.has("icon") ? value.getInt("icon") : 0;
		task.end = Trigger.fromJSON(value.get("end"));
		task.gain = Trigger.fromJSON(value.get("gain"));
		
		return task;
	}
	
	public Image getIcon(){
		return getIcon(id);
	}
	
	public static Image getIcon(int id){
		String name = Setting.IMAGE_ICONS+"t"+id+".png";
		if(Gdx.files.internal(name).exists())
			return Res.get(name);
		return getDefaultIcon();
	}
	
	public static Image getDefaultIcon(){
		return Res.get(Setting.IMAGE_ICONS+"t0.png");
	}
	
	public boolean canEnd(){
		return end.test();
	}
	
}
