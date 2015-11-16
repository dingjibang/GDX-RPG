package com.rpsg.rpg.object.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.rpg.Hero;

public abstract class Support implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public String description;
	public float r=1,g=1,b=1,a=1;
	
	public void execute() {
	}
	
	public static void addToSupport(Hero hero){
		if(RPG.global.support.size()>=4)
			return;
		RPG.global.support.add(hero);
	}
	
	public static void removeSupport(Hero hero){
		RPG.global.support.remove(hero);
	}
	
	public static List<Hero> getSupportList(){
		List<Hero> sup=RPG.global.support;
		boolean flag=false;
		for(Hero hero:sup){
			if(hero.lead)
				flag=true;
			if(!RPG.global.heros.contains(hero))
				flag=true;
			if(RPG.global.currentHeros.contains(hero))
				flag=true;
		}
		if(flag)
			sup.clear();
		return sup;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Hero> getPreSupportList(){
		List<Hero> sList=getSupportList();
		List<Hero> tmp=(List<Hero>) RPG.global.heros.clone();
		tmp.removeAll(sList);
		tmp.removeAll(RPG.global.currentHeros);
		List<Hero> removeList=new ArrayList<>();
		for(Hero hero:tmp)
			if(hero.lead)
				removeList.add(hero);
		tmp.removeAll(removeList);
		return tmp;
	}
	
}
