package com.rpsg.rpg.object.item;

import com.badlogic.gdx.utils.JsonValue;

/**
 * GDX-RPG “可使用”的道具，他继承了{@link BaseItem}，而{@link Spellcard}和{@link Item}继承了他，相比普通的{@link BaseItem}物品，他具有“可被使用的”能力。<br>
 * 他存储了一些基本“可使用”的信息，如使用的{@link ItemForward 目标}、{@link ItemRange 范围}等信息，也拥有一个{@link Effect 效果变量}，用来存储道具被使用后的数值操作。
 * */
public class UsableItem extends EffectableItem{

	private static final long serialVersionUID = 1L;
	
	/**道具/符卡指向*/
	public ItemForward forward = ItemForward.friend;
	/**道具/符卡使用范围*/
	public ItemRange range = ItemRange.one;
	/**道具/符卡使用场景**/
	public ItemOccasion occasion = ItemOccasion.all;
	/**道具/符卡是否可以给死亡的人使用**/
	public ItemDeadable deadable = ItemDeadable.no;
	
	/**道具/符卡使用时播放的动画**/
	public int animation = 0;
	/**道具/符卡延迟值*/
	public int delay = 0;

	public UsableItem(Integer id, JsonValue value) {
		super(id, value);
		
		forward = value.has("forward") ? ItemForward.valueOf(value.getString("forward")) : ItemForward.friend;
		range = value.has("range") ? ItemRange.valueOf(value.getString("range")) : ItemRange.one;
		occasion = value.has("occasion") ? ItemOccasion.valueOf(value.getString("occasion")) : ItemOccasion.all;
		animation = value.has("animation") ? value.getInt("animation") : 0;
		deadable = value.has("deadable") ? ItemDeadable.valueOf(value.getString("deadable")) : ItemDeadable.no;
		delay = value.has("delay") ? value.getInt("delay") : 0;
	}

}
