package com.rpsg.rpg.object.base;

import com.rpsg.rpg.game.association.skill.PursueAndAttack;

public class EmptyAssociation extends Association{
	private static final long serialVersionUID = 1L;

	public EmptyAssociation(){
		level = 1;
		name = "无";
		favor = 0;
		skills.put(1,new PursueAndAttack());
	}
}
