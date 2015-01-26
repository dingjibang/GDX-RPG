package com.rpsg.rpg.game.items.equipment;

import com.rpsg.rpg.game.hero.Yuuka;
import com.rpsg.rpg.object.base.items.Equipment;

public class Sunshade extends Equipment{

	private static final long serialVersionUID = 1L;

	public Sunshade() {
		name="阳伞";
		statusName="草鞋[攻击+5 魔攻+1 命中-1]";
		prop.replace("attack", 5);
		prop.replace("magicAttack", 1);
		prop.replace("hit", -1);
		illustration="看似很普通的一把阳伞，幽香小姐似乎无时无刻都带在身边。不过据说有人看见幽香小姐用其射出了巨大的魔炮。";
		equipType=Equipment.EQUIP_WEAPON;
		onlyFor=Yuuka.class;
		throwable=false;
	}

	@Override
	public boolean use() {
		return false;
	}

}
