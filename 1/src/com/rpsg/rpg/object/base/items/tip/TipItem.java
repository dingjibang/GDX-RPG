package com.rpsg.rpg.object.base.items.tip;

import com.rpsg.rpg.object.base.items.Item;


public class TipItem extends Item{
	private static final long serialVersionUID = 1L;

	public TipItem(){
		name="��ʾ";
		illustration="���������Ϸ�ѡ����߷��࣬���Ϊ��ǰ����ĵ����б��������߲鿴������Ϣ��˫�����߽��й��ܲ�����";
	}

	@Override
	public boolean use() {
		return false;
	}
}
