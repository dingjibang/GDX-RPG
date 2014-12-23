package com.rpsg.rpg.utils.game;

import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.object.script.ScriptExecutor;
import com.rpsg.rpg.system.control.HeroControler;

public class Move {
	public static BaseScriptExecutor move(Script script,final int step){
		return script.add(new ScriptExecutor(script) {
			public void step() {
				if(script.npc.walkStack.size()==0)
					this.dispose();
			}
			public void init() {
				script.npc.walk(step);
			}
		});
	}
	
	public static BaseScriptExecutor turn(Script script,final int faceTo){
		return script.add((BaseScriptExecutor)()->{
			script.npc.turn(faceTo);
		});
	}
	
	public static BaseScriptExecutor faceToHero(Script script){
		return script.add((BaseScriptExecutor)()->{
				script.npc.turn(HeroControler.getHeadHero().getReverseFace());
		});
	}
}
