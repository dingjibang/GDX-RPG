package com.rpsg.rpg.game.association.association;

import com.rpsg.rpg.game.association.skill.Protect;
import com.rpsg.rpg.game.association.skill.PursueAndAttack;
import com.rpsg.rpg.object.base.Association;

public class Devil extends Association {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Devil() {
		level = 3;
		name = "恶魔";
		favor = 20;
		
		skills.put(1, new PursueAndAttack());
		skills.put(3, new Protect());
	}
}
