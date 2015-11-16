package com.rpsg.rpg.object.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rpsg.rpg.object.base.items.Spellcard;

public class AssociationSkill implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public int id;
	public int level;
	public Spellcard spellcard;
	public List<Integer> special = new ArrayList<>();
	
	@Override
	public boolean equals(Object skill){
		return this.id == ((AssociationSkill)skill).id;
	}
	
}
