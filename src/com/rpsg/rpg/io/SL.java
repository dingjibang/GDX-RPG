package com.rpsg.rpg.io;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.PixmapIO.PNG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.object.base.SLData;
import com.rpsg.rpg.system.controller.HeroController;
import com.rpsg.rpg.system.controller.MapController;
import com.rpsg.rpg.system.controller.MenuController;
import com.rpsg.rpg.view.GameViews;

public class SL {
	public static void save(int fileID) {
		Global global = GameViews.global;
		global.npcs = MapController.getNPCs();
		global.heros = HeroController.allHeros;
		global.currentHeros = HeroController.heros;
		Files.save(global,Setting.GAME_PERSISTENCE+fileID+".dat");
		
		SLData slData=new SLData();
		slData.gameDate=global.tyear+"Äê"+global.tmonth+"ÔÂ"+global.tday;
		slData.id=fileID;
		slData.level=global.currentHeros.get(0).prop.get("level");
		slData.mapName=(String)GameViews.gameview.map.getProperties().get("name");
		slData.saveDate=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
		Files.save(slData,Setting.GAME_PERSISTENCE+fileID+"_sld.dat");
		
//		PixmapIO.writePNG(Gdx.files.internal(Setting.GAME_PERSISTENCE+fileID+".png"), MenuController.bg.getRegion());
	}
	
	public static Global load(int fileID){
		return (Global) Files.load();
	}
}
