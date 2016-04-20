package com.rpsg.rpg.object.base.items;

import java.io.Serializable;

import com.badlogic.gdx.math.MathUtils;

public class Prop implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String type;
	public String formula;
	public String floatRate;
	
	public int rate(int val){
		int min = 0,max = 0;
		
		boolean negative = val < 0;
		
		if(floatRate.endsWith("%")){
			float rate = Float.valueOf(floatRate.split("%")[0]);
			min = (int) (val - (val * (rate / 100)));
			max = (int) (val + (val * (rate / 100)));
		}else{
			min = val - Integer.valueOf(floatRate);
			max = val + Integer.valueOf(floatRate); 
		}
		return MathUtils.random(Math.abs(min),Math.abs(max)) * (negative ? -1 : 1);
	}
	
}
