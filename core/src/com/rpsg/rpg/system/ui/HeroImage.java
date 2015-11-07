package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.rpsg.rpg.object.rpg.Hero;


public class HeroImage extends Group{
	private final static int _goffset=9;
	private int offset=0,step=30,goffset;
	private Hero hero;
	private Image image;
	public HeroImage(Hero hero) {
		this(hero,_goffset);
	}
	
	public HeroImage(Hero hero,int off){
		goffset=off;
		this.hero=hero;
		addActor(this.image = new Image().size((int)hero.getWidth(), (int)hero.getHeight()).disableTouch());
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(step++>18){
			if(offset++>=3)
				offset=0;
			step=0;
		}
		image.setDrawable(hero.images[goffset+(offset==3?1:offset)].getDrawable());
		image.setColor(hero.prop.get("dead").equals(Hero.TRUE)?Color.valueOf("00000033"):new Color(1,1,1,1));
		super.draw(batch, parentAlpha);
	}
	
}
