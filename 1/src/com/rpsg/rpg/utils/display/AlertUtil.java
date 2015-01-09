package com.rpsg.rpg.utils.display;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Alert;
import com.rpsg.rpg.system.base.Image;
import com.rpsg.rpg.utils.game.Logger;

public class AlertUtil {
	
	public static Image Yellow,Green,Red;
	
	public static NinePatch box;
	
	public static List<Alert> list=new ArrayList<Alert>();
	
	static int oyc;
	
	public static void init(){
		Yellow=new Image(Setting.GAME_RES_GLOBAL+"t.png");
		Green=new Image(Setting.GAME_RES_GLOBAL+"o.png");
		Red=new Image(Setting.GAME_RES_GLOBAL+"x.png");
		box=processNinePatchFile(Setting.GAME_RES_GLOBAL+"box.9.png");
		Logger.info("��ʾģ��������ɡ�");
	}
	
	public static void add(String alert,Image type){
		list.add(new Alert(new Image(type), alert));
	}
	
	public static void add(String alert,Image type,int fontsize){
		list.add(new Alert(new Image(type), alert, fontsize));
	}
	
	public static void draw(SpriteBatch sb){
		list.removeIf((a)->a.dispose);
		for(int i=0;i<list.size();i++){
			list.get(i).draw(sb, i*60);
		}
		sb.setColor(1,1,1,1);
		box.draw(sb, 100, 100, 300, 400);
	}
	
	private static NinePatch processNinePatchFile(String fname) {
	    final Texture t = new Texture(Gdx.files.internal(fname));
	    final int width = t.getWidth() - 2;
	    final int height = t.getHeight() - 2;
	    return new NinePatch(new TextureRegion(t, 1, 1, width, height), 3, 3, 3, 3);
	}
	
}
