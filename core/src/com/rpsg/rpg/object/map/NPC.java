package com.rpsg.rpg.object.map;

import java.util.Map;



/**
 * 地图上的NPC，基于{@link MapSprite}
 */
public class NPC extends MapSprite{
	private static final long serialVersionUID = 1L;
	
	/**从{@link com.rpsg.rpg.controller.MapController#load(String, boolean, com.rpsg.gdxQuery.CustomRunnable) 地图}中读取到的脚本，
	 * 或从{@link com.rpsg.rpg.object.game.Archive#mapSprites 存档}中读取的*/
	public Map<CollideType, String> scripts;
}
