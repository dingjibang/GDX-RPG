package com.rpsg.rpg.utils.display;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Image;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.utils.game.Logger;

public class TipUtil {
	static Stage stage;
	public static Image t_escape,t_select,t_z; 
	public static void init(){
		stage=new Stage();
		t_escape=Res.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"tip_escape.png");
		t_select=Res.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"tip_select.png");
		t_z=Res.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"tip_z.png");
		
		stage.addActor(t_escape);
		stage.addActor(t_select);
		stage.addActor(t_z);
		
		int offset=0;
		for(Actor a:stage.getActors()){
			Image i=(Image)a;
			i.setPosition(40+(offset+=120), 520);
		}
		Logger.info("图形提示控制器初始化完成。");
	}
	
	public static void draw(){
		SpriteBatch sb=(SpriteBatch) stage.getBatch();
		sb.begin();
		stage.getActors().forEach((a)->{
			if(((Image)a).visible)
				((Image)a).draw(sb);
		});
		sb.end();
	}
	
	public static void add(Image i){
		stage.getActors().forEach((a)->{
			if(((Image)a).equals(i))
				((Image)a).visible=true;
		});
	}
	
	public static void remove(Image i){
		stage.getActors().forEach((a)->{
			if(((Image)a).equals(i))
				((Image)a).visible=false;
		});
	}
	
	public static void removeAll(){
		stage.getActors().forEach((a)->{
			((Image)a).visible=false;
		});
	}
}
