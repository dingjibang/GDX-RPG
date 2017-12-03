package com.rpsg.rpg.object.item;

import com.badlogic.gdx.utils.JsonValue;

/**
 * GDX-RPG 道具
 */
public class Item extends UsableItem {

	private static final long serialVersionUID = 1L;

	
	/**道具是否为一次性的，即使用后消失*/
	public boolean removeable = true;
	
	
	public Item(Integer id, JsonValue value) {
		super(id, value);
		
		removeable = value.has("removeable") ? value.getBoolean("removeable") : true;
	}

}
