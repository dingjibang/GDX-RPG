package com.rpsg.rpg.object.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.object.prop.PropKey;

/**
 * GDX-RPG 可成长的{@link Target}<br>
 * 额外的拥有了经验和等级的属性，并且当 当前经验 > 到下一级的经验时，将会升级并且更新玩家的属性
 */
public class GrowableTarget extends Target{
	
	public GrowableTarget(JsonValue props, JsonValue grow) {
		super(props);
		for(JsonValue value : grow)
			this.grow.put(PropKey.valueOf(value.name), value.asInt());
	}

	private static final long serialVersionUID = 1L;

	/**每次升级增加的玩家属性*/
	public Map<PropKey, Integer> grow = new HashMap<>();
	
	
	/**给该target增加经验，如果因此升级的话，返回的list包含了升级后的属性*/
	public List<Map<PropKey, Integer>> addExp(int exp) {
		int currentExp = props.get(PropKey.exp), maxExp = props.get(PropKey.nextExp);
		
		List<Map<PropKey, Integer>> list = new ArrayList<>();
		list.add(cloneProps());
		
		if(currentExp + exp < maxExp){
			props.put(PropKey.exp, currentExp + exp);
		}else{
			//升级
			int leftExp = currentExp + exp - maxExp;
			props.put(PropKey.level, props.get(PropKey.level) + 1);
			props.put(PropKey.exp, props.get(PropKey.nextExp));
			props.put(PropKey.nextExp, props.get(PropKey.nextExp) + 1000);//TODO
			
			//升级各种属性
			for(PropKey key : grow.keySet())
				props.put(key, props.get(key) + grow.get(key));
			
			list.addAll(addExp(leftExp));
		}
		
		return list;
	}
	
	private Map<PropKey, Integer> cloneProps() {
		Map<PropKey, Integer> map = new HashMap<>();
		for(PropKey key : props.keySet())
			map.put(key, props.get(props).intValue());
		
		return map;
	}
	
}
