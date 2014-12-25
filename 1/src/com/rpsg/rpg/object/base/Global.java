package com.rpsg.rpg.object.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.rpsg.rpg.object.rpgobj.Hero;
import com.rpsg.rpg.object.rpgobj.NPC;

public class Global implements Serializable{
	private static final long serialVersionUID = 1L;
	public String map="test/map.tmx";
	public List<NPC> npcs=new ArrayList<NPC>();
	public List<Hero> currentHeros=new ArrayList<Hero>();
	public List<Hero> heros=new ArrayList<Hero>();
	public int day=0;
	public int mapColor=Color.WHITE.toIntBits();
	
}

