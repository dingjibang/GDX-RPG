package com.rpsg.rpg.object.context;

import com.rpsg.rpg.object.game.Target;
import com.rpsg.rpg.object.item.Prop;

/**
 * 装备运算的上下文
 * @see Prop#get(Contextable)
 */
public class EquipmentContext implements Contextable{
	
	/**
	 * 原始值，原始值代表每个{@link Target}的原始{@link Target#getProps() 属性}，他的作用是给那些需要做百分比运算（比如穿上这件装备hp+5%）来使用的<br>
	 * 比如"value * 0.05";
	 */
	public int value;
	
	public EquipmentContext(int value) {
		this.value = value;
	}
	
}
