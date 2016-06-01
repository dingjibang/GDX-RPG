package com.rpsg.rpg.object.rpg;

import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.base.items.BaseItem;

public class EnemyDrop {
	public int item;
	public int rate;
	
	private boolean empty = false;
	
	public EnemyDrop() {
		empty = true;
	}
	
	public EnemyDrop(int item,int rate) {
		this.item = item;
		this.rate = rate;
	}
	
	public boolean isEmpty(){
		return empty;
	}
	
	public BaseItem getItem(){
		return RPG.ctrl.item.get(item);
	}
}
