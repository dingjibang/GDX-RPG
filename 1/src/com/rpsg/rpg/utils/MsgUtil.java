package com.rpsg.rpg.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.system.Image;
import com.rpsg.rpg.system.Setting;


public class MsgUtil {
	public static Image msgbox;
	
	public static void init(){
		msgbox=new Image(Setting.GAME_RES_MESSAGE+"msgbox.png");
		float ss=msgbox.getWidth();
		msgbox.setWidth(GameUtil.screen_width-40);
		ss=ss/msgbox.getWidth();
		msgbox.setHeight((float)msgbox.getHeight()/ss);
		msgbox.setX(GameUtil.screen_width/2-msgbox.getWidth()/2);
		msgbox.setY(25);
	}
	public static void MSG(SpriteBatch batch,String str,String title,int size){
		batch.end();
		batch.begin();
		msgbox.draw(batch);
		FontUtil.draw(batch, str, size, Color.DARK_GRAY, 50, 140, (int) (msgbox.getWidth()-60));
		FontUtil.draw(batch, title, 22, Color.WHITE, 70, 185, (int) (msgbox.getWidth()-60));
	}
}
