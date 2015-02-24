package com.rpsg.rpg.io;


import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.system.controller.HeroController;
import com.rpsg.rpg.system.controller.MapController;
import com.rpsg.rpg.view.GameViews;

public class SaveLoad {
	public static void save(int fileID){
		Global global=GameViews.global;
		global.npcs=MapController.getNPCs();
		global.heros=HeroController.allHeros;
		global.currentHeros=HeroController.heros;
		FileIO.save(global);
	}
	
	public static Global load(int fileID){
		return (Global) FileIO.load();
	}
}
