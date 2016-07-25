package com.rpsg.rpg.object.base.items;

import com.rpsg.rpg.core.RPG;

public class Note extends BaseItem{

	private static final long serialVersionUID = 1L;
	
	public long index;
	public Integer spellcard;

	@Override
	public Result use(Context ctx) {
		return Result.faild();
	}
	
	public Spellcard getSpellcard(){
		if(!hasSpellcard()) return null;
		return RPG.ctrl.item.get(spellcard, Spellcard.class);
	}
	
	public boolean hasSpellcard(){
		return spellcard != null; 
	}

}
