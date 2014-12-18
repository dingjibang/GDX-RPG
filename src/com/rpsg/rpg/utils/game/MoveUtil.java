package com.rpsg.rpg.utils.game;

import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.object.script.ScriptExecutor;

public class MoveUtil {
	public static ScriptExecutor move(Script script,final int step){
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
	
	public static ScriptExecutor turn(Script script,final int faceTo){
		return script.add(new ScriptExecutor(script) {
			public void step() {
				this.dispose();
			}
			public void init() {
				script.npc.turn(faceTo);
			}
		});
	}
}
