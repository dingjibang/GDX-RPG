package com.rpsg.rpg.object.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.rpsg.rpg.object.rpg.Hero;

public abstract class Association implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int level = 1;
	public String name = "";
	public int favor = 0;

	public Map<Integer, AssociationSkill> skills = new HashMap<Integer, AssociationSkill>();
	public ArrayList<SpecialLink> specialLink = new ArrayList<SpecialLink>();

	public static class SpecialLink {
		public Class<? extends Hero> hero;
		public Class<? extends AssociationSkill> associationSkill;
		public SpecialLink(Class<? extends Hero> hero, Class<? extends AssociationSkill> associationSkill) {
			this.hero = hero;
			this.associationSkill = associationSkill;
		}
	}
}
