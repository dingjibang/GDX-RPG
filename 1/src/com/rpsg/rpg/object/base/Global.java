package com.rpsg.rpg.object.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.rpsg.rpg.object.base.items.*;
import com.rpsg.rpg.object.rpgobj.Hero;
import com.rpsg.rpg.object.rpgobj.NPC;
import com.rpsg.rpg.utils.display.ColorUtil;

public class Global implements Serializable{
	private static final long serialVersionUID = 1L;
	//初始地图
	public String map="test/map.tmx";
	//地图相关
	public List<NPC> npcs=new ArrayList<NPC>();
	public List<Hero> currentHeros=new ArrayList<Hero>();
	public List<Hero> heros=new ArrayList<Hero>();
	public int x=4;
	public int y=12;
	public int z=2;
	
	//时间模块
	public int day=0;
	public int mapColor = ColorUtil.DAY;
	public int tyear=2015;
	public int tmonth=3;
	public int tday=10;
	
	//debug 主角等级
	public int level=55;
	public int exp=10253;
	public int next=25102;
	
	//金钱
	public int gold=120;
	
	
	//物品、装备等道具
	
	public Map<String, List<? extends Item>> items=new HashMap<String, List<? extends Item>>();
	{
		if(items.isEmpty()){
			items.put("equips", new LinkedList<Equip>());
			items.put("important", new LinkedList<Important>());
			items.put("material", new LinkedList<Material>());
			items.put("medicine", new LinkedList<Medicine>());
			items.put("spellcard", new LinkedList<SpellCard>());
			items.put("cooking", new LinkedList<Cooking>());
		}
	}
	
}

