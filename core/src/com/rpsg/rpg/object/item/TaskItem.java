package com.rpsg.rpg.object.item;

import com.badlogic.gdx.utils.JsonValue;

/**
 * GDX-RPG 任务道具
 * 任务道具一般只是单纯在背包里没有效果，但为了照顾特殊情况，任务道具也可以被“使用”，当任务道具被使用时，会对玩家身边最近的一个NPC发送“onuse"命令（再说）
 */
public class TaskItem extends BaseItem{

	boolean usable = false;

	public TaskItem(Integer id, JsonValue value) {
		super(id, value);
	}

}
