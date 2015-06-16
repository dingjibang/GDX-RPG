package com.rpsg.rpg.game.association.skill;

import com.rpsg.rpg.object.base.AssociationSkill;

public class Protect extends AssociationSkill {

	private static final long serialVersionUID = 1L;

	public Protect() {
		name = "守护";
		illustration = "连携者将为另一连携者抵挡本次攻击伤害，且本次伤害降低30%。";
	}
	
}
