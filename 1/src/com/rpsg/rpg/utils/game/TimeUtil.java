package com.rpsg.rpg.utils.game;

import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.object.script.ScriptExecutor;

public class TimeUtil {
	public static ScriptExecutor wait(Script script,final int frame){
		return script.add(new ScriptExecutor(script) {
			public void step() {
				this.dispose();
			}
			public void init() {
				script.sleep(frame);
			}
		});
	}
}
