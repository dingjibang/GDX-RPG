package com.rpsg.rpg.object.base;


public class EmptyEquip extends Equip{

	private static final long serialVersionUID = 1L;

	public EmptyEquip() {
		name=gloname;
		statusName="ж�µ�ǰװ��";
		illustration="�ѵ�ǰ������װ��ж�¡�";
		onlyFor=null;
		disable=true;
	}

	public static String gloname= "ж�µ�ǰװ��";

	@Override
	public boolean use() {
		return false;
	}

}
