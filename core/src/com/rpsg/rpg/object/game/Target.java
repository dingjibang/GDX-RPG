package com.rpsg.rpg.object.game;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.object.context.EquipmentContext;
import com.rpsg.rpg.object.item.Equipment;
import com.rpsg.rpg.object.prop.Prop;
import com.rpsg.rpg.object.prop.PropKey;

/**
 * GDX-RPG 数值信息类<br>
 * <br>
 * 所有的enemy和hero均拥有Target类，Target存储了该角色的所有数值信息（能力、装备等）。<br>
 * 
 * TODO 为了保证不被数值污染，最好把战斗相关的数值上下文不再这里加入。到时候做一个BattleTarget怎么样=。= 
 */
public class Target implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	/**基础数值，存储了永久的基础属性（攻击，血量等）*/
	Map<PropKey, Integer> props = new HashMap<>();{
		props.put(PropKey.dead, PropKey.FALSE);
		props.put(PropKey.attackRate, 0);
	}
	
	/**该target身上的所有装备*/
	private Map<Equipment.Parts, Equipment> equipments = new HashMap<>();
	
	/**获取原始属性，请不要用此来获取最终数值，最终数值请使用{@link #get(PropKey)}来获取*/	
	public Map<PropKey, Integer> getProps() { return props; }
	/**获取装备*/ 	
	public Map<Equipment.Parts, Equipment> getEquipments() { return equipments; }
	
	
	public Target(JsonValue props) {
		for(JsonValue value : props)
			this.props.put(PropKey.valueOf(value.name), value.asInt());
	}
	
	/**
	 * 获取该target的某项数值，要注意的是，该数值是经过计算的（基础数值+装备提供的数值）。<br>
	 * 因为该方法需要{@link Prop#get() 多次执行js}，所以调用该方法可能会消耗性能（调用1次大概要2ms，不可以持续调用，必须做UI数值缓存）
	 * */
	public int get(PropKey propName){
		int value = props.get(propName) == null ? 0 : props.get(propName);
		int originValue = value;
		
		for(Equipment e : equipments.values()){
			if(e == null || e.effect == null) continue;
			Prop prop = e.effect.prop.get(propName);
			if(prop != null)
				value += prop.get(new EquipmentContext(originValue));
		}
		
		return value;
	}
	
	/**
	 * 穿上一件装备<br>
	 * 如果当前部位已经有一件装备了，则先脱下来。
	 * 本来想用英文的put on之类的作为方法名，想想还是算了，用程序式的英语比较迷之带感2333
	 * */
	
	public void setEquipment(Equipment.Parts parts, Equipment equipment){
		//已经有装备的话就先脱下来
		if(equipments.get(parts) != null)
			removeEquipment(parts, equipment);
		
		equipments.put(parts, equipment);
		
		Game.item.remove(equipment);
	}
	
	/***
	 * 脱下一件装备，并将装备放入玩家背包中
	 */
	public void removeEquipment(Equipment.Parts parts, Equipment equipment){
		Equipment equip = equipments.get(parts);
		if(equip == null) return;
		
		equipments.put(parts, null);
		
		Game.item.put(equip);
	}
	
	
	
}
