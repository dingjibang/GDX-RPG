package com.rpsg.rpg.object.base.items.tip;

import com.rpsg.rpg.object.base.items.Equipment;


public class TipEquip extends Equipment{

	private static final long serialVersionUID = 1L;

	public TipEquip() {
		name="��ʾ";
		statusName="��ʾ";
		illustration="�����ѡ��һ��װ�������Ϸ�ѡ��ж�µ�ǰװ������������Ϊ����װ����ʹ���������϶��Ϸ�װ�������Խ��б����¹�����";
		onlyFor=null;
		disable=true;
	}
	
	public TipEquip(String tip,String ill){
		this();
		name=tip;
		illustration=ill;
	}

	@Override
	public boolean use() {
		return false;
	}

}
