package com.rpsg.rpg.system.ui;

import com.rpsg.rpg.object.rpg.Hero;


public class HeroImage extends BGActor{
	private Image shadow;
	private final static int goffset=9;
	private int offset=0,step=30;
	private Hero hero;
	public HeroImage(Hero hero) {
		this.hero=hero;
		this.image=new Image();
		this.image.setSize(hero.getWidth(), hero.getHeight());
		this.image.disableTouch();
	}
	
	@Override
	public void drawBefore() {
		if(step++>18){
			if(offset++>=3)
				offset=0;
			step=0;
		}
		image.setDrawable(hero.images[goffset+(offset==3?1:offset)].getDrawable());
		image.setDebug(true);
	}
	

}
