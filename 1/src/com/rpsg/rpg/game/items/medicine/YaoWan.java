package com.rpsg.rpg.game.items.medicine;

import com.rpsg.rpg.object.base.items.Medicine;

public class YaoWan extends Medicine{

	private static final long serialVersionUID = 1L;
	
	public YaoWan() {
		illustration="Ҫ��Ҫ�꣬ʹ�ú�HP�ָ�10��";
		maxCount=0;
		name="ҩ��";
	}

	@Override
	public boolean use() {
		return false;
	}

}
