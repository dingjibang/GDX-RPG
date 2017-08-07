package com.rpsg.rpg.object.prop;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.utils.JsonValue;

/**
 * GDX-RPG 基础数值管理器
 */
public class Props{
	
	Map<PropKey, Prop> props = new HashMap<>();
	
	public Prop get(PropKey type) {
		return props.get(type);
	}
	
	public static Props fromJSON(JsonValue value) {
		Props props = new Props();

		for(int i = 0; i < value.size; i ++){
			Prop prop = Prop.fromJSON(value.get(i));
			props.props.put(prop.name, prop);
		}
		return props;
	}
}
