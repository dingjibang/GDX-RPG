package com.rpsg.rpg.game.items.medicine;

import com.rpsg.rpg.object.base.items.Medicine;
import com.rpsg.rpg.object.rpgobj.Hero;
import com.rpsg.rpg.utils.display.AlertUtil;

public class YaoWan extends Medicine{

	private static final long serialVersionUID = 1L;
	
	public YaoWan() {
		illustration="要完要完，使用后HP恢复10。";
		count=20;
		name="药丸";
	}

	public boolean use(Hero hero) {
		throwSelf("使用成功。",AlertUtil.Green);
		return false;
	}

}
