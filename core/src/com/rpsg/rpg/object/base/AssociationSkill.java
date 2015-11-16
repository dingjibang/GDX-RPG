package com.rpsg.rpg.object.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rpsg.rpg.object.base.items.Spellcard;

public class AssociationSkill implements Serializable {
	private static final long serialVersionUID = 1L;
	
	int level;
	Spellcard spellcard;
	List<Integer> special = new ArrayList<>();
	
	@Override
	public boolean equals(Object skill){
		return this.getClass().equals(skill);
	}
	
}
