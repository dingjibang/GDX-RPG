package com.rpsg.rpg.object.base;

import java.util.HashMap;
import java.util.Map;

public class SpellCard {
	public String name;
	
	public Map<String, Integer> addParam=new HashMap<String, Integer>();
	
	public int magicConsume=0;
	
	public String illustration;
	public String story;
	
	public int type;
	
	public static int TYPE_NORMAL=0;
	public static int TYPE_USEINMAP=1;
	
	public String toString(){
		return name;
	}
}
