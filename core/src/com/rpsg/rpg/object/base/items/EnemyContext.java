package com.rpsg.rpg.object.base.items;

import java.lang.reflect.Field;
import java.util.List;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import com.rpsg.rpg.object.rpg.EnemyAction;
import com.rpsg.rpg.object.rpg.Target;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;

public class EnemyContext extends BaseContext{
	Target target;
	List<Target> friend;
	Target lastAttackTarget; 
	
	public boolean hasBuff(int id){
		boolean include = false;
		for(EffectBuff ebuff : target.getBuffList())
			if(ebuff.buff.id == id)
				include = true;
		return include;
	}
	
	public boolean isMin(String prop){
		int self = target.getProp(prop),min = 0;
		for(Target t : friend){
			int val = t.getProp(prop);
			if(val < min) min = val;
		}
		return self >= min;
	}
	
	public boolean isMax(String prop){
		int self = target.getProp(prop),max = 0;
		for(Target t : friend){
			int val = t.getProp(prop);
			if(val > max) max = val;
		}
		return self <= max;
	}
	
	public Target min(String prop){
		Target target = this.target;
		for(Target t : friend)
			if(t.getProp(prop) < target.getProp(prop))
				target = t;
		return target;
	}
	
	public Target self(){
		return this.target;
	}
	
	public static Boolean eval(Target self,Target target,List<Target> friend,List<Target> enemies,EnemyAction action){
		try {
			Context ctx = Context.enter();
			ctx.getWrapFactory().setJavaPrimitiveWrap(false);
			if(!GameUtil.isDesktop)
				ctx.setOptimizationLevel(-1);
			
			ScriptableObject scope = ctx.initStandardObjects();
			
			scope.put("self", scope, build(self,friend));
			scope.put("target", scope, build(target,enemies));
			scope.put("turn", scope, self.getTurn());
			
			if(action.target != null && action.target.length() != 0)
				action.calcedTarget = (Target) Context.jsToJava(ctx.evaluateString(scope, action.target, null, 1, null), Target.class);

			Boolean result = null;
			if(action.formula != null && action.formula.length() != 0)
				result = (Boolean) ctx.evaluateString(scope, action.formula, null, 1, null);
			else
				result = true;
			
			Context.exit();
			
			return result;
		} catch (Exception e) {
			Logger.error("无法执行脚本", e);
			e.printStackTrace();
		}
		return false;
	}
	
	public static EnemyContext build(Target target,List<Target> friend){
		EnemyContext ctx = new EnemyContext();
		ctx.target = target;
		ctx.friend = friend;
		ctx.lastAttackTarget = target.lastAttackTarget;
		try {
			for(Field f : ctx.getClass().getFields()){
				if(target.hasProp(f.getName()))
					f.setInt(ctx, target.getProp(f.getName()));
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return ctx;
	}
	
}
