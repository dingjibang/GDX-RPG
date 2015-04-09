package com.rpsg.rpg.utils.game;

import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.object.script.ScriptExecutor;
import com.rpsg.rpg.system.base.Initialization;
import com.rpsg.rpg.system.base.ThreadPool;
import com.rpsg.rpg.system.controller.HeroController;
import com.rpsg.rpg.view.GameViews;


public class Move {
	public static BaseScriptExecutor move(Script script,final int step){
		return script.$(new ScriptExecutor(script) {
			public void step() {
				if(script.npc.walkStack.size()==0)
					this.dispose();
			}
			public void init() {
				script.npc.walk(step);
				script.npc.testWalk();
			}
		});
	}
	
	public static BaseScriptExecutor turn(Script script,final int faceTo){
		return script.$(()->{
			script.npc.turn(faceTo);
		});
	}
	
	public static BaseScriptExecutor faceToHero(Script script){
		return script.$(()->{
				script.npc.turn(HeroController.getHeadHero().getReverseFace());
		});
	}
	
	public static BaseScriptExecutor teleportAnotherMap(Script script,String map,int x,int y,int z){
		return script.$(()->{
			GameViews.global.map="test/"+map;
			GameViews.global.x=x;
			GameViews.global.y=y;
			GameViews.global.z=z;
			ThreadPool.pool.clear();
			Initialization.restartGame();
			HeroController.reinitByTeleport();
		});
	}
}
