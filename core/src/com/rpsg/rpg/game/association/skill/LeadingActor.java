package com.rpsg.rpg.game.association.skill;

import com.rpsg.rpg.object.base.AssociationSkill;

public class LeadingActor extends AssociationSkill {

	private static final long serialVersionUID = 1L;

	public LeadingActor() {
		name = "主角光环";
		description = "将提供三回合的“主角光环”Buff。主角光环Buff会增幅10%的物理与魔法攻击。";
	}
	
}
