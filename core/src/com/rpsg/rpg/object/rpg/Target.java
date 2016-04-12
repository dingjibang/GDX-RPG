package com.rpsg.rpg.object.rpg;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.rpsg.rpg.object.base.Resistance;
import com.rpsg.rpg.object.base.Resistance.ResistanceType;

/**
 * GDX-RPG Hero/Enemy 数据模块
 * @author dingjibang
 *
 */
public class Target implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final int TRUE = 1;
	public static final int FALSE = 0;
	
	
	
	public Map<String, Integer> prop = new HashMap<String, Integer>();
	{
		//等级
		prop.put("level", 0);
		//经验
		prop.put("exp", 0);
		//最大经验值至下一次升级
		prop.put("maxexp", 0);
		//生命值
		prop.put("hp", 0);
		//最大生命值
		prop.put("maxhp", 0);
		//魔法量
		prop.put("mp", 0);
		//魔法量
		prop.put("maxmp", 0);
		//攻击
		prop.put("attack", 0);
		//魔法攻击
		prop.put("magicAttack", 0);
		//防御
		prop.put("defense", 0);
		//魔法防御
		prop.put("magicDefense", 0);
		//速度
		prop.put("speed", 0);
		//闪避
		prop.put("evasion", 0);
		//准确率
		prop.put("hit", 0);
		//最大可携带副卡量
		prop.put("maxsc", 0);
		//是否是死亡状态的
		prop.put("dead", FALSE);
	};
	
	/**抗性*/
	public LinkedHashMap<String, Resistance> resistance = new LinkedHashMap<String, Resistance>();
	{
		resistance.put("sun", new Resistance(ResistanceType.normal,0));
		resistance.put("moon", new Resistance(ResistanceType.normal,0));
		resistance.put("star", new Resistance(ResistanceType.normal,0));
		resistance.put("metal", new Resistance(ResistanceType.normal,0));
		resistance.put("water", new Resistance(ResistanceType.normal,0));
		resistance.put("earth", new Resistance(ResistanceType.normal,0));
		resistance.put("fire", new Resistance(ResistanceType.normal,0));
		resistance.put("wood", new Resistance(ResistanceType.normal,0));
		resistance.put("physical", new Resistance(ResistanceType.normal,0));
	}

	public boolean isDead(){
		return getProp("dead")==Target.TRUE;
	}


	public int getProp(String string) {
		return prop.get(string);
	}
	
	
}
