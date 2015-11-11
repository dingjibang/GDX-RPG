package com.rpsg.rpg.object.base.items;

import com.rpsg.rpg.object.base.items.Item.ItemForward;
import com.rpsg.rpg.object.base.items.Item.ItemRange;


public class Spellcard extends BaseItem {
	
	private static final long serialVersionUID = 1L;
	
	public int cost;
	public ItemRange range = ItemRange.one;
	public ItemForward forward = ItemForward.all;
	public int success;
	
	public int animation;

	public String description2;
	
}
