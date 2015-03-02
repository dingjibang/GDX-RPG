package com.rpsg.rpg.object.base;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Files;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.Image;

public class SLData {
	
	public int id;
	public transient Image thumbnail;
	public int level;
	public String mapName;
	public String saveDate;
	public String gameDate;
	public static void generate(int id,int i,Stage stage) {
		Image im=Res.get(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"savebl.png").position(i>1?44+(i-2)*483:44+i*483, i>1?270:114);
		im.setUserObject(new Object());
		stage.addActor(im);
		SLData slData = null;
		String fileName = Setting.GAME_PERSISTENCE+(id+"_sld.dat");
		if(Files.empty(fileName)){
			slData = new SLData();
			slData.thumbnail=Res.get(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"ea.png");
		}else{
			slData = (SLData) Files.load(fileName);
			slData.thumbnail=Res.get(Setting.GAME_PERSISTENCE+id+".png");
		}
		slData.thumbnail.setUserObject(new Object());
		stage.addActor(slData.thumbnail.position(i>1?54+(i-2)*493:54+i*493, i>1?270:114));
	}
}
