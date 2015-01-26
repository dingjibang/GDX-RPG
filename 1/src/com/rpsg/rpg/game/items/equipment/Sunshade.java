package com.rpsg.rpg.game.items.equipment;

import com.rpsg.rpg.game.hero.Yuuka;
import com.rpsg.rpg.object.base.items.Equipment;

public class Sunshade extends Equipment{

	private static final long serialVersionUID = 1L;

	public Sunshade() {
		name="��ɡ";
		statusName="��Ь[����+5 ħ��+1 ����-1]";
		prop.replace("attack", 5);
		prop.replace("magicAttack", 1);
		prop.replace("hit", -1);
		illustration="���ƺ���ͨ��һ����ɡ������С���ƺ���ʱ�޿̶�������ߡ�������˵���˿�������С����������˾޴��ħ�ڡ�";
		equipType=Equipment.EQUIP_WEAPON;
		onlyFor=Yuuka.class;
		throwable=false;
	}

	@Override
	public boolean use() {
		return false;
	}

}
