package com.rpsg.rpg.object.item;

import com.badlogic.gdx.utils.JsonValue;

/**
 * GDX-RPG 材料
 * 材料没有任何可使用的属性，所以继承{@link BaseItem}
 */
public class Material extends BaseItem{


	public Material(Integer id, JsonValue value) {
		super(id, value);
	}
}
