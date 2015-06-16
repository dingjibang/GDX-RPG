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
	public static int MAX_ASSOCIATION_LEVEL = 10;
	public int level = 1;
	public String name = "";
	public int favor = 0;

	public Map<Integer, AssociationSkill> skills = new HashMap<Integer, AssociationSkill>();
	public ArrayList<SpecialLink> specialLink = new ArrayList<SpecialLink>();

	public static class SpecialLink implements Serializable {
		private static final long serialVersionUID = 1L;
		public Class<? extends Hero> hero;
		public Class<? extends AssociationSkill> associationSkill;

		public SpecialLink(Class<? extends Hero> hero, Class<? extends AssociationSkill> associationSkill) {
			this.hero = hero;
			this.associationSkill = associationSkill;
		}
	}

	public ArrayList<AssociationSkill> getCurrentLevelLinkSkills() {
		ArrayList<AssociationSkill> t_skills=new ArrayList<AssociationSkill>();
		for(Integer i=0;i<=level;i++)
			if(skills.get(i)!=null){
				skills.get(i).t_level=i;
				t_skills.add(skills.get(i));
			}
		return t_skills;
	}

}
