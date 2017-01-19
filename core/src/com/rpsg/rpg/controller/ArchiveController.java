package com.rpsg.rpg.controller;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.rpsg.rpg.core.File;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.Path;
import com.rpsg.rpg.core.Views;
import com.rpsg.rpg.object.game.Archive;
import com.rpsg.rpg.view.GameView;

/**
 * 游戏档案管理器
 */
public class ArchiveController {
	
	private Archive ach;
	
	/**获取当前游戏正在使用的档案，如果没有则返回一个新的档案*/
	public Archive get() {
		if(ach == null){
			ach = new Archive();
			//从脚本中读取默认档案信息
			Game.executeJS(File.readString(Path.SCRIPT_SYSTEM + "archive.js"), ach);
		}
		return ach;
	}
	
	/**
	 * 读取档案
	 */
	public void load(int id) {
		Object obj = File.load(Path.SAVE + id + ".sav");
		//如果没有这个档案就不读了
		if(obj == null)
			return;
		
		ach = (Archive)obj;
		
		//没有游戏视图，创建一个（从游戏外菜单的读档界面进来的），否则让地图控制器重新读地图（从游戏内菜单的进来的）
		if(Game.view == null)
			Views.addView(GameView.class);
		else
			Game.view.map.load(ach.mapName);
	}
	
	/**
	 * 存档
	 */
	public void save(int id) {
		if(Game.view == null)
			throw new GdxRuntimeException("must in the game when save archive");
		
		//储存当前地图中的所有精灵
		ach.setMapSprites(Game.view.map.mapSprites);
		
		File.save(ach, Path.SAVE + id + ".sav");
	}

	/**
	 * 是否有存档
	 */
	public boolean has() {
		return ach != null;
	}
	
	/**
	 * 清空存档
	 */
	public void clear() {
		ach = null;
	}
	
}
