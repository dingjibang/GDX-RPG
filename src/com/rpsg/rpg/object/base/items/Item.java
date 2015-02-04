package com.rpsg.rpg.object.base.items;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.rpsg.rpg.object.rpg.Hero;

public abstract class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public Map<String, Integer> addParam = new HashMap<String, Integer>();
	public boolean throwable = true;
	public String name;
	public int count;
	public String illustration;
	public boolean disable = false;

	public boolean use(){
		return false;
	};

	public int type=TYPE_NORMAL;

	public static int TYPE_NORMAL = 0;
	public static int TYPE_USEINMAP = 1;

	public String toString() {
		return name;
	}

	public boolean use(Hero user) {
		return true;
	}
	
	public boolean use(Hero user, Hero to) {
		return true;
	}
}
