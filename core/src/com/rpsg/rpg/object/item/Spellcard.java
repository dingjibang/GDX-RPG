package com.rpsg.rpg.object.item;

import com.badlogic.gdx.utils.JsonValue;

/**
 * GDX-RPG 符卡
 */
public class Spellcard extends UseableItem {

	private static final long serialVersionUID = 1L;
	
	/**符卡的耗蓝*/
	public int cost;
	/**符卡的故事*/
	public String description2;

	public Spellcard(Integer id, JsonValue value) {
		super(id, value);
		
		description2 = value.has("description2") ? value.getString("description2") : "";
		cost = value.has("cost") ? value.getInt("cost") : 0;
	}

}
