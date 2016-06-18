package com.rpsg.rpg.object.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.MathUtils;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.Target;

/**
 * 成长值计算系统
 * @author dingjibang
 *
 */
public class Grow implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
			for(String key : target.keySet())
				if(!key.equals("dead") && !key.equals("exp") && !key.equals("maxexp") && !key.equals("level"))
					target.setProp(key, target.getProp(key) + MathUtils.random(0,100));
			
			target.addProp("level", 1);
			target.setProp("maxexp", 450);
			target.setProp("exp", 0);
		}
	}
	
	public void levelUp(){
		levelUp(1);
	}
	
}
