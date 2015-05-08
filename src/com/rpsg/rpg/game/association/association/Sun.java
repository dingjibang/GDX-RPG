package com.rpsg.rpg.game.association.association;

import com.rpsg.rpg.game.association.skill.AmplificationMagic;
import com.rpsg.rpg.game.association.skill.Immortal;
import com.rpsg.rpg.game.association.skill.Protect;
import com.rpsg.rpg.game.association.skill.PursueAndAttack;
import com.rpsg.rpg.game.association.skill.Strike;
import com.rpsg.rpg.object.base.Association;

public class Sun extends Association {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Sun() {
		level = 10;
		name = "太阳";
		favor = 20;
		
		skills.put(1, new PursueAndAttack());
		skills.put(3, new Strike());
		skills.put(5, new Protect());
		skills.put(7, new AmplificationMagic());
		skills.put(9, new Immortal());
	}
}
