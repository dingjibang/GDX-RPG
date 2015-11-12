package com.rpsg.rpg.game.association.skill;

import com.rpsg.rpg.object.base.AssociationSkill;

public class DoubleGun extends AssociationSkill {

	private static final long serialVersionUID = 1L;

	public DoubleGun() {
		name = "双重魔炮";
		description = "有西暂时学习连携者魔炮技能，在本回合将与连携者同时发动魔炮，攻击伤害为当前等级的连携者“魔炮”技能的1.8倍。";
	}
	
}
