package com.rpsg.rpg.object.base;

/**
 * GDX-RPG 抗性数据
 * @author dingjibang
 *
 */
public class Resistance{
	
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
		absorb		//吸收
	}
	
	
}