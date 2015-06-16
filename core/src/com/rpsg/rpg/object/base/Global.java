package com.rpsg.rpg.object.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.rpsg.rpg.object.base.items.*;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.NPC;
import com.rpsg.rpg.utils.display.ColorUtil;
import com.rpsg.rpg.utils.display.WeatherUtil;

public class Global implements Serializable{
	private static final long serialVersionUID = 1L;
	//初始地图
	public String map="test/map.tmx";
	//地图相关
	public ArrayList<NPC> npcs=new ArrayList<NPC>();
	public ArrayList<Hero> currentHeros=new ArrayList<Hero>();
	public ArrayList<Hero> heros=new ArrayList<Hero>();
	public ArrayList<Hero> support=new ArrayList<Hero>();
	public int x=12;
	public int y=12;
	public int z=2;
	
	public boolean first = true;
	
	//时间模块
	public int mapColor = ColorUtil.DAY;
	public int tyear=2015;
	public int tmonth=3;
	public int tday=10;
	
	public int weather=WeatherUtil.WEATHER_NO;
	
	
	//金钱
	public int gold=120;
	
	
	//物品、装备等道具
	
	public Map<String, List<? extends Item>> items=new HashMap<String, List<? extends Item>>();
	{
		if(items.isEmpty()){
			items.put("equipment", new LinkedList<Equipment>());
			items.put("important", new LinkedList<Important>());
			items.put("material", new LinkedList<Material>());
			items.put("medicine", new LinkedList<Medicine>());
			items.put("spellcard", new LinkedList<SpellCard>());
			items.put("cooking", new LinkedList<Cooking>());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> getItems(String name){
		return (List<Item>) items.get(name);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getItems(Class<T> c){
		return (List<T>) items.get(c.getSimpleName().toLowerCase());
	}
	
}

