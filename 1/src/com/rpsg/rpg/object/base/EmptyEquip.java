package com.rpsg.rpg.object.base;


public class EmptyEquip extends Equip{

	public EmptyEquip() {
		name="ж�µ�ǰװ��";
		statusName="ж�µ�ǰװ��";
		attack=0;
		defense=0;
		magicAttack=0;
		magicDefense=0;
		speed=0;
		hit=0;
		illustration="�ѵ�ǰ������װ��ж�¡�";
		onlyFor=null;
	}

	@Override
	public void use() {
		
	}

}
