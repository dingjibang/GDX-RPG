package com.rpsg.rpg.utils.game;

import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;

public class Timer {
	public static BaseScriptExecutor wait(final Script script,final int frame){
		return script.$((BaseScriptExecutor) new BaseScriptExecutor() {
			@Override
			public void init() {
				script.sleep(frame);
			}
		});
	}
}
