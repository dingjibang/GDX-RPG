package com.rpsg.rpg.controller;

import com.rpsg.rpg.core.File;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.Path;
import com.rpsg.rpg.object.game.Archive;

/**
 * 游戏档案管理器
 */
public class ArchiveController {
	
	Archive ach;
	
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
	}
	
	/**
	 * 存档
	 */
	public void save(int id) {
		File.save(ach, Path.SAVE + id + ".sav");
	}
}
