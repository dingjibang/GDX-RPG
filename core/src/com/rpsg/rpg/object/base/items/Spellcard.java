package com.rpsg.rpg.object.base.items;

import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.base.items.Effect.EffectBuff;
import com.rpsg.rpg.object.base.items.Effect.EffectBuffType;
import com.rpsg.rpg.object.base.items.Item.ItemDeadable;
import com.rpsg.rpg.object.base.items.Item.ItemForward;
import com.rpsg.rpg.object.base.items.Item.ItemOccasion;
import com.rpsg.rpg.object.base.items.Item.ItemRange;
import com.rpsg.rpg.object.base.items.Prop.FormulaType;
import com.rpsg.rpg.object.rpg.Target;
import com.rpsg.rpg.view.GameViews;


public class Spellcard extends BaseItem {
	
	private static final long serialVersionUID = 1L;
	
	public int cost;
	public ItemRange range = ItemRange.one;
	public ItemForward forward = ItemForward.all;
	public ItemOccasion occasion = ItemOccasion.all;
	public ItemDeadable deadable = ItemDeadable.no;
	public int success;
	public int animation;
	public String description2;
	public int delay = 0;
	
	private static int attackId = 9,defenseId = 10;//XXX 写死的QAQ
	
	public static Class<Spellcard> getClassEx(){
		return Spellcard.class;
	}
	
	public static boolean isAttack(Spellcard sc){
		return sc.id == attackId;
	}
	
	public static boolean isDefense(Spellcard sc){
		return sc.id == defenseId;
	}
	
	public static Spellcard attack(){
		return (Spellcard) RPG.ctrl.item.get(attackId);
	}
	
	public static Spellcard defense(){
		return (Spellcard) RPG.ctrl.item.get(defenseId);
	}
	
	public Result use(Context ctx){
		if(effect.getTurn() > 0){
			if(check(ctx).success){
				CallbackBuff buff = new CallbackBuff(ctx.self, this, ()-> $use(ctx,true), effect.wait);
				used(ctx);
				buff.turn = effect.getTurn();
				ctx.self.addCallbackBuff(buff);
				return Result.success();
			}
		}else{
			return $use(ctx,false);
		}
		return Result.faild();
	}
	
	private Result check(Context ctx){
		//判断使用场景是否正确
		boolean battle = RPG.ctrl.battle.isBattle();
		
		if(!(battle && occasion != ItemOccasion.map && ctx.type == Context.Type.battle) && !(!battle && occasion != ItemOccasion.battle && ctx.type == Context.Type.map))
			return Result.faild();
		
		//设置使用角色 self ==(spellcard)==> target
		Target self = ctx.self;
		List<Target> targetList = Target.getTargetList(this, ctx);
		
		
		
		//判断使用条件是否正确
		for(Target t : targetList)
			if((!t.isDead() && deadable == ItemDeadable.yes) || (t.isDead() && deadable == ItemDeadable.no))
				return Result.faild();
		
		//判断mp是否足够
		if(self.getProp("mp") < cost)
			return Result.faild();
		
		return Result.success();
	}
	
	private void used(Context ctx){
		//扣除消耗
		ctx.self.addProp("mp", (-cost) + "");
	}
	
	//使用一个符卡=w=
	private Result $use(Context ctx,boolean used){
		if(!check(ctx).success) return Result.faild();
		
		//设置使用角色 self ==(spellcard)==> target
		Target self = ctx.self;
		List<Target> targetList = Target.getTargetList(this, ctx);
				
		//添加buff（如果有的话）
		for(EffectBuff ebuff : effect.buff){
			if(ebuff.type == EffectBuffType.add)
				$.each(targetList, t -> t.addBuff(ebuff.buff.cpy()));
			if(ebuff.type == EffectBuffType.remove)
				$.each(targetList, t -> t.removeBuff(ebuff.buff.cpy()));
		}
		//计算数值变化
		for(Target t : targetList){
			for (String key : effect.prop.keySet()) {
				Prop prop = effect.prop.get(key);
				
				int damage = damage(effect, self, t, key);
				boolean miss = false;
				
				if(prop.formulaType == FormulaType.negative){
					//计算伤害浮动
					damage = prop.rate(damage);
					
					//计算闪避
					float max = (self.getProp("hit") + t.getProp("evasion"));
					float rate = (self.getProp("hit") / max) * 100;
					if(max != 0)
						miss = MathUtils.random(0,100) > rate;
				}
				
				
				if(!miss){
					//处理伤害
					t.addProp(key, damage + "");
					
					if(!used) used(ctx);
					
					if(RPG.ctrl.battle.isBattle()){
						if(prop.formulaType == FormulaType.negative)
							GameViews.gameview.battleView.status.append("...造成了 " + Math.abs(damage) + " 点伤害");
						else
							GameViews.gameview.battleView.status.append("...回复了" + damage + " 点" + BaseContext.getPropName(key));
					}
				}else{
					if(RPG.ctrl.battle.isBattle())
					GameViews.gameview.battleView.status.append("...但是没有命中");
				}
				
				//更新上下文
				if(self != null) self.lastAttackTarget = t;
				
				//更新死亡状态
				if(self != null) self.refresh();
				t.refresh();
				
				//更新延迟值
				if(RPG.ctrl.battle.isBattle())
					GameViews.gameview.battleView.timer.addDelay(t.getTime(), delay);
			}

		};
		return Result.success(this.animation,targetList);
	}
	
	public static int damage(Effect effect, Target self, Target target, String propName) {
		Prop prop = effect.prop.get(propName); 
		if(prop == null)
			return 0;
		
		/*** ON DAMAGE START ***/
		
		Double doubleVal = SpellcardContext.eval(self, target, prop.formula);
		
		if(doubleVal == null)
			throw new GdxRuntimeException("bad properties.");
		
		int val = doubleVal.intValue();
		
		if(prop.formulaType == FormulaType.positive){//如果是增加属性的状态，则跳过所有数值计算直接叠加
			return val < 0 ? 0 : val;//如果数值溢出，则返回0
		}
		
		/**negative 模式*/
		
		//获取攻击属性，攻击方式，穿防率
		String rtype = prop.type;
		
		//计算纯粹伤害
		Integer damage = val;
		
		
		//计算抗性所带来的 增/减 伤
		if(rtype != null)
			damage = (int) (damage * target.resistance.get(rtype));
		
		return -damage > 0 ? 0 : -damage;//如果数值溢出，则返回0
	}
	
}
