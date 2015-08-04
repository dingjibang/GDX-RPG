package com.rpsg.rpg.core;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.rpsg.rpg.system.controller.Distant;
import com.rpsg.rpg.system.controller.MapLoader;

public class Maps {
	public Distant distant;
	public TiledMap map;
	public MapLoader loader = new MapLoader();
	
	public MapProperties getProp(){
		return map==null?null:map.getProperties();
	}
	
	public String getName(){
		String name=(String) map.getProperties().get("name");
		if(name==null)
			return "未知地图";
		return name;
	}
}
