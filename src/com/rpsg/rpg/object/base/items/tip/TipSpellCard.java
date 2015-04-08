package com.rpsg.rpg.object.base.items.tip;

import com.rpsg.rpg.object.base.items.SpellCard;

public class TipSpellCard extends SpellCard{
	private static final long serialVersionUID = 1L;

	public TipSpellCard(){
		name="提示";
		illustration="您可以在左侧选择英雄，下方即为当前英雄所持有的符卡，单击符卡查看符卡信息，双击符卡进行功能操作。";
		story="";
		magicConsume=0;
	}
}
