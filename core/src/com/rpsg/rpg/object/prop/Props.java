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
		Props p = new Props();
		for(int i = 0; i < value.size; i ++){
			JsonValue child = value.get(i);
			p.props.put(PropKey.valueOf(child.name), Prop.fromJSON(child));
		}
		return p;
	}
}
