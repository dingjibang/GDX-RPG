package com.rpsg.rpg.object.base;

import java.io.Serializable;



import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Files;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.view.hover.SaveView;

public class SLData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public int id;
	public transient Image thumbnail;
	public int level;
	public String mapName;
	public String saveDate;
	public String gameDate;
	public static void generate(int id,int i,Stage stage,SaveView sv) {
		Image im=Res.get(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"savebl.png").position(i>1?44+(i-2)*483:44+i*483, i>1?270:114);
		im.setUserObject(new exMask());
		stage.addActor(im);
		SLData slData = null;
		im.onClick(()->{
			stage.getActors().forEach((Actor actor)->{
				if(actor.getUserObject()!=null && actor.getUserObject().getClass().equals(exMask.class))
					((Image)actor).setColor(1,1,1,1);
			});
			im.color(0,0,0,0.5f);
			sv.currentSelect=id;
		});
		String fileName = Setting.GAME_PERSISTENCE+(id+"_sld.dat");
		if(Files.empty(fileName)){
			slData = new SLData();
			slData.thumbnail=Res.get(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"ea.png");
			stage.addActor(new Label("¿ÕÎ»ÖÃ",26).userObj(new Object()).setWidth(1000).setPos(i>1?324+(i-2)*483:324+i*483, i>1?347:191));
		}else{
			slData = (SLData) Files.load(fileName);
			slData.thumbnail=Res.get(Setting.GAME_PERSISTENCE+id+".png");
		}
		slData.thumbnail.setUserObject(new Object());
		stage.addActor(slData.thumbnail.position(i>1?54+(i-2)*483:54+i*483, i>1?277:121));
	}
	
	static class exMask{
		
	}
}
