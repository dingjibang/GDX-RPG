package com.rpsg.rpg.object.base;

import java.io.Serializable;

import com.rpsg.rpg.object.rpgobj.Hero;

public class SpellCard extends Item implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public int magicConsume=0;
	
	public int maxCount=0;
	
	public String story;
	
	public int type;
	
	public static int TYPE_NORMAL=0;
	public static int TYPE_USEINMAP=1;
	
	public int added=0;
	
	public String toString(){
		return name;
	}
	
	public boolean use(){
		return true;
	}
	public boolean use(Hero user,Hero to){
		return true;
	}
}
