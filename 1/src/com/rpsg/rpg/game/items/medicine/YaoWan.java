package com.rpsg.rpg.game.items.medicine;

import com.rpsg.rpg.object.base.items.Medicine;
import com.rpsg.rpg.object.rpgobj.Hero;
import com.rpsg.rpg.utils.display.AlertUtil;

public class YaoWan extends Medicine{

	private static final long serialVersionUID = 1L;
	
	public YaoWan() {
		illustration="Ҫ��Ҫ�꣬ʹ�ú�HP�ָ�10��";
		count=20;
		name="ҩ��";
	}

	public boolean use(Hero hero) {
		throwSelf("ʹ�óɹ���",AlertUtil.Green);
		return false;
	}

}
