package com.rpsg.rpg.object.map;

import java.util.HashMap;
import java.util.Map;

import com.rpsg.rpg.object.game.Scriptable;



/**
 * 地图上的NPC，基于{@link MapSprite}
 */
public class NPC extends MapSprite{
	public NPC(int x,int y,int zIndex) {
		super(x,y,zIndex,4);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;
	
	/**该NPC所挂载的脚本*/
	public Map<CollideType, Scriptable> scripts = new HashMap<>();
}
