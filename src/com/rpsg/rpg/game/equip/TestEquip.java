package com.rpsg.rpg.game.equip;

import com.rpsg.rpg.object.base.items.Equip;

public class TestEquip extends Equip{

	private static final long serialVersionUID = 1L;

	public TestEquip() {
		name="草鞋";
		statusName="草鞋[防御+5 魔防+1]";
		prop.replace("defense", 5);
		prop.replace("magicDefense", 1);
		illustration="很普通的草鞋，仅仅是贫穷的象征，穿上去感觉怪怪的。";
		type=Equip.EQUIP_SHOES;
		onlyFor=null;
	}

	@Override
	public boolean use() {
		return false;
	}

}
