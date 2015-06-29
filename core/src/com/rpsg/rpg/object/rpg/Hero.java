package com.rpsg.rpg.object.rpg;


import java.util.ArrayList;
import java.util.HashMap;
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
import com.rpsg.rpg.object.base.items.SpellCard;

public abstract class Hero extends IRPGObject {
	
	private static final long serialVersionUID = 1L;
	public static final int HERO_WIDTH=48;
	public static final int HERO_HEIGHT=64;
	
	public static final int TRUE=1;
	public static final int FALSE=0;
	
	public static final String RES_PATH=Setting.GAME_RES_WALK+"heros/";
	
	public String name;
	public String jname;
	public String fgname;
	public String tag="";
	public Support support =null;
	public Association association=new EmptyAssociation();
	public Hero linkTo;
	public ArrayList<AssociationSkill> linkSkills = new ArrayList<AssociationSkill>();
	public String color="000000cc";
	public Map<String,Integer> prop=new HashMap<String, Integer>();
	{
		prop.put("level", 1);
		prop.put("exp", 0);
		prop.put("maxexp", 10);
		prop.put("hp", 0);
		prop.put("maxhp", 1);
		prop.put("mp", 0);
		prop.put("maxmp", 1);
		prop.put("attack", 0);
		prop.put("magicAttack", 0);
		prop.put("defense", 0);
		prop.put("magicDefense", 0);
		prop.put("speed", 0);
		prop.put("hit", 0);
		prop.put("maxsc", 10);
		prop.put("dead", FALSE);
		prop.put("chop", FALSE);
		prop.put("shoot", FALSE);
		prop.put("prick", FALSE);
	}


	public boolean lead=false;
	
	public Map<String,Resistance> resistance=new HashMap<String, Resistance>();
	{
		resistance.put("earth", Resistance.normal);
		resistance.put("fire", Resistance.normal);
		resistance.put("metal", Resistance.normal);
		resistance.put("moon", Resistance.normal);
		resistance.put("physical", Resistance.normal);
		resistance.put("star", Resistance.normal);
		resistance.put("sun", Resistance.normal);
		resistance.put("water", Resistance.normal);
		resistance.put("wood", Resistance.normal);
	}
	
	public List<SpellCard> sc=new ArrayList<SpellCard>();
	
	public Map<String,Equipment> equips=new HashMap<String, Equipment>();
	{
		equips.put(Equipment.EQUIP_SHOES, null);
		equips.put(Equipment.EQUIP_CLOTHES, null);
		equips.put(Equipment.EQUIP_WEAPON, null);
		equips.put(Equipment.EQUIP_ORNAMENT1, null);
		equips.put(Equipment.EQUIP_ORNAMENT2, null);
	}
	
	public String getEquipName(String name){
		return equips.get(name)==null?"无":equips.get(name).name;
	}
	
	public int getEquipValue(String name,String value){
		return equips.get(name)==null?0:equips.get(name).prop.get(value);
	}
	
	public Hero() {
		super();
		this.waitWhenCollide=false;
	}
	
	public abstract void first();
	public abstract void init();
	
	public Hero(String path) {
		super(RES_PATH+path, HERO_WIDTH, HERO_HEIGHT);
		this.waitWhenCollide=false;
	}
	
	public String toString(){
		return name;
	}
	
	public boolean subProp(String name,int c){
		if(prop.get(name)-c<0)
			return false;
		else
			prop.put(name, prop.get(name)-c);
		return true;
	}
	
	public void addProp(String name,int c){
		if(name.equals("hp") || name.equals("mp"))
			if(prop.get(name)+c>prop.get("max"+name))
				prop.put(name, prop.get("max"+name));
			else
				prop.put(name, prop.get(name)+c);
		else
			prop.put(name, prop.get(name)+c);
	}
	
	public boolean full(String name){
		if(name.equals("hp") || name.equals("mp"))
			return prop.get("max"+name).equals(prop.get(name));
		return false;
	}
	
	@Override
	public String getName(){
		return name;
	}
	
	public void link(Hero hero,boolean link){
		if(link){
			this.linkTo=hero;
			hero.linkTo=this;
			this.linkSkills=hero.linkSkills=generateLinkList(hero);
//			System.out.println(hero.name+"与"+this.name+"连携将得到技能："+skills.toString());
		}else{
			this.linkTo=null;
			hero.linkTo=null;
			for(AssociationSkill s:linkSkills)
				s.t_level=0;
			this.linkSkills=hero.linkSkills=new ArrayList<AssociationSkill>();
		}
	}
	
	private ArrayList<AssociationSkill> generateLinkList(Hero hero){
		ArrayList<AssociationSkill> skills=new ArrayList<AssociationSkill>();
		tryAddLink(this.association.getCurrentLevelLinkSkills(),skills);
		tryAddLink(hero.association.getCurrentLevelLinkSkills(),skills);
		if(!hero.lead && !this.lead){
			ArrayList<AssociationSkill> available=new ArrayList<AssociationSkill>();
			available.add(skills.get(0));//add "PursueAndAttack" when ever.
			setAvailableList(this,hero,available,skills);
			setAvailableList(hero,this,available,skills);
			skills=available;
		}
		return skills;
	}
	
	public int getLinkSize(Hero hero){
		return generateLinkList(hero).size();
	}

	private void setAvailableList(Hero hero, Hero hero2,ArrayList<AssociationSkill> available, ArrayList<AssociationSkill> skills) {
		for(AssociationSkill skill:skills)
			for(SpecialLink link:hero.association.specialLink)
				if((link.hero.equals(hero.getClass()) || link.hero.equals(hero2.getClass())) && link.associationSkill.equals(skill.getClass()))
					available.add(skill);
	}

	private void tryAddLink(ArrayList<AssociationSkill> currentLevelLinkSkills, ArrayList<AssociationSkill> skills) {
		for(AssociationSkill s:currentLevelLinkSkills)
			if(!skills.contains(s))
				skills.add(s);
	}

}
