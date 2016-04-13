package com.rpsg.rpg.object.rpg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.object.base.Resistance;
import com.rpsg.rpg.object.base.Resistance.ResistanceType;
import com.rpsg.rpg.object.base.items.Buff;

/**
 * GDX-RPG Hero/Enemy 数据模块
 * @author dingjibang
 *
 */
public class Target implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final int TRUE = 1;
	public static final int FALSE = 0;
	
	public List<Buff> buffList = new ArrayList<>();
	
	public Target clear(){
		buffList.clear();
		return this;
	}
	
	public Target addBuff(Buff buff){
		if(buff == null) return this;
		buffList.add(buff);
		return this;
	}
	
	public Target removeBuff(Buff buff){
		if(buff == null)
			return removeBuff();
		
		Buff target = null;
		for(Buff b : buffList)
			target = b.id == buff.id ? buff : target;
		if(target != null)
			buffList.remove(target);
		return this;
	}
	
	public Target removeBuff(){
		buffList.clear();
		return this;
	}
	
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
	
	//计算数值（百分比或绝对值）
	public static Integer calcProp(int value,String prop){
		try {
			if(prop.endsWith("%")){
				return Integer.valueOf(prop.substring(0, prop.length() - 1)) / 100;
			}else{
				return Integer.valueOf(prop);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static List<Target> parse(List<?> list){
		List<Target> result = new ArrayList<>();
		$.each(list, e -> result.add(parse(e)));
		return result;
	}
	
	public static Target parse(Object o){
		if(o instanceof Hero)
			return (((Hero)o).target);
		if(o instanceof Enemy)
			return (((Enemy)o).target);
		throw new GdxRuntimeException("target must be hero or enemy.");
	}
	
}
