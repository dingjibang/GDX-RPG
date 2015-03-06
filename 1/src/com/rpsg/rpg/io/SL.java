package com.rpsg.rpg.io;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.PixmapIO.PNG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.object.base.SLData;
import com.rpsg.rpg.system.controller.HeroController;
import com.rpsg.rpg.system.controller.MapController;
import com.rpsg.rpg.system.controller.MenuController;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.view.GameViews;

public class SL {
	public static boolean save(int fileID) {
		try{
			Global global = GameViews.global;
			global.npcs = MapController.getNPCs();
			global.heros = HeroController.allHeros;
			global.currentHeros = HeroController.heros;
			Files.save(global,Setting.GAME_PERSISTENCE+fileID+".dat");
			
			SLData slData=new SLData();
			slData.gameDate=global.tyear+"年"+global.tmonth+"月"+global.tday+"日";
			slData.id=fileID;
			slData.level=global.currentHeros.get(0).prop.get("level");
			slData.mapName=(String)GameViews.gameview.map.getProperties().get("name");
			slData.saveDate=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
			slData.heroName=HeroController.getHeadHero().name;
			Files.save(slData,Setting.GAME_PERSISTENCE+fileID+"_sld.dat");
			
			PixmapIO.writePNG(new FileHandle(Gdx.files.getLocalStoragePath()+Setting.GAME_PERSISTENCE+fileID+".png"),MenuController.pbg);
			return true;
		}catch(Exception e){
			Logger.error("无法保存文件。", e);
			return false;
		}
	}
	
	public static Global load(int fileID){
		return (Global) Files.load();
	}
	
	public static boolean delete(int fileID){
		return 	new FileHandle(Gdx.files.getLocalStoragePath()+Setting.GAME_PERSISTENCE+fileID+".png").delete() &
				new FileHandle(Gdx.files.getLocalStoragePath()+Setting.GAME_PERSISTENCE+fileID+".dat").delete() &
				new FileHandle(Gdx.files.getLocalStoragePath()+Setting.GAME_PERSISTENCE+fileID+"_sld.dat").delete();
	}
}
