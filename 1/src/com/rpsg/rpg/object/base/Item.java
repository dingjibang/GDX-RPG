package com.rpsg.rpg.object.base;

import java.util.HashMap;
import java.util.Map;

public abstract class Item {
	public Map<String, Integer> addParam=new HashMap<String, Integer>();
	public boolean throwable=true;
	public String name;
	public int maxCount;
	public String illustration;
	public boolean disable=false;
	public abstract boolean use();
	
	public String toString(){
		return name;
	}
}
