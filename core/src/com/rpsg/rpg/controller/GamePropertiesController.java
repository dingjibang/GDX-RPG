package com.rpsg.rpg.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.core.File;
import com.rpsg.rpg.core.Path;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;

/**
 * 游戏各种属性、配置管理器=。=<br>
 * 配置读取的是json文件，源自 [assets/script/prop/] 文件夹下
 */
public class GamePropertiesController {
	Map<String, JsonValue> props = new HashMap<>();
	JsonReader reader;
	
	public GamePropertiesController() {
		reader = new JsonReader();

		JsonValue index = File.readJSON(Path.SCRIPT_DATA_PROP + "index.grd");

		for(String fileName : index.get("prop").asStringArray()){
			FileHandle file = Gdx.files.internal(Path.SCRIPT_DATA_PROP + fileName);
			props.put(file.name().replaceAll(".grd", ""), reader.parse(file).get("prop"));
		}


	}
	
	public JsonValue get(String propName) {
		return props.get(propName);
	}

	public JsonValue get(String propName, String childId) {
		JsonValue json = props.get(propName);
		if(json.isArray())
			for(JsonValue value : json)
				if(value.has("id") && value.getString("id").equals(childId))
					return value;
		return null;
	}
}
