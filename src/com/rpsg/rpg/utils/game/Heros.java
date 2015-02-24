package com.rpsg.rpg.utils.game;

import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.controller.HeroController;

public class Heros {
	public static BaseScriptExecutor addNewHero(Script script,Class<? extends Hero> c){
		return script.$(()->{
			HeroController.newHero(c);
		});
	}
	
	public static BaseScriptExecutor putHeroQueue(Script script,Class<? extends Hero> c){
		return script.$(()->{
			HeroController.addHero(c);
		});
	}
	
	public static BaseScriptExecutor putHeroQueue(Script script,Class<? extends Hero> c,int position){
		return script.$(()->{
			HeroController.addHero(c,position);
		});
	}
	
	public static BaseScriptExecutor removeHeroQueue(Script script,Class<? extends Hero> c){
		return script.$(()->{
			HeroController.removeHero(c);
		});
	}
	
	public static BaseScriptExecutor deleteHeroQueue(Script script,Class<? extends Hero> c){
		return script.$(()->{
			HeroController.deleteHero(c);
		});
	}
	
	public static BaseScriptExecutor swapHeroQueue(Script script,int position){
		return script.$(()->{
			HeroController.swapHero(position);
		});
	}
	
	public static BaseScriptExecutor swapHeroQueue(Script script,int position,int swappos){
		return script.$(()->{
			HeroController.swapHero(position,swappos);
		});
	}
	
	public static BaseScriptExecutor swapHeroQueue(Script script,Class<? extends Hero> c){
		return script.$(()->{
			HeroController.swapHero(c);
		});
	}
	
	public static BaseScriptExecutor swapHeroQueue(Script script,Class<? extends Hero> c,Class<? extends Hero> sc){
		return script.$(()->{
			HeroController.swapHero(c,sc);
		});
	}
}
