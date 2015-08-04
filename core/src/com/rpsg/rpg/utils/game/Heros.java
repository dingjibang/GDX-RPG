package com.rpsg.rpg.utils.game;

import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.Balloon.BalloonType;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.controller.HeroController;

public class Heros {
	public static BaseScriptExecutor addNewHero(Script script, final Class<? extends Hero> c){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				HeroController.newHero(c);
			}
		});
	}
	
	public static BaseScriptExecutor putHeroQueue(Script script, final Class<? extends Hero> c){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				HeroController.addHero(c);
			}
		});
	}
	
	public static BaseScriptExecutor putHeroQueue(Script script, final Class<? extends Hero> c, final int position){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				HeroController.addHero(c, position);
			}
		});
	}
	
	public static BaseScriptExecutor removeHeroQueue(Script script, final Class<? extends Hero> c){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				HeroController.removeHero(c);
			}
		});
	}
	
	public static BaseScriptExecutor deleteHeroQueue(Script script, final Class<? extends Hero> c){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				HeroController.deleteHero(c);
			}
		});
	}
	
	public static BaseScriptExecutor swapHeroQueue(Script script, final int position){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				HeroController.swapHero(position);
			}
		});
	}
	
	public static BaseScriptExecutor swapHeroQueue(Script script, final int position, final int swappos){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				HeroController.swapHero(position, swappos);
			}
		});
	}
	
	public static BaseScriptExecutor swapHeroQueue(Script script, final Class<? extends Hero> c){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				HeroController.swapHero(c);
			}
		});
	}
	
	public static BaseScriptExecutor swapHeroQueue(Script script, final Class<? extends Hero> c, final Class<? extends Hero> sc){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				HeroController.swapHero(c, sc);
			}
		});
	}
	
	public static BaseScriptExecutor balloon(Script script, final Class<? extends Hero> c,final BalloonType type){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				HeroController.getHero(c).setBalloon(type);
			}
		});
	}
	
	public static BaseScriptExecutor balloon(final Script script,final BalloonType type){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				script.npc.setBalloon(type);
			}
		});
	}
	
	public static BaseScriptExecutor setHeroVisible(final Script script,final boolean vis){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				HeroController.setVisible(vis);
			}
		});
	}
}
