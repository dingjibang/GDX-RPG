package com.rpsg.rpg.object.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rpsg.rpg.object.map.MapSprite;

/**
 * GDX-RPG 游戏存档<br>
 * 所有初始化的内容，都是从js脚本里读取的，js脚本的位置在 assets/script/system/archive.js
 */
public class Archive implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**地图*/
	public String mapName = "";
	
	/**地图里的精灵*/
	public List<MapSprite> mapSprites = new ArrayList<>();
	
	/**自定义状态*/
	Map<String, Boolean> flags = new HashMap<>();
	
	/**添加一个状态*/
	public void set(String id, boolean flag){
		flags.put(id, flag);
	}
	
	/**maybe null*/
	public Boolean get(String id){
		return flags.get(id);
	}
	
}
