package com.rpsg.rpg.system.base;

import java.util.HashMap;
import java.util.Map;

import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.ScriptableObject;

import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.base.items.BaseItem.Context;

/**
 * 战斗拦截器模块
 * @author dingjibang
 * @see 文档“装备模型”中的拦截器模块
 */
public class BattleFilter {
	
	/**执行*/
	public static Map<String, Object> exec(String js, Context context){
		org.mozilla.javascript.Context jsctx = org.mozilla.javascript.Context.enter();
		jsctx.getWrapFactory().setJavaPrimitiveWrap(false);
		
		ScriptableObject scope = jsctx.initStandardObjects();
		
		scope.setPrototype(((NativeJavaObject)org.mozilla.javascript.Context.javaToJS(context, scope)));

		NativeObject result = (NativeObject)jsctx.evaluateString(scope, js, null, 1, null);
		
		Map<String, Object> map = new HashMap<>();
		
		Object[] ids = result.getAllIds();
		for(Object obj : ids)
			map.put(obj.toString(), result.get(obj));
		
		org.mozilla.javascript.Context.exit();
		
		if(map.containsKey("msg"))
			RPG.battleMsg(map.get("msg").toString());
		
		
		return map;
		
	}
}
