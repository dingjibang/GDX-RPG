package com.rpsg.rpg.object.base;

import com.badlogic.gdx.utils.JsonValue;

/**
 * GDX-RPG 自定义战斗拦截器
 * @author dingjibang
 *
 */
public class Filter {
	public String 
		onTurnBegin,
		onDamage,
		onAttack,
		onRemove,
		onSelect
	;
	
	public static Filter fromJSON(JsonValue value) {
		Filter f = new Filter();
		if(value == null || value.isNull())
			return f;
		
		f.onTurnBegin = value.has("onTurnBegin") ? value.getString("onTurnBegin") : null;
		f.onDamage = value.has("onDamage") ? value.getString("onDamage") : null;
		f.onAttack = value.has("onAttack") ? value.getString("onAttack") : null;
		f.onRemove = value.has("onRemove") ? value.getString("onRemove") : null;
		f.onSelect = value.has("onSelect") ? value.getString("onSelect") : null;
		
		return f;
	}
}
