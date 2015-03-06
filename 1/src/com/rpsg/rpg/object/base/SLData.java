package com.rpsg.rpg.object.base;

import java.io.Serializable;





import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	public String heroName;
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
		if(Files.empty(Files.getABSPath()+fileName)){
			slData = new SLData();
			slData.thumbnail=Res.get(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"ea.png");
			stage.addActor(new Label("¿ÕÎ»ÖÃ",26).userObj(new Object()).setWidth(1000).setPos(i>1?324+(i-2)*483:324+i*483, i>1?347:191));
		}else{
			slData = (SLData) Files.load(fileName);
			Texture txt=Res.getTexture("save/"+id+".png");
			slData.thumbnail=new Image(new TextureRegion(txt,0,txt.getHeight(),txt.getWidth(),-txt.getHeight()));
			stage.addActor(new Label("LV "+slData.level,30).userObj(new Object()).setPad(-5).setWidth(1000).setPos(i>1?270+(i-2)*483:270+i*483, i>1?382:226));
			stage.addActor(new Label(slData.mapName+","+slData.heroName,18).userObj(new Object()).setWidth(1000).setPos(i>1?274+(i-2)*483:274+i*483, i>1?347:191));
			stage.addActor(new Label(slData.gameDate,18).userObj(new Object()).setWidth(1000).setPos(i>1?274+(i-2)*483:274+i*483, i>1?322:166));
			stage.addActor(new Label(slData.saveDate,14).userObj(new Object()).setWidth(1000).setPad(-5).setPos(i>1?283+(i-2)*483:283+i*483, i>1?295:139));
		}
		slData.thumbnail.setUserObject(new Object());
		slData.thumbnail.setSize(207,112);
		stage.addActor(slData.thumbnail.position(i>1?54+(i-2)*483:54+i*483, i>1?277:121));
		stage.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"/saveno.png").position(i>1?54+(i-2)*483:54+i*483, i>1?277:121));
		stage.addActor(new Label(id+"",14).userObj(new Object()).setWidth(1000).setPad(-5).setPos(0, i>1?295:139).setAlignX(i>1?83+(i-2)*483:83+i*483));
	}
	
	static class exMask{
		
	}
}
