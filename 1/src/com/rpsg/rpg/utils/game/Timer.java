package com.rpsg.rpg.utils.game;

import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;

public class Timer {
	public static BaseScriptExecutor wait(Script script,final int frame){
		return script.add((BaseScriptExecutor)()->{
			script.sleep(frame);
		});
	}
}
