package com.rpsg.rpg.utils.game;

import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;

public class Base {
	public static BaseScriptExecutor removeSelf(Script script){
		return script.$((BaseScriptExecutor)()->{
			script.npc.scripts.remove(script.callType);
		});
	}
	
	public static BaseScriptExecutor changeSelf(final Script script,final Class<? extends Script> newScript){
		return script.$(()->{
			String callType=script.callType;
			script.npc.scripts.remove(callType);
			script.npc.scripts.put(callType, newScript);
		});
	}
}