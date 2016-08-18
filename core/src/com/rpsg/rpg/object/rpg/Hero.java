package com.rpsg.rpg.object.rpg;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Association;
import com.rpsg.rpg.object.base.AssociationSkill;
import com.rpsg.rpg.object.base.EmptyAssociation;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.object.base.Grow;
import com.rpsg.rpg.object.base.items.Buff;
import com.rpsg.rpg.object.base.items.Equipment;
import com.rpsg.rpg.object.base.items.Prop;
import com.rpsg.rpg.object.base.items.Spellcard;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.Image;

public class Hero extends RPGObject implements Time{

	private static final long serialVersionUID = 1L;
	public static final int HERO_WIDTH = 48;
	public static final int HERO_HEIGHT = 64;

	public static final String RES_PATH = Setting.WALK + "heros/";
	
	public int id = -1;
	
	public String name;
	public String jname;
	public String fgname;
	public String tag = "";
	
	public Association association = new EmptyAssociation();
	public Hero linkTo;
	public ArrayList<AssociationSkill> linkSkills = new ArrayList<AssociationSkill>();
	public String color = "000000cc";
	public float[][] face;
	public float[][] head;
	
	public Buff support;
	
	public Target target = (new Target(){
		private static final long serialVersionUID = 1L;

		public int getProp(String propName) {
			int prop = super.getProp(propName);
			int base = prop;
			
			//支援buff叠加
			for(Hero hero : RPG.global.support){
				Buff support = hero.support;
				if(support == null) continue;
				Prop _prop = support.prop.get(propName);
				if(_prop == null) continue;
				String result = _prop.formula;
				if(result == null) continue;
				
				prop += calcProp(base, result);
			}
			
			return prop;
		};
	}).hero(this);
	
	public Grow grow = new Grow(this);

	public boolean lead = false;
	
	
	/**持有的符卡*/
	public List<Spellcard> sc = new ArrayList<Spellcard>();

	/**持有的装备*/
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
		super(RES_PATH + path);
		this.waitWhenCollide = false;
		this.drawShadow = true;
	}
	

	public String toString() {
		return name;
	}
	
	public boolean addSpellcard(Spellcard sc){
		if(this.sc.size()>=target.getProp("maxsc"))
			return false;
		for(Spellcard _sc:this.sc)
			if(_sc.id==sc.id)
				return false;
		return this.sc.add(sc);
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
		return target.getProp("speed");
	}
	
	@Override
	public Color getObjectColor() {
		return Color.valueOf(color);
	}

	@Override
	public String getSimpleName() {
		return name.substring(0, 1);
	}
	
	public Object getThis() {
		return this;
	}
	
	public Image defaultFG(){
		return Res.getNP(Setting.IMAGE_FG + fgname + "/Normal.png");
	}

}
