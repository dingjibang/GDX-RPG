package com.rpsg.rpg.object.base.items;

public abstract class BaseContext {
	public int level, exp, maxexp, hp, maxhp, mp, maxmp, attack, magicAttack, defense, magicDefense, speed, evasion, hit, maxsc, dead;
	
	public static String getPropName(String name){
		switch(name){
			case "level" : return "等级";
			case "exp" : return "经验";
			case "maxexp" : return "经验上限";
			case "hp" : return "生命";
			case "maxhp" : return "最大生命";
			case "mp" : return "妖力";
			case "maxmp" : return "最大妖力";
			case "attack" : return "攻击";
			case "magicAttack" : return "魔法攻击";
			case "defense" : return "防御";
			case "magicDefense" : return "魔法防御";
			case "speed" : return "速度";
			case "evasion" : return "闪避";
			case "hit" : return "命中";
			case "maxsc" : return "最大符卡持有数量";
		}
		return name;
	}
}
