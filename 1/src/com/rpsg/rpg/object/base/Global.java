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

public class Global implements Serializable{
	private static final long serialVersionUID = 1L;
	//��ʼ��ͼ
	public String map="test/map.tmx";
	//��ͼ���
	public List<NPC> npcs=new ArrayList<NPC>();
	public List<Hero> currentHeros=new ArrayList<Hero>();
	public List<Hero> heros=new ArrayList<Hero>();
	public int x=12;
	public int y=12;
	public int z=2;
	
	//ʱ��ģ��
	public int day=0;
	public int mapColor = ColorUtil.DAY;
	public int tyear=2015;
	public int tmonth=3;
	public int tday=10;
	
	
	//��Ǯ
	public int gold=120;
	
	
	//��Ʒ��װ���ȵ���
	
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

