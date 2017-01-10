package com.rpsg.rpg.object.item;

import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.object.context.Contextable;

/**
 * GDX-RPG 基础数值
 */
public class Prop {
	/**数值附带的属性*/
	PropType type;
	/**攻击类型（物理还是魔法）*/
	PropStyle style;
	/**攻击算法（js脚本）*/
	String formula;
	/**攻击类型*/
	FormulaType formulaType;
	
	public static Prop fromJSON(JsonValue value) {
		Prop p = new Prop();
		p.type = value.has("type") ? PropType.valueOf(value.getString("type")) : PropType.none;
		p.style = value.has("style") ? PropStyle.valueOf(value.getString("style")) : PropStyle.physic;
		p.formula = value.getString("formula");
		p.formulaType = value.has("formulaType") ? FormulaType.valueOf(value.getString("formulaType")) : FormulaType.negative;
		
		return p; 
	}

	/**
	 * 获取该Prop的值，可能需要传入相关的{@link Contextable 上下文}<br>
	 * <br>
	 * Prop中含有{@link #formula}变量，它是一段js脚本，如：<br>
	 * "30"、"value * 0.05"等。<br>
	 * 在上边我们可以看到，有一个迷之"value"变量，这个变量即为{@link Contextable context}带来的。<br>
	 */
	public int get(Contextable context) {
		int value = ((Number)Game.executeJS(formula, context)).intValue();
		
		if(formulaType == FormulaType.negative && value > 0)
			value = 0;
		if(formulaType == FormulaType.positive && value < 0)
			value = 0;
		
		return value;
	}
	
	public String get() {
		return formula;
	}
	
}
