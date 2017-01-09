package com.rpsg.rpg.object.item;

import com.badlogic.gdx.utils.JsonValue;

/**
 * GDX-RPG 笔记
 */
public class Note extends BaseItem{

	private static final long serialVersionUID = 1L;
	
	/**排序索引，从上到下显示，从大到小排序*/
	public long index;
	/**所携带的符卡的ID，如果为null则代表符卡已领取过/没有符卡*/
	public Integer spellcard;

	public Note(Integer id, JsonValue value) {
		super(id, value);
		
		packable = false;
		
		index = value.has("index") ? value.getLong("index") : 0;
		spellcard = value.has("spellcard") ? value.getInt("spellcard") : null;
	}

}
