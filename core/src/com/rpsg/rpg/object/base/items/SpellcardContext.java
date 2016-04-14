package com.rpsg.rpg.object.base.items;

import java.lang.reflect.Field;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import com.rpsg.rpg.object.rpg.Target;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;

public class SpellcardContext{
	public int level,exp,maxexp,hp,maxhp,mp,attack,magicAttack,defense,magicDefense,speed,evasion,hit,maxsc,dead;
	
	public static Double eval(Target self, Target enemy, String formula) {
		try {
			Context ctx = Context.enter();
			if(!GameUtil.isDesktop)
				ctx.setOptimizationLevel(-1);
			
			ScriptableObject scope = ctx.initStandardObjects();
			
			scope.put("self", scope, build(self));
			scope.put("enemy", scope, build(enemy));
			
			Object obj = ctx.evaluateString(scope, formula, null, 1, null);
			
			Context.exit();
			
			return (Double)obj;
		} catch (Exception e) {
			Logger.error("无法执行脚本", e);
			e.printStackTrace();
		}
		return null;
	}
	
	public static SpellcardContext build(Target target){
		SpellcardContext ctx = new SpellcardContext();
		
		try {
			for(Field f : target.getClass().getFields()){
				f.setInt(target, target.prop.get(f.getName()));
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return ctx;
	}
}
