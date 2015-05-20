package com.rpsg.rpg.object.base;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.view.GameViews;

public abstract class Support {
	
	public String name;
	public String illustration;
	public Color color;
	
	public static Color TYPE_UP=Color.valueOf("40c43a");
	
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
		tmp.removeIf((hero)->hero.lead);
		return tmp;
	}
}
