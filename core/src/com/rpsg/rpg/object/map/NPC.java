package com.rpsg.rpg.object.map;

import java.util.HashMap;
import java.util.Map;



/**
 * 地图上的NPC，基于{@link MapSprite}
 */
public class NPC extends MapSprite{
	public NPC(int zIndex, int speed) {
		super(zIndex, speed);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;
	
	/**该NPC所挂载的脚本*/
	public Map<CollideType, String> scripts = new HashMap<>();
}
