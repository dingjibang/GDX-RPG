package com.rpsg.rpg.object.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.Spellcard;

public class Association implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int MAX_ASSOCIATION_LEVEL = 10;
	public int level = 1;
	public String name = "";
	public int favor = 0;

	public List<AssociationSkill> skills = new ArrayList<>();

	public ArrayList<AssociationSkill> getCurrentLevelLinkSkills() {
		ArrayList<AssociationSkill> result = new ArrayList<>();
		
		for(AssociationSkill skill:skills)
			if(skill.level <= level)
				result.add(skill);
		
		return result;
	}
	
	public static Association read(String json){
		Association ass = new Association();
		JsonReader reader = new JsonReader();
		JsonValue object = reader.parse(json);
		ass.name = object.getString("name");
		ass.level = object.getInt("level");
		ass.favor = object.getInt("favor");
		for(JsonValue value:object.get("skill"))
			ass.skills.add(readSkill(value.asInt()));
		
		return ass;
	}
	
	public static AssociationSkill readSkill(int id){
		FileHandle skillFile = Gdx.files.internal(Setting.SCRIPT_DATA_ASSOCIATION_SKILL+id+".grd");
		AssociationSkill skill = new AssociationSkill();
		
		JsonValue value = new JsonReader().parse(skillFile);
		
		skill.level = value.getInt("level");
		skill.spellcard = (Spellcard)RPG.ctrl.item.get(value.getInt("spellcard"));
		
		if(value.has("special") && value.get("special").size != 0){
			for(JsonValue spec:value.get("special"))
				skill.special.add(spec.asInt());
		}
		
		skill.trigger = value.has("trigger") ? value.getString("trigger") : "var result = false";
		
		
		return skill;
	}
	
	public static Association read(int id){
		return read(Gdx.files.internal(Setting.SCRIPT_DATA_ASSOCIATION+id+".grd").readString("utf-8"));
	}
	
}
