package com.rpsg.rpg.utils.game;

import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.object.script.ScriptExecutor;

public class Timer {
	public static BaseScriptExecutor wait(final Script script, final int frame) {
		return script.set(new ScriptExecutor(script) {
			int time = frame;

			public void init() {
			}

			public void step() {
				if (time-- == 0)
					dispose();
			}
		});
	}
}
