package com.rpsg.rpg.io;


import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.system.control.HeroControler;
import com.rpsg.rpg.system.control.MapControler;
import com.rpsg.rpg.utils.display.ColorUtil;
import com.rpsg.rpg.view.GameViews;

public class SaveLoad {
	public static void save(int fileID){
		Global global=GameViews.global;
		global.npcs=MapControler.getNPCs();
		global.heros=HeroControler.allHeros;
		global.currentHeros=HeroControler.heros;
		global.mapColor=ColorUtil.currentColor;
		FileIO.save(global);
	}
	
	public static Global load(int fileID){
		return (Global) FileIO.load();
	}
}