package com.rpsg.rpg.game.equip;

import com.rpsg.rpg.object.base.items.Equip;

public class TestEquip extends Equip{

	private static final long serialVersionUID = 1L;

	public TestEquip() {
		name="��Ь";
		statusName="��Ь[����+5 ħ��+1]";
		prop.replace("defense", 5);
		prop.replace("magicDefense", 1);
		illustration="����ͨ�Ĳ�Ь��������ƶ�������������ȥ�о��ֵֹġ�";
		type=Equip.EQUIP_SHOES;
		onlyFor=null;
	}

	@Override
	public boolean use() {
		return false;
	}

}
