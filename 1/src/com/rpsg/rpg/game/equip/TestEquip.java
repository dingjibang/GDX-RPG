package com.rpsg.rpg.game.equip;

import com.rpsg.rpg.object.base.Equip;

public class TestEquip extends Equip{

	public TestEquip() {
		name="��Ь";
		statusName="��Ь[����+5 ħ��+1]";
		attack=1;
		defense=5;
		magicAttack=-1;
		magicDefense=1;
		speed=1;
		hit=0;
		illustration="����ͨ�Ĳ�Ь��ƶ�������������ȥ�о��ֵֹġ�";
		onlyFor=null;
		hp=1;
		mp=100;
	}

	@Override
	public void use() {
		
	}

}
