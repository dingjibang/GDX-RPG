package com.rpsg.rpg.system.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.core.Setting;


public class HeroImage {
	private Image[] images=new Image[3]; 
	private Image shadow;
	
	public void draw(SpriteBatch batch,int img){
		shadow.draw(batch);
		images[img].draw(batch);
	}
	
	public static HeroImage generateImage(Image[] IRPGObjectImageArray,int x,int y){
		HeroImage hi=new HeroImage();
		Image i=new Image();
		i.setDrawable(IRPGObjectImageArray[9].getDrawable());
		hi.images[0]=new Image(i);
		hi.images[0].setPosition(x, y);
		i.setDrawable(IRPGObjectImageArray[10].getDrawable());
		hi.images[1]=new Image(i);
		hi.images[1].setPosition(x, y);
		i.setDrawable(IRPGObjectImageArray[11].getDrawable());
		hi.images[2]=new Image(i);
		hi.images[2].setPosition(x, y);
		hi.shadow=new Image(Res.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"walk_shadow.png"));
		hi.shadow.setPosition(x+5, y-5);
		return hi;
	}
	
	public void dispose(){
	}
}
