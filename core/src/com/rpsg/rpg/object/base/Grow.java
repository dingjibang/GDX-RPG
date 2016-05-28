package com.rpsg.rpg.object.base;

import java.util.HashMap;
import java.util.Map;

import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.Target;

/**
 * 成长值计算系统
 * @author dingjibang
 *
 */
public class Grow {
	
	class Prop{int begin,end;float grow;}
	public Map<String, Prop> prop = new HashMap<>();
	
	private Target target;
	
	public Grow(Hero hero) {
		target = hero.target;
	}
	
	public void set(String propName,int begin,int end,float grow){
		Prop prop = new Prop();
		prop.begin = begin;
		prop.end = end;
		prop.grow = grow;
		this.prop.put(propName,prop);
	}
	
	public void levelUp(int level){
		while(level -- > 0){
			int currentLevel = target.getProp("level");
			
		}
	}
	
	public void levelUp(){
		levelUp(1);
	}
	
}
