package com.rpsg.rpg.object.item;

import com.badlogic.gdx.utils.JsonValue;

/**
 * GDX-RPG 装备
 */
public class Equipment extends BaseItem{
	
	private static final long serialVersionUID = 1L;

	/**装备部位*/
	public static enum Parts {
		shoes, clothes, weapon, ornament1, ornament2
	}
	
	/**装备仅限于某人穿（hero.name）*/
	public String onlyFor;
	/**装备故事描述*/
	public String description2;
	/**装备部位类型*/
	public Parts equipType;
	/**装备在战斗中使用时，所播放的动画的ID*/
	public int animation;
	
	public Equipment(Integer id, JsonValue value) {
		super(id, value);
		
		packable = false;
		
		onlyFor = value.has("onlyFor") ? value.getString("onlyFor") : null;
		description2 = value.has("description2") ? value.getString("description2") : "";
		equipType = Parts.valueOf(value.getString("equipType"));
		animation = value.has("animation") ? value.getInt("animation") : 0;
	}
	
}
