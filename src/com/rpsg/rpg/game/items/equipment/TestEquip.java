package com.rpsg.rpg.game.items.equipment;

import com.rpsg.rpg.object.base.items.Equipment;

public class TestEquip extends Equipment{

	private static final long serialVersionUID = 1L;

	public TestEquip() {
		name="草鞋";
		statusName="草鞋[防御+5 魔防+1]";
		prop.replace("defense", 5);
		prop.replace("magicDefense", 1);
		illustration="很普通的草鞋，仅仅是贫穷的象征，穿上去感觉怪怪的。";
		equipType=Equipment.EQUIP_SHOES;
		onlyFor=null;
	}

	@Override
	public boolean use() {
		return false;
	}

}
