package com.rpsg.rpg.object.base;


public class TipEquip extends Equip{

	public TipEquip() {
		name="��ʾ";
		statusName="��ʾ";
		attack=0;
		defense=0;
		magicAttack=0;
		magicDefense=0;
		speed=0;
		hit=0;
		illustration="�����ѡ��һ��װ�������Ϸ�ѡ��ж�µ�ǰװ������������Ϊ����װ����";
		onlyFor=null;
	}
	
	public TipEquip(String tip,String ill){
		this();
		name=tip;
		illustration=ill;
	}

	@Override
	public void use() {
		
	}

}
