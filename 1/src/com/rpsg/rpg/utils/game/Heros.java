package com.rpsg.rpg.utils.game;

import com.rpsg.rpg.object.rpgobj.Hero;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.control.HeroControler;

public class Heros {
	public static BaseScriptExecutor addNewHero(Script script,Class<? extends Hero> c){
		return script.add(()->{
			HeroControler.newHero(c);
		});
	}
	
	public static BaseScriptExecutor putHeroQueue(Script script,Class<? extends Hero> c){
		return script.add(()->{
			HeroControler.addHero(c);
		});
	}
	
	public static BaseScriptExecutor putHeroQueue(Script script,Class<? extends Hero> c,int position){
		return script.add(()->{
			HeroControler.addHero(c,position);
		});
	}
	
	public static BaseScriptExecutor removeHeroQueue(Script script,Class<? extends Hero> c){
		return script.add(()->{
			HeroControler.removeHero(c);
		});
	}
	
	public static BaseScriptExecutor deleteHeroQueue(Script script,Class<? extends Hero> c){
		return script.add(()->{
			HeroControler.deleteHero(c);
		});
	}
	
	public static BaseScriptExecutor swapHeroQueue(Script script,int position){
		return script.add(()->{
			HeroControler.swapHero(position);
		});
	}
	
	public static BaseScriptExecutor swapHeroQueue(Script script,int position,int swappos){
		return script.add(()->{
			HeroControler.swapHero(position,swappos);
		});
	}
	
	public static BaseScriptExecutor swapHeroQueue(Script script,Class<? extends Hero> c){
		return script.add(()->{
			HeroControler.swapHero(c);
		});
	}
	
	public static BaseScriptExecutor swapHeroQueue(Script script,Class<? extends Hero> c,Class<? extends Hero> sc){
		return script.add(()->{
			HeroControler.swapHero(c,sc);
		});
	}
}
