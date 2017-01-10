package com.rpsg.rpg.object.item;

import com.badlogic.gdx.utils.JsonValue;

/**
 * GDX-RPG “有效果”的道具<br>
 * 他继承了{@link BaseItem}，而{@link Equipment}和{@link UseableItem}继承了它。<br>
 * 他拥有一个{@link Effect}变量，用来存储了这个道具的“效果”。
 */
public class EffectableItem extends BaseItem{
	
	private static final long serialVersionUID = 1L;
	
	public Effect effect;

	public EffectableItem(Integer id, JsonValue value) {
		super(id, value);
		effect = value.has("effect") ? new Effect(value.get("effect")) : null;
	}

}
