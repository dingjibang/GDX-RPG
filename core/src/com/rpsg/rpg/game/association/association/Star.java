package com.rpsg.rpg.game.association.association;

import com.rpsg.rpg.game.association.skill.DoubleGun;
import com.rpsg.rpg.game.association.skill.LeadingActor;
import com.rpsg.rpg.game.association.skill.PursueAndAttack;
import com.rpsg.rpg.game.hero.Reimu;
import com.rpsg.rpg.object.base.Association;

public class Star extends Association {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Star() {
		level = 3;
		name = "星星";
		favor = 20;
		
		skills.put(1, new PursueAndAttack());
		skills.put(3, new LeadingActor());
		skills.put(5, new DoubleGun());
		specialLink.add(new SpecialLink(Reimu.class, LeadingActor.class));
	}
}
