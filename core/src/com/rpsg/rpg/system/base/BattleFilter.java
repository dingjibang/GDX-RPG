package com.rpsg.rpg.system.base;

import java.util.Map;

import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.ScriptableObject;

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
		
		System.out.println(result);
		
		org.mozilla.javascript.Context.exit();
		
		return null;
		
	}
}
