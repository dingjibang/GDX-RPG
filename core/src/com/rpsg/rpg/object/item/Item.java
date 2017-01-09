package com.rpsg.rpg.object.item;

import com.badlogic.gdx.utils.JsonValue;

/**
 * GDX-RPG 道具
 */
public class Item extends BaseItem{

	private static final long serialVersionUID = 1L;

	/**道具指向*/
	public ItemForward forward = ItemForward.friend;
	/**道具使用范围*/
	public ItemRange range = ItemRange.one;
	/**道具使用场景**/
	public ItemOccasion occasion = ItemOccasion.all;
	/**道具是否可以给死亡的人使用**/
	public ItemDeadable deadable = ItemDeadable.no;
	/**道具是否为一次性的*/
	public boolean removeable = true;
	/**道具使用动画**/
	public int animation = 0;
	/**道具延迟值*/
	public int delay = 0;
	
	public Item(int id, JsonValue value) {
		super(id, value);
		
		forward = value.has("forward") ? ItemForward.valueOf(value.getString("forward")) : ItemForward.friend;
		range = value.has("range") ? ItemRange.valueOf(value.getString("range")) : ItemRange.one;
		occasion = value.has("occasion") ? ItemOccasion.valueOf(value.getString("occasion")) : ItemOccasion.all;
		animation = value.has("animation") ? value.getInt("animation") : 0;
		removeable = value.has("removeable") ? value.getBoolean("removeable") : true;
		deadable = value.has("deadable") ? ItemDeadable.valueOf(value.getString("deadable")) : ItemDeadable.no;
		delay = value.has("delay") ? value.getInt("delay") : 0;
	}

}
