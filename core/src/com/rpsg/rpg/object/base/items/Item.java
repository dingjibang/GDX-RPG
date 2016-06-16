package com.rpsg.rpg.object.base.items;

import java.util.List;

import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.base.Resistance.ResistanceType;
import com.rpsg.rpg.object.base.items.Effect.EffectBuff;
import com.rpsg.rpg.object.base.items.Effect.EffectBuffType;
import com.rpsg.rpg.object.base.items.Prop.FormulaType;
import com.rpsg.rpg.object.rpg.Target;
import com.rpsg.rpg.view.GameViews;

public class Item extends BaseItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**道具指向*/
	public ItemForward forward = ItemForward.friend;
	
	/**道具使用范围*/
	public ItemRange range = ItemRange.one;
	
	/**道具使用场景**/
	public ItemOccasion occasion = ItemOccasion.all;
	
	/**道具是否可以给满身疮痍的人使用**/
	public ItemDeadable deadable = ItemDeadable.no;
	
	/**道具是否为一次性的*/
	public boolean removeable = true;
	
	/**道具使用动画**/
	public int animation = 0;
	
	/**
	 * 道具指向
	 * @author dingjibang
	 */
	public static enum ItemForward{
		friend,//我方
		enemy,//敌人
		all,//全部
		link,//对与自己连携的对象使用的。
		self//自己
	}
	
	/**
	 * 道具使用范围
	 * @author dingjibang
	 */
	public static enum ItemRange{
		one,//一人
		all//全部
	}
	
	/**
	 * 道具使用场景
	 * @author dingjibang
	 */
	public static enum ItemOccasion{
		battle,//仅战斗时
		map,//仅非战斗时
		all//所有场景
	}
	
	/**
	 * 道具是否可以给满身疮痍的人使用。
	 * @author dingjibang
	 *
	 */
	public static enum ItemDeadable{
		yes,//仅能给满身疮痍的人使用。
		no,//仅能给活着的人使用。
		all//可以给所有人使用。
	}
	
	private Result check(Context ctx){
		//判断使用场景是否正确
		if((ctx.type == Context.Type.map || occasion == ItemOccasion.map) && RPG.ctrl.battle.isBattle())
			return Result.faild();
		if((ctx.type == Context.Type.battle || occasion == ItemOccasion.battle) && !RPG.ctrl.battle.isBattle())
			return Result.faild();
		
		//设置使用角色 self ==(item)==> target
		List<Target> targetList = Target.getTargetList(this, ctx);
		
		//判断使用条件是否正确
		for(Target t : targetList)
			if((!t.isDead() && deadable == ItemDeadable.yes) || (t.isDead() && deadable == ItemDeadable.no))
				return Result.faild();
		
		if(count <= 0)
			return Result.faild();
		
		return Result.success();
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
	
	private void used(Context ctx){
		//扣除消耗
		if(this.count > 1)
			this.count --;
		else
			RPG.ctrl.item.remove(this);
	}
	
	/**
	 * 使用一个物品
	 */
	private Result $use(Context ctx,boolean used) {
		if(!check(ctx).success) return Result.faild();
		
		//设置使用角色 self ==(item)==> target
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
				
				int damage = Spellcard.damage(effect, self, t, key);
				boolean miss = false;
				
				if(prop.formulaType == FormulaType.negative){
					String rtype = prop.type;
					if(rtype != null){
						ResistanceType trtype = t.resistance.get(rtype).type;
						if(trtype == ResistanceType.reflect){	//如果抗性为反射，则把伤害给自己
							self.addProp(key, damage);
						}
					}
				}
				
				
				if(!miss){
					if(!used) used(ctx);
					//处理伤害
					t.addProp(key, damage + "");
					
					if(RPG.ctrl.battle.isBattle())
						if(prop.formulaType == FormulaType.negative)
							GameViews.gameview.battleView.status.append("...造成了 " + Math.abs(damage) + " 点伤害");
						else
							GameViews.gameview.battleView.status.append("...回复了" + damage + " 点" + BaseContext.getPropName(key));
				}else{
					if(RPG.ctrl.battle.isBattle())
						GameViews.gameview.battleView.status.append("...但是没有命中");
				}
				
				//更新上下文
				if(self != null)
					self.lastAttackTarget = t;
				
				//更新死亡状态
				if(self != null)
					self.refresh();
				t.refresh();
			}
		};

		return Result.success(this.animation,targetList);
	}
}
