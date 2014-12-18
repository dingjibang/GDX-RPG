package com.rpsg.rpg.utils.game;

import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.object.script.ScriptExecutor;

public class BaseUtil {
	public static ScriptExecutor removeSelf(Script script){
		return script.add(new ScriptExecutor(script){
			public void init(){
				script.npc.scripts.remove(script.callType);
				this.dispose();
			}
		});
	}
	
	public static ScriptExecutor changeSelf(Script script,final Class<? extends Script> newScript){
		return script.add(new ScriptExecutor(script){
			public void init(){
				String callType=script.callType;
				script.npc.scripts.remove(callType);
				script.npc.scripts.put(callType, newScript);
				this.dispose();
			}
		});
	}
}