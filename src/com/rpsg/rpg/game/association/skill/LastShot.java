package com.rpsg.rpg.game.association.skill;

import com.rpsg.rpg.object.base.AssociationSkill;

public class LastShot extends AssociationSkill {

	private static final long serialVersionUID = 1L;

	public LastShot() {
		name = "最后一击";
		illustration = "在使用“追击”技能，若敌人生命达到濒死程度（最后一击的伤害值），本技能将生效，否则无效。";
	}
	
}
