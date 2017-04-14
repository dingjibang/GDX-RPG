package com.rpsg.rpg.object.item;

import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.object.prop.Props;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 道具/符卡的效果<br>
 * <br>
 * 在GDX-RPG中，所有的道具都拥有一个{@link EffectableItem#effect 效果}变量，顾名思义，他用来描述该道具有什么用。<br>
 * 其中，每个效果包含了一个永久属性的map，如map里有{hp:30}，则代表使用该物品+30血，当然实际上会比这个复杂，具体请看代码。<br>
 * 随后，还会有一个数组，里面包含了该物品所提供的buff，该buff仅仅能在战斗时候生效。
 */
public class Effect implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**该道具/符卡提供的永久属性*/
	public Props prop;
	/**该道具/符卡提供的buff，在非战斗时候使用，本属性无用*/
	public List<EffectBuff> buff = new ArrayList<>();
	
	public Effect(JsonValue value) {
		prop = value.has("prop") ? Props.fromJSON(value.get("prop")) : null;
		if(value.has("buff"))
			for(JsonValue ebuff : value.get("buff"))
				buff.add(EffectBuff.fromJSON(ebuff));
	}
	
}
