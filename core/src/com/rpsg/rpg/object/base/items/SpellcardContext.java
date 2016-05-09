package com.rpsg.rpg.object.base.items;

import java.lang.reflect.Field;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import com.rpsg.rpg.object.rpg.Target;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;

public class SpellcardContext extends BaseContext{
	
	public static Double eval(Target self, Target target, String formula) {
		try {
			Context ctx = Context.enter();
			if(!GameUtil.isDesktop)
				ctx.setOptimizationLevel(-1);
			
			ScriptableObject scope = ctx.initStandardObjects();
			
			scope.put("self", scope, build(self));
			scope.put("target", scope, build(target));
			
			Object obj = ctx.evaluateString(scope, formula, null, 1, null);
			
			Context.exit();
			
			try {
				return (Double)obj;
			} catch (Exception e) {
				return Integer.valueOf(obj.toString()).doubleValue();
			}
		} catch (Exception e) {
			Logger.error("无法执行脚本", e);
			e.printStackTrace();
		}
		return null;
	}
	
	public static SpellcardContext build(Target target){
		SpellcardContext ctx = new SpellcardContext();
		
		try {
			for(Field f : ctx.getClass().getFields()){
				f.setInt(ctx, target.getProp(f.getName()));
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return ctx;
	}
}
