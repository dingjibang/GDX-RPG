package com.rpsg.rpg.game.items.equipment;

import com.rpsg.rpg.object.base.items.Equipment;

public class TestEquip extends Equipment{

	private static final long serialVersionUID = 1L;

	public TestEquip() {
		name="��Ь";
		statusName="��Ь[����+5 ħ��+1]";
		prop.replace("defense", 5);
		prop.replace("magicDefense", 1);
		illustration="����ͨ�Ĳ�Ь��������ƶ�������������ȥ�о��ֵֹġ�";
		equipType=Equipment.EQUIP_SHOES;
		onlyFor=null;
	}

	@Override
	public boolean use() {
		return false;
	}

}
