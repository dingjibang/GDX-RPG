package com.rpsg.rpg.utils.game;

import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;

public class Base {
	public static BaseScriptExecutor removeSelf(final Script script){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				script.npc.scripts.remove(script.callType);
			}
		});
	}
	
	public static BaseScriptExecutor changeSelf(final Script script,final Class<? extends Script> newScript){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				String callType = script.callType;
				script.npc.scripts.remove(callType);
				script.npc.scripts.put(callType, newScript);
			}
		});
	}
	
	public static BaseScriptExecutor addScript(final Script script,final Class<? extends Script> newScript,final String type){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				script.npc.scripts.put(type, newScript);
			}
		});
	}
	
}