package com.rpsg.rpg.object.rpg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Association;
import com.rpsg.rpg.object.base.Association.SpecialLink;
import com.rpsg.rpg.object.base.AssociationSkill;
import com.rpsg.rpg.object.base.EmptyAssociation;
import com.rpsg.rpg.object.base.Resistance;
import com.rpsg.rpg.object.base.Support;
import com.rpsg.rpg.object.base.items.Equipment;
import com.rpsg.rpg.object.base.items.Spellcard;

public abstract class Hero extends RPGObject {

	private static final long serialVersionUID = 1L;
	public static final int HERO_WIDTH = 48;
	public static final int HERO_HEIGHT = 64;

	public static final int TRUE = 1;
	public static final int FALSE = 0;

	public static final String RES_PATH = Setting.WALK + "heros/";

	public String name;
	public String jname;
	public String fgname;
	public String tag = "";
	public Support support = null;
	public Association association = new EmptyAssociation();
	public Hero linkTo;
	public ArrayList<AssociationSkill> linkSkills = new ArrayList<AssociationSkill>();
	public String color = "000000cc";
	public Map<String, Integer> prop = new HashMap<String, Integer>();
	{
		//等级
		prop.put("level", 1);
		//经验
		prop.put("exp", 0);
		//最大经验值至下一次升级
		prop.put("maxexp", 10);
		//生命值
		prop.put("hp", 0);
		//最大生命值
		prop.put("maxhp", 1);
		//魔法量
		prop.put("mp", 0);
		//魔法量
		prop.put("maxmp", 1);
		//攻击
		prop.put("attack", 0);
		//魔法攻击
		prop.put("magicAttack", 0);
		//防御
		prop.put("defense", 0);
		//魔法防御
		prop.put("magicDefense", 0);
		//速度
		prop.put("speed", 0);
		//准确率
		prop.put("hit", 0);
		//最大可携带副卡量
		prop.put("maxsc", 10);
		//是否是死亡状态的
		prop.put("dead", FALSE);
	}

	public boolean lead = false;

	public LinkedHashMap<String, Resistance> resistance = new LinkedHashMap<String, Resistance>();
	{
		resistance.put("sun", Resistance.normal);
		resistance.put("moon", Resistance.normal);
		resistance.put("star", Resistance.normal);
		resistance.put("metal", Resistance.normal);
		resistance.put("water", Resistance.normal);
		resistance.put("earth", Resistance.normal);
		resistance.put("fire", Resistance.normal);
		resistance.put("wood", Resistance.normal);
		resistance.put("physical", Resistance.normal);
	}

	public List<Spellcard> sc = new ArrayList<Spellcard>();

	public LinkedHashMap<String, Equipment> equips = new LinkedHashMap<String, Equipment>();
	{
		equips.put(Equipment.EQUIP_SHOES, null);
		equips.put(Equipment.EQUIP_CLOTHES, null);
		equips.put(Equipment.EQUIP_WEAPON, null);
		equips.put(Equipment.EQUIP_ORNAMENT1, null);
		equips.put(Equipment.EQUIP_ORNAMENT2, null);
	}

	public Hero() {
		super();
		this.waitWhenCollide = false;
		this.drawShadow = true;
	}

	public abstract void first();

	public abstract void init();

	public Hero(String path) {
		super(RES_PATH + path, HERO_WIDTH, HERO_HEIGHT);
		this.waitWhenCollide = false;
		this.drawShadow = true;
	}
	
	public boolean isDead(){
		return prop.get("dead")==TRUE;
	}

	public String toString() {
		return name;
	}
	
	public boolean addSpellcard(Spellcard sc){
		if(this.sc.size()>=prop.get("maxsc"))
			return false;
		for(Spellcard _sc:this.sc)
			if(_sc.id==sc.id)
				return false;
		return this.sc.add(sc);
	}

	public void addProp(String name, String p) {
		if(p.indexOf("%")<0){
			prop.put(name, prop.get(name) + Integer.parseInt(p));
		}else{
			float f = Float.parseFloat(p.split("%")[0]);
			prop.put(name, prop.get(name) * (int)(f / 100));
		}
		
		if(name.equalsIgnoreCase("dead")){
			prop.put(name, Integer.parseInt(p));
		}
		
		postOverflow();
	}
	
	public void addProps(Map<String,String> map){
		for(String key:map.keySet())
			addProp(key,map.get(key));
	}
	
	public void postOverflow(){
		for(String name:prop.keySet()){
			if(prop.get(name)<0)
				prop.put(name, 0);
		}
		
		if (prop.get("hp") > prop.get("maxhp"))
			prop.put("hp", prop.get("maxhp"));

		if (prop.get("mp") > prop.get("maxmp"))
			prop.put("mp", prop.get("maxmp"));

	}
	
	public boolean full(String name) {
		if (name.equals("hp") || name.equals("mp"))
			return prop.get("max" + name).equals(prop.get(name));
		return false;
	}

	@Override
	public String getName() {
		return name;
	}

	public void link(Hero hero, boolean link) {
		if (link) {
			this.linkTo = hero;
			hero.linkTo = this;
			this.linkSkills = hero.linkSkills = generateLinkList(hero);
		} else {
			this.linkTo = null;
			hero.linkTo = null;
			for (AssociationSkill s : linkSkills)
				s.t_level = 0;
			this.linkSkills = hero.linkSkills = new ArrayList<AssociationSkill>();
		}
	}

	private ArrayList<AssociationSkill> generateLinkList(Hero hero) {
		ArrayList<AssociationSkill> skills = new ArrayList<AssociationSkill>();
		tryAddLink(this.association.getCurrentLevelLinkSkills(), skills);
		tryAddLink(hero.association.getCurrentLevelLinkSkills(), skills);
		if (!hero.lead && !this.lead) {
			ArrayList<AssociationSkill> available = new ArrayList<AssociationSkill>();
			available.add(skills.get(0));// add "PursueAndAttack" when ever.
			setAvailableList(this, hero, available, skills);
			setAvailableList(hero, this, available, skills);
			skills = available;
		}
		return skills;
	}

	public int getLinkSize(Hero hero) {
		return generateLinkList(hero).size();
	}

	private void setAvailableList(Hero hero, Hero hero2, ArrayList<AssociationSkill> available, ArrayList<AssociationSkill> skills) {
		for (AssociationSkill skill : skills)
			for (SpecialLink link : hero.association.specialLink)
				if ((link.hero.equals(hero.getClass()) || link.hero.equals(hero2.getClass())) && link.associationSkill.equals(skill.getClass()))
					available.add(skill);
	}

	private void tryAddLink(ArrayList<AssociationSkill> currentLevelLinkSkills, ArrayList<AssociationSkill> skills) {
		for (AssociationSkill s : currentLevelLinkSkills)
			if (!skills.contains(s))
				skills.add(s);
	}

}
