package com.rpsg.rpg.object.base.items;

import java.io.Serializable;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.JsonValue;

public class Prop implements Serializable {
	
	public static enum FormulaType{positive, negative}
	public static enum Style{physic, magic}
	
	private static final long serialVersionUID = 1L;

	public String type;
	public String formula;
	public String floatRate;
	public Style style;
	public FormulaType formulaType = FormulaType.positive;
	

	public int rate(int val) {
		int min = 0, max = 0;

		boolean negative = val < 0;

		if (floatRate.endsWith("%")) {
			float rate = Float.valueOf(floatRate.split("%")[0]);
			min = (int) (val - (val * (rate / 100)));
			max = (int) (val + (val * (rate / 100)));
		} else {
			min = val - Integer.valueOf(floatRate);
			max = val + Integer.valueOf(floatRate);
		}
		return MathUtils.random(Math.abs(min), Math.abs(max)) * (negative ? -1 : 1);
	}

	@Override
	public String toString() {
		return "\"" + formula + "\" (±" + floatRate + ")";
	}
	
	public static Prop fromJSON(JsonValue propValue){
		Prop prop = new Prop();
		prop.formula = propValue.getString("formula");
		prop.type = propValue.has("type") ? propValue.getString("type") : null;
		prop.floatRate = propValue.has("floatRate") ? propValue.getString("floatRate") : "0";
		prop.formulaType = propValue.has("formulaType") ? FormulaType.valueOf(propValue.getString("formulaType")) : FormulaType.positive;
		prop.style = propValue.has("style") ? Style.valueOf(propValue.getString("style")) : Style.physic;
		return prop;
	}
}
