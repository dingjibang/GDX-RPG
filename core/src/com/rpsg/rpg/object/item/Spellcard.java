package com.rpsg.rpg.object.item;

import com.badlogic.gdx.utils.JsonValue;

/**
 * GDX-RPG 符卡
 */
public class Spellcard extends BaseItem {

	private static final long serialVersionUID = 1L;
	
	/**符卡的耗蓝*/
	public int cost;
	/**符卡的使用范围*/
	public ItemRange range = ItemRange.one;
	public ItemForward forward = ItemForward.all;
	/**符卡使用场景*/
	public ItemOccasion occasion = ItemOccasion.all;
	/**符卡是否可以给死人使用*/
	public ItemDeadable deadable = ItemDeadable.no;
	/**符卡在战斗时使用播放的动画*/
	public int animation;
	/**符卡的故事*/
	public String description2;
	/**符卡的减速值*/
	public int delay = 0;

	public Spellcard(Integer id, JsonValue value) {
		super(id, value);
		
		description2 = value.has("description2") ? value.getString("description2") : "";
		forward = value.has("forward") ? ItemForward.valueOf(value.getString("forward")) : ItemForward.friend;
		range = value.has("range") ? ItemRange.valueOf(value.getString("range")) : ItemRange.one;
		animation = value.has("animation") ? value.getInt("animation") : 0;
		cost = value.has("cost") ? value.getInt("cost") : 0;
		occasion = value.has("occasion") ? ItemOccasion.valueOf(value.getString("occasion")) : ItemOccasion.all;
		deadable = value.has("deadable") ? ItemDeadable.valueOf(value.getString("deadable")) : ItemDeadable.no;
		delay = value.has("delay") ? value.getInt("delay") : 0;
	}

}
