package com.rpsg.rpg.utils.game;

import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.control.HeroControler;

public class Heros {
	public static BaseScriptExecutor addNewHero(Script script,Class<? extends Hero> c){
		return script.$(()->{
			HeroControler.newHero(c);
		});
	}
	
	public static BaseScriptExecutor putHeroQueue(Script script,Class<? extends Hero> c){
		return script.$(()->{
			HeroControler.addHero(c);
		});
	}
	
	public static BaseScriptExecutor putHeroQueue(Script script,Class<? extends Hero> c,int position){
		return script.$(()->{
			HeroControler.addHero(c,position);
		});
	}
	
	public static BaseScriptExecutor removeHeroQueue(Script script,Class<? extends Hero> c){
		return script.$(()->{
			HeroControler.removeHero(c);
		});
	}
	
	public static BaseScriptExecutor deleteHeroQueue(Script script,Class<? extends Hero> c){
		return script.$(()->{
			HeroControler.deleteHero(c);
		});
	}
	
	public static BaseScriptExecutor swapHeroQueue(Script script,int position){
		return script.$(()->{
			HeroControler.swapHero(position);
		});
	}
	
	public static BaseScriptExecutor swapHeroQueue(Script script,int position,int swappos){
		return script.$(()->{
			HeroControler.swapHero(position,swappos);
		});
	}
	
	public static BaseScriptExecutor swapHeroQueue(Script script,Class<? extends Hero> c){
		return script.$(()->{
			HeroControler.swapHero(c);
		});
	}
	
	public static BaseScriptExecutor swapHeroQueue(Script script,Class<? extends Hero> c,Class<? extends Hero> sc){
		return script.$(()->{
			HeroControler.swapHero(c,sc);
		});
	}
}
