package com.rpsg.rpg.object.base.items;

import com.rpsg.rpg.object.base.items.ItemOption.ItemForward;
import com.rpsg.rpg.object.base.items.ItemOption.ItemRange;
import com.rpsg.rpg.object.rpg.Hero;

public class Item extends BaseItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**道具指向*/
	public ItemForward forward = ItemForward.friend;
	
	/**道具使用范围*/
	public ItemRange range = ItemRange.one;

}
