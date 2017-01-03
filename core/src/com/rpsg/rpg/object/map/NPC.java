package com.rpsg.rpg.object.map;

import java.util.Map;



/**
 * 地图上的NPC，基于{@link MapSprite}
 */
public class NPC extends MapSprite{
	private static final long serialVersionUID = 1L;
	
	/**该NPC所挂载的脚本*/
	public Map<CollideType, String> scripts;
}
