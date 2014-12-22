package com.rpsg.rpg.object.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rpsg.rpg.object.rpgobj.NPC;

public class Global implements Serializable{
	private static final long serialVersionUID = 5362383606048587168L;
	public String headHeroImage="/walk_marisa.png";
	public String map="test/map.tmx";
	public int heroMapx=10;
	public int heroMapy=10;
	public int heroMapz=1;
	public int heroFace=2;
	public List<NPC> npcs=new ArrayList<NPC>();
	public int test=0;
}
