package com.rpsg.rpg.object.item;

/**
 * 道具使用朝向
 */
public enum ItemForward {
	/**只能给我方人员使用*/
	friend,
	/**只能给敌方人员使用*/
	enemy,
	/**可以给所有人使用*/
	all,
	/**只能给自己使用*/
	self
}
