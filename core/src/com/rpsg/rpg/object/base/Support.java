package com.rpsg.rpg.object.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.view.GameViews;

public abstract class Support implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public String illustration;
	public float r=1,g=1,b=1,a=1;
	
	public void execute() {
	}
	
	public static void addToSupport(Hero hero){
		if(GameViews.global.support.size()>=4)
			return;
		GameViews.global.support.add(hero);
	}
	
	public static void removeSupport(Hero hero){
		GameViews.global.support.remove(hero);
	}
	
	public static List<Hero> getSupportList(){
		List<Hero> sup=GameViews.global.support;
		boolean flag=false;
		for(Hero hero:sup){
			if(hero.lead)
				flag=true;
			if(!GameViews.global.heros.contains(hero))
				flag=true;
			if(GameViews.global.currentHeros.contains(hero))
				flag=true;
		}
		if(flag)
			sup.clear();
		return sup;
	}
	
	public static List<Hero> getPreSupportList(){
		List<Hero> sList=getSupportList();
		@SuppressWarnings("unchecked")
		List<Hero> tmp=(List<Hero>) GameViews.global.heros.clone();
		tmp.removeAll(sList);
		tmp.removeAll(GameViews.global.currentHeros);
		List<Hero> removeList=new ArrayList<>();
		for(Hero hero:tmp)
			if(hero.lead)
				removeList.add(hero);
		tmp.removeAll(removeList);
		return tmp;
	}
}
