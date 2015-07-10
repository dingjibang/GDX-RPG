package com.rpsg.rpg.game.items.equipment;

import com.rpsg.rpg.object.base.items.Equipment;

public class YvBi extends Equipment{

	private static final long serialVersionUID = 1L;

	public YvBi() {
		name="御币";
		statusName="御币[攻击+12 魔攻+3]";
		prop.put("attack", 12);
		prop.put("magicAttack", 3);
		illustration="博丽灵梦手里常持的家伙。";
		equipType=Equipment.EQUIP_SHOES;
		onlyFor=null;
		icon=3;
	}

	@Override
	public boolean use() {
		return false;
	}

}
