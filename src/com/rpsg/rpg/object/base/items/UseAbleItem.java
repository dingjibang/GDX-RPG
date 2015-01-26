package com.rpsg.rpg.object.base.items;

import com.rpsg.rpg.system.base.Image;
import com.rpsg.rpg.utils.display.AlertUtil;
import com.rpsg.rpg.utils.game.ItemUtil;

public abstract class UseAbleItem extends Item{
	private static final long serialVersionUID = 1L;

	public void throwSelf(String msg,Image type){
		ItemUtil.throwItem(getClass().getSuperclass().getSimpleName().toLowerCase(), this);
		AlertUtil.add(msg,type);
	}
}
