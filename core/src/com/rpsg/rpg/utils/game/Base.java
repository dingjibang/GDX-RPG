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
	
	public static BaseScriptExecutor changeSelf(final Script script,final String newScript){
		return script.set(new BaseScriptExecutor() {
			@Override
			public void init() {
				script.script = newScript;
			}
		});
	}
	
	public static BaseScriptExecutor addScript(final Script script,final String scriptstr,final CollideType type){
		return script.set(new BaseScriptExecutor() {
			@Override
			public void init() {
				script.npc.scripts.put(type, scriptstr);
			}
		});
	}
	
}