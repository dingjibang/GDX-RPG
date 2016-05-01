package com.rpsg.rpg.object.base.items;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.base.Resistance.ResistanceType;
import com.rpsg.rpg.object.base.items.Effect.EffectBuff;
import com.rpsg.rpg.object.base.items.Effect.EffectBuffType;
import com.rpsg.rpg.object.base.items.Item.ItemDeadable;
import com.rpsg.rpg.object.base.items.Item.ItemForward;
import com.rpsg.rpg.object.base.items.Item.ItemOccasion;
import com.rpsg.rpg.object.base.items.Item.ItemRange;
import com.rpsg.rpg.object.rpg.Hero;
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
	public Hero user2;
	public int animation;
	public String description2;
	
	private static int attackId = 9,defenseId = 10;//XXX 写死的QAQ
	
	public static Class<Spellcard> getClassEx(){
		return Spellcard.class;
	}
	
	public static Spellcard attack(){
		return (Spellcard) RPG.ctrl.item.get(attackId);
	}
	
	public static Spellcard defense(){
		return (Spellcard) RPG.ctrl.item.get(defenseId);
	}
	
	//使用一个符卡=w=
	public BattleResult use(BattleContext ctx){
		//判断使用场景是否正确
		boolean battle = RPG.ctrl.battle.isBattle();
		if(battle && occasion == ItemOccasion.map) return BattleResult.faild();
		if(!battle && occasion == ItemOccasion.battle) return BattleResult.faild();
		//设置使用角色 self ==(spellcard)==> target
		Target self = ctx.self;
		List<Target> targetList = new ArrayList<>();
		
		//如果符卡针对我方敌方所有人
		if(forward == ItemForward.all && range == ItemRange.all){
			targetList.addAll(ctx.friend);
			targetList.addAll(ctx.enemies);
			targetList.add(ctx.enemy);
		}
		//如果符卡针对我方所有人
		if(forward == ItemForward.friend && range == ItemRange.all)
			targetList.addAll(ctx.friend);
		//如果符卡针对敌方所有人
		if(forward == ItemForward.enemy && range == ItemRange.all)
			targetList.addAll(ctx.enemies);
		//如果指向自己
		if(forward == ItemForward.self)
			targetList.add(self);
		//如果符卡针对我方敌方单人
		if(forward == ItemForward.enemy && range == ItemRange.one)
			targetList.add(ctx.enemy);
		if(forward == ItemForward.friend && range == ItemRange.one)
			targetList.add(ctx.enemy);
		
		//判断使用条件是否正确
		for(Target t : targetList)
			if((!t.isDead() && deadable == ItemDeadable.yes) || (t.isDead() && deadable == ItemDeadable.no))
				return BattleResult.faild();
		
		//TODO self
		//判断mp是否足够
		if(self.getProp("mp") < cost)
			return BattleResult.faild();
		
		//添加buff（如果有的话）
		for(EffectBuff ebuff : effect.buff){
			if(ebuff.type == EffectBuffType.add)
				$.each(targetList, t -> t.addBuff(ebuff.buff));
			if(ebuff.type == EffectBuffType.remove)
				$.each(targetList, t -> t.removeBuff(ebuff.buff));
		}
		//计算数值变化
		for(Target t : targetList){
			for (String key : effect.prop.keySet()) {
				Prop prop = effect.prop.get(key);
				
				int damage = damage(self, t, key);
				
				if(damage > 0){
					t.addProp(key, damage + "");
					continue;
				}
				
				
				
//				计算伤害浮动
				damage = prop.rate(damage);
				
				//处理溢出
				damage = damage < 0 ? damage : 0;
				
				String rtype = prop.type;
				if(rtype != null){
					ResistanceType trtype = t.resistance.get(rtype).type;
					if(trtype == ResistanceType.reflect){	//如果抗性为反射，则把伤害给自己
						self.addProp(key, (-damage) + "");
					}
				}
				
				//计算闪避
				float eva = (rtype != null ? t.resistance.get(key).evasion : 0);
				float max = (self.getProp("hit") + t.getProp("evasion") + eva);
				boolean miss = false;
				float rate = (self.getProp("hit") / max) * 100;
				if(max != 0)
					miss = MathUtils.random(0,100) > rate;
					
				
				if(!miss){
					//处理伤害
					t.addProp(key, damage + "");
					
					//扣除消耗
					self.addProp("mp", (-cost) + "");
					
					if(RPG.ctrl.battle.isBattle())
					GameViews.gameview.battleView.status.append("...造成了 " + damage + " 点伤害");
				}else{
					if(RPG.ctrl.battle.isBattle())
					GameViews.gameview.battleView.status.append("...但是没有命中");
				}
				
				//更新上下文
				self.lastAttackTarget = t;
				
				//更新死亡状态
				self.refresh();
				t.refresh();
			}
		};

		return BattleResult.success(this.animation,targetList);
	}
	
	public int damage(Target self,Target target,String propName){
		Prop prop = effect.prop.get(propName); 
		if(prop == null)
			return 0;
		
		Double doubleVal = SpellcardContext.eval(self, target, prop.formula);
		
		if(doubleVal == null)
			throw new GdxRuntimeException("bad properties.");
		
		int val = doubleVal.intValue();
		
		if(val > 0){//如果是增加属性的状态，则跳过所有数值计算直接叠加
			return val;
		}
		
		//获取攻击属性，攻击方式，穿防率
		String rtype = prop.type;
		
		//计算纯粹伤害
		Integer damage = val;
		
		
		//计算抗性
		if(rtype != null){
			ResistanceType trtype = target.resistance.get(rtype).type;
			int result = ResistanceType.invoke(trtype, damage);
			damage = result;
		}
		
		return damage;
		
	}
	
}
