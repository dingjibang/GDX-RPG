package com.rpsg.rpg.object.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.rpsg.rpg.object.rpgobj.Hero;

public class SpellCard implements Serializable{
	private static final long serialVersionUID = 1L;

	public String name;
	
	public Map<String, Integer> addParam=new HashMap<String, Integer>();
	
	public int magicConsume=0;
	
	public String illustration;
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
//	public void use(Empty empty,Hero hero)
}
