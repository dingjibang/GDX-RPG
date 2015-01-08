package com.rpsg.rpg.game.sc;

import com.rpsg.rpg.object.base.SpellCard;

public class MagicGun extends SpellCard{
	public MagicGun(){
		name="魔炮";
		illustration="使用后，将对敌人将造成约320点固定的魔法伤害。\n装备专属装备[阳伞]后，伤害将提升为320+20%魔攻的魔\n法伤害。";
		story="风见幽香的初始技能，在LV10时可以获得。\n\n在绝对的力量优势面前,一切花拳绣腿都是浮云\n—— 风见幽香（轰）";
		magicConsume=55;
		type=TYPE_USEINMAP;
	}
}
