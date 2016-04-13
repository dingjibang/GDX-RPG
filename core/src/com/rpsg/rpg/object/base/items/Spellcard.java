package com.rpsg.rpg.object.base.items;

import java.util.ArrayList;
import java.util.List;

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
	private static Spellcard attack,defense;
	
	public static Class<Spellcard> getClassEx(){
		return Spellcard.class;
	}
	
	public static Spellcard attack(){
		if(attack == null) attack = (Spellcard) RPG.ctrl.item.get(attackId);
		return attack;
	}
	
	public static Spellcard defense(){
		if(defense == null) defense = (Spellcard) RPG.ctrl.item.get(defenseId);
		return defense;
	}
	
	//使用一个符卡=w=
	public boolean use(BattleContext ctx){
		//判断使用场景是否正确
		boolean battle = RPG.ctrl.battle.isBattle();
		if(battle && occasion == ItemOccasion.map) return false;
		if(!battle && occasion == ItemOccasion.battle) return false;
		
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
		//如果符卡针对我方敌方单人
		if(range == ItemRange.one)
			targetList.add(ctx.enemy);
		
		//判断使用条件是否正确
		for(Target t : targetList)
			if((!t.isDead() && deadable == ItemDeadable.yes) || (t.isDead() && deadable == ItemDeadable.no))
				return false;
		
		//判断mp是否足够
		if(self.getProp("mp") < cost)
			return false;
		
		//添加buff（如果有的话）
		for(EffectBuff ebuff : effect.buff){
			if(ebuff.type == EffectBuffType.add)
				$.each(targetList, t -> t.addBuff(ebuff.buff));
			if(ebuff.type == EffectBuffType.remove)
				$.each(targetList, t -> t.removeBuff(ebuff.buff));
		}
		
		//计算数值变化
		$.each(targetList, t -> {
			for (String key : effect.prop.keySet()) {
				String[] data = effect.prop.get(key).split("#");
				if (data.length != 4) throw new GdxRuntimeException("wrong properties with" + key + ",spellcard:" + this.name);
				
				String val = data[3];
				boolean add = Integer.valueOf(val.endsWith("%") ? val.split("%")[0] : val) > 0;
				if(add){//如果是增加属性的状态，则跳过所有数值计算直接叠加
					t.addProp(key, val);
					continue;
				}
				
				//获取攻击属性，攻击方式，穿防率
				String rtype = data[0].length() == 0 ? null : data[0];
				boolean isPhysical = data[1].equalsIgnoreCase("p");
				int peneRate = Integer.valueOf(data[2]);
				
				//计算纯粹伤害
				Integer damage = Target.calcProp(self.getProp(key), val);
				
				//根据攻击类型，获取敌人的物理或魔法防御
				int defense = t.getProp(isPhysical ? "defense" : "magicDefense");
				
				//计算穿防
				defense *= (100 - peneRate) / 100;
				
				//计算减少防御后的伤害
				damage += defense;
				
				//计算抗性
				if(rtype != null){
					ResistanceType trtype = t.resistance.get(rtype).type;
					int result = ResistanceType.invoke(trtype, damage);
					
					if(trtype != ResistanceType.reflect){
						damage = result;
					}else{
						//如果对方反弹伤害，则把伤害加给自己
						self.addProp(key, (-result) + "");
					}
				}
				
				//处理伤害
				t.addProp(key, damage + "");
				
				//扣除消耗
				self.addProp("mp", (-cost) + "");
				
				//更新死亡状态
				self.refresh();
				t.refresh();
			}
		});

		return true;
	}
	
}
