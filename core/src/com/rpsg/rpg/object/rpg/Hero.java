package com.rpsg.rpg.object.rpg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Association;
import com.rpsg.rpg.object.base.AssociationSkill;
import com.rpsg.rpg.object.base.EmptyAssociation;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.object.base.Resistance;
import com.rpsg.rpg.object.base.items.Buff;
import com.rpsg.rpg.object.base.items.Equipment;
import com.rpsg.rpg.object.base.items.Spellcard;

public class Hero extends RPGObject implements Time{

	private static final long serialVersionUID = 1L;
	public static final int HERO_WIDTH = 48;
	public static final int HERO_HEIGHT = 64;

	public static final int TRUE = 1;
	public static final int FALSE = 0;

	public static final String RES_PATH = Setting.WALK + "heros/";
	
	public int id = -1;
	
	public String name;
	public String jname;
	public String fgname;
	public String tag = "";
	public Buff support;
	public Association association = new EmptyAssociation();
	public Hero linkTo;
	public ArrayList<AssociationSkill> linkSkills = new ArrayList<AssociationSkill>();
	public String color = "000000cc";
	public float[][] face;
	public float[][] head;
	private Map<String, Integer> prop = new HashMap<String, Integer>();
	{
		//等级
		prop.put("level", 0);
		//经验
		prop.put("exp", 0);
		//最大经验值至下一次升级
		prop.put("maxexp", 0);
		//生命值
		prop.put("hp", 0);
		//最大生命值
		prop.put("maxhp", 0);
		//魔法量
		prop.put("mp", 0);
		//魔法量
		prop.put("maxmp", 0);
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
		prop.put("maxsc", 0);
		//是否是死亡状态的
		prop.put("dead", FALSE);
	}

	public boolean lead = false;
	
	public Integer getProp(String propName){
		//获取基本数值
		Integer prop = this.prop.get(propName);
		
		//支援buff叠加
		for(Hero hero : RPG.global.support){
			Buff support = hero.support;
			if(support == null) continue;
			String result = support.prop.get(propName);
			if(result == null) continue;
			
			prop = calcProp(prop, result);
			
		}
		return prop;
	}
	
	//计算数值（百分比或绝对值）
	private Integer calcProp(int value,String prop){
		try {
			if(prop.endsWith("%")){
				return value + value * Integer.valueOf(prop.substring(0, prop.length() - 1)) / 100;
			}else{
				return value + Integer.valueOf(prop);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

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

	public void init(){
		this.images=RPGObject.generateImages(Hero.RES_PATH+imgPath, HERO_WIDTH, HERO_HEIGHT);
	}

	public Hero(String path) {
		super(RES_PATH + path, HERO_WIDTH, HERO_HEIGHT);
		this.waitWhenCollide = false;
		this.drawShadow = true;
	}
	
	public boolean isDead(){
		return getProp("dead")==TRUE;
	}

	public String toString() {
		return name;
	}
	
	public boolean addSpellcard(Spellcard sc){
		if(this.sc.size()>=getProp("maxsc"))
			return false;
		for(Spellcard _sc:this.sc)
			if(_sc.id==sc.id)
				return false;
		return this.sc.add(sc);
	}

	public void addProp(String name, String p, boolean post) {
		if(p.indexOf("%")<0){
			Integer val = getProp(name);
			prop.put(name,(val==null?0:val) + Integer.parseInt(p));
		}else{
			float f = Float.parseFloat(p.split("%")[0]);
			prop.put(name, getProp(name) * (int)(f / 100));
		}
		
		if(name.equalsIgnoreCase("dead")){
			prop.put(name, Integer.parseInt(p));
		}
		
		if(post)
			postOverflow();
	}
	
	public void addProp(String name, String p) {
		addProp(name, p,true);
	}
	
	public void setProp(String name,Integer d){
		addProp(name, d+"",false);
	}
	
	public void addProps(Map<String,String> map){
		for(String key:map.keySet())
			addProp(key,map.get(key));
	}
	
	public void postOverflow(){
		for(String name:prop.keySet()){
			if(getProp(name)<0)
				prop.put(name, 0);
		}
		
		if (getProp("hp") > getProp("maxhp"))
			prop.put("hp", getProp("maxhp"));

		if (getProp("mp") > getProp("maxmp"))
			prop.put("mp", getProp("maxmp"));

	}
	
	public boolean full(String name) {
		if (name.equals("hp") || name.equals("mp"))
			return getProp("max" + name).equals(getProp(name));
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
			this.linkSkills = hero.linkSkills = new ArrayList<AssociationSkill>();
		}
	}

	private ArrayList<AssociationSkill> generateLinkList(Hero that) {
		ArrayList<AssociationSkill> list = new ArrayList<AssociationSkill>();
		
		if(this.lead){
			list = that.association.getCurrentLevelLinkSkills();
		}else if(that.lead){
			list = this.association.getCurrentLevelLinkSkills();
		}else{
			List<AssociationSkill> all = new ArrayList<AssociationSkill>();
			all.addAll(that.association.getCurrentLevelLinkSkills());
			all.addAll(this.association.getCurrentLevelLinkSkills());
			
			ArrayList<AssociationSkill> result = new ArrayList<AssociationSkill>();
			
			for(AssociationSkill skill : all)
				for(int i : skill.special)
					if(this.id == i || that.id == i)
						result.add(skill);
			list = result;
		}
		
		list.add(Global.baseLinkSpellCard);
		
		return list;
	}

	public int getLinkSize(Hero hero) {
		return generateLinkList(hero).size();
	}

	@Override
	public int getSpeed() {
		return getProp("speed");
	}
	
	@Override
	public Color getObjectColor() {
		return Color.valueOf(color);
	}

	@Override
	public String getSimpleName() {
		return name.substring(0, 1);
	}
	
	@Override
	public Object getThis() {
		return this;
	}
}
