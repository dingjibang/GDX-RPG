package com.rpsg.rpg.utils.game;

import com.rpsg.rpg.object.rpg.CollideType;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;

public class Base {
	public static BaseScriptExecutor removeSelf(final Script script){
		return script.set(new BaseScriptExecutor() {
			@Override
			public void init() {
				script.npc.scripts.remove(script.callType);
			}
		});
	}
	
	public static BaseScriptExecutor changeSelf(final Script script,final Class<? extends Script> newScript){
		return script.set(new BaseScriptExecutor() {
			@Override
			public void init() {
				CollideType callType = script.callType;
				script.npc.scripts.remove(callType);
//				TODO script.npc.scripts.put(callType, newScript);
			}
		});
	}
	
	public static BaseScriptExecutor addScript(final Script script,final Class<? extends Script> newScript,final CollideType type){
		return script.set(new BaseScriptExecutor() {
			@Override
			public void init() {
//				TODO script.npc.scripts.put(type, newScript);
			}
		});
	}
	
}