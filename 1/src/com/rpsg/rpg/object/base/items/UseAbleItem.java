package com.rpsg.rpg.object.base.items;

import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.display.AlertUtil;
import com.rpsg.rpg.utils.game.ItemUtil;

public abstract class UseAbleItem extends Item{
	private static final long serialVersionUID = 1L;

	public boolean throwSelf(String msg,Image type){
		AlertUtil.add(msg,type);
		return !(ItemUtil.throwItem(getClass().getSuperclass().getSimpleName().toLowerCase(), this));
	}
}
