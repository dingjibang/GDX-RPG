package com.rpsg.rpg.game.items.medicine;

import com.rpsg.rpg.object.base.items.Medicine;

public class YaoWan extends Medicine{

	private static final long serialVersionUID = 1L;
	
	public YaoWan() {
		illustration="要完要完，使用后HP恢复10。";
		maxCount=0;
		name="药丸";
	}

	@Override
	public boolean use() {
		return false;
	}

}
