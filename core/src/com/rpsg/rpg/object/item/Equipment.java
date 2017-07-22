package com.rpsg.rpg.object.item;

import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.object.game.Target;
import com.rpsg.rpg.object.hero.Hero;

/**
 * GDX-RPG 装备<br>
 * 装备是穿着在{@link Target}身上的。
 */
public class Equipment extends EffectableItem{
	
	private static final long serialVersionUID = 1L;

	/**装备部位*/
	public static enum Parts {
		shoes, clothes, weapon, ornament1, ornament2
	}
	
	/**装备仅限于某人穿（{@link Hero#id}），空的则可以让所有角色穿*/
	public int[] onlyFor;
	/**装备故事描述*/
	public String description2;
	/**装备部位类型*/
	public Parts equipType;
	/**装备在战斗中使用时，所播放的动画的ID*/
	public int animation;
	
	public Equipment(Integer id, JsonValue value) {
		super(id, value);

		//装备是不可以叠加的
		packable = false;
		
		if(value.has("onlyFor"))
			onlyFor = value.get("onlyFor").asIntArray();
				
		description2 = value.has("description2") ? value.getString("description2") : "";
		equipType = Parts.valueOf(value.getString("equipType"));
		animation = value.has("animation") ? value.getInt("animation") : 0;
	}
	
}
