package com.rpsg.rpg.object.base;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.utils.game.GameUtil;

public class TriggerContext {
	
	public static boolean test(String script) {
		if(script == null || script.length() == 0) return true;
		
		Context jsctx = Context.enter();
		if(!GameUtil.isDesktop)
			jsctx.setOptimizationLevel(-1);
		
		ScriptableObject scope = jsctx.initStandardObjects();
		
		scope.put("RPG",scope, RPG.class);
		
		Object obj = jsctx.evaluateString(scope, script, null, 1, null);
		
		Context.exit();
		
		return (Boolean)obj;
	}
	
	
}
