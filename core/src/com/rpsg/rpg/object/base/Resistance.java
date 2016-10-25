package com.rpsg.rpg.object.base;

import java.io.Serializable;

/**
 * GDX-RPG 抗性数据
 * @author dingjibang
 *
 */
public class Resistance implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**抗性*/
	public ResistanceType type = ResistanceType.normal;
	/**闪避率*/
	public int evasion = 0; 
	
	
	public Resistance(ResistanceType type, int evasion) {
		this.type = type;
		this.evasion = evasion;
	}


	public static enum ResistanceType {
		weak,		//虚弱
		normal,		//正常
		tolerance,	//忍耐
		invalid,	//无效
		reflect,	//反射
		absorb;		//吸收
		
		public static int invoke(ResistanceType type, int val){
			switch (type){
				case weak: return (int)((float)val * 1.3f);
				case normal: return val;
				case tolerance: return (int)((float)val * .7f);
				case invalid: return 0;
				case reflect: return (int)((float)val * .3f);
				case absorb: return -(int)((float)val * .3f);
			}
			return 0;
		}
	}
	
	
	
	
}