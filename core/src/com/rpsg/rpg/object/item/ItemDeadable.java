package com.rpsg.rpg.object.item;

/**
 * 道具是否可以给死人使用的，的标记
 */
public enum ItemDeadable {
	/**只能给活人使用*/
	no,
	/**只能给死人使用*/
	deadonly,
	/**能给所有人使用*/
	all
}
