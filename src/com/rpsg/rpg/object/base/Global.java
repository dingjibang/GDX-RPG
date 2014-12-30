package com.rpsg.rpg.object.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rpsg.rpg.object.rpgobj.Hero;
import com.rpsg.rpg.object.rpgobj.NPC;
import com.rpsg.rpg.utils.display.ColorUtil;

public class Global implements Serializable{
	private static final long serialVersionUID = 1L;
	public String map="test/map.tmx";
	public List<NPC> npcs=new ArrayList<NPC>();
	public List<Hero> currentHeros=new ArrayList<Hero>();
	public List<Hero> heros=new ArrayList<Hero>();
	public int day=0;
	public int mapColor = ColorUtil.DAY;
	
	public int level=55;
	public int exp=10253;
	public int next=25102;
	
	public int gold=120;
	
	public int x=0;
	public int y=17;
	public int z=2;
	
	public int tyear=2015;
	public int tmonth=3;
	public int tday=10;
}

