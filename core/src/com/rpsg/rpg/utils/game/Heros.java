package com.rpsg.rpg.utils.game;

import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.rpg.Balloon.BalloonType;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;

public class Heros {
	public static BaseScriptExecutor addHero(Script script, final int id){
		return script.set(new BaseScriptExecutor() {
			@Override
			public void init() {
				RPG.ctrl.hero.add(id);
			}
		});
	}
	
	public static BaseScriptExecutor swapHeroQueue(Script script, final int position){
		return script.set(new BaseScriptExecutor() {
			@Override
			public void init() {
				RPG.ctrl.hero.swapHero(position);
			}
		});
	}
	
	public static BaseScriptExecutor swapHeroQueue(Script script, final int position, final int swappos){
		return script.set(new BaseScriptExecutor() {
			@Override
			public void init() {
				RPG.ctrl.hero.swapHero(position, swappos);
			}
		});
	}
	
	public static BaseScriptExecutor balloon(final Script script,final BalloonType type){
		return script.set(new BaseScriptExecutor() {
			@Override
			public void init() {
				script.npc.setBalloon(type);
			}
		});
	}
	
	public static BaseScriptExecutor setHeroVisible(final Script script,final boolean vis){
		return script.set(new BaseScriptExecutor() {
			@Override
			public void init() {
				RPG.ctrl.hero.setVisible(vis);
			}
		});
	}
}
