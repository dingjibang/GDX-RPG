package com.rpsg.rpg.game.sc;

import com.rpsg.rpg.object.base.SpellCard;
import com.rpsg.rpg.object.rpgobj.Hero;

public class patientBack extends SpellCard{
	private static final long serialVersionUID = 1L;

	public patientBack(){
		name="回春术";
		illustration="使用后，将对非满身疮痍状态的队友恢复50点+10%魔攻的生命值，可以在非战斗状态下使用。";
		story="return of spring（滚";
		magicConsume=30;
		addParam.put("hp", 50);
		added=10;
		type=TYPE_USEINMAP;
	}
	
	public boolean use(Hero user,Hero to){
		System.out.println("okay");
		return false;
	}
}
