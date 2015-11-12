package com.rpsg.rpg.object.base.items;

import com.rpsg.rpg.object.base.items.Item.ItemDeadable;
import com.rpsg.rpg.object.base.items.Item.ItemForward;
import com.rpsg.rpg.object.base.items.Item.ItemOccasion;
import com.rpsg.rpg.object.base.items.Item.ItemRange;
import com.rpsg.rpg.object.rpg.Hero;


public class Spellcard extends BaseItem {
	
	private static final long serialVersionUID = 1L;
	
	public int cost;
	public ItemRange range = ItemRange.one;
	public ItemForward forward = ItemForward.all;
	public ItemOccasion occasion = ItemOccasion.all;
	public ItemDeadable deadable = ItemDeadable.no;
	public int success;
	
	public Hero user2;
	
	public int animation;

	public String description2;
	
}
