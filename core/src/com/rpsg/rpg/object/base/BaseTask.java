package com.rpsg.rpg.object.base;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Task.TriggerType;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.ItemController;
import com.rpsg.rpg.system.ui.Image;

public abstract class BaseTask implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id;
	public String name,description,description2;
	public Integer icon;
	public TriggerType trigger;
	
	public Trigger end,gain;
	
	public static <T extends BaseTask> T fromJSON(int id,T task){
		JsonReader reader = ItemController.reader();
		JsonValue value = reader.parse(Gdx.files.internal((task instanceof Task ? Setting.SCRIPT_DATA_TASK : Setting.SCRIPT_DATA_ACHIEVEMENT) + id + ".grd"));
		task.id = id;
		task.name = value.getString("name");
		task.description = value.getString("description");
		task.description2 = value.has("description2") ? value.getString("description2") : "";
		task.icon = value.has("icon") ? value.getInt("icon") : 0;
		task.end = Trigger.fromJSON(value.get("end"));
		task.gain = value.has("gain") ? Trigger.fromJSON(value.get("gain")) : null;
		task.trigger = TriggerType.valueOf(value.getString("trigger"));
		
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

	public void gain() {
		if(gain != null){
			gain.gain();
			if(this instanceof Achievement)
				$.getIf(RPG.ctrl.task.achievementHistory(), info -> info.task == this).gained(true);
		}
	}

	public boolean hasGain() {
		return gain != null;
	}
	
}
