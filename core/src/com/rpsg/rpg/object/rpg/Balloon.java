package com.rpsg.rpg.object.rpg;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.Image;

public class Balloon extends Actor implements Serializable{
	private static final long serialVersionUID = 1L;
	static final String BALLOON=Setting.MESSAGE+"balloon.png";
	static final int ICON_SIZE=32,ANIMATION_SIZE=7,ANIMATION_SPEED=10;
	
	Image[] balloons=new Image[ANIMATION_SIZE];
	int current=0,buffer=0;
	public Balloon(BalloonType type) {
		int y=type.value();
		Texture tex=Res.getTexture(BALLOON);
		TextureRegion region=new TextureRegion(tex,0,y*ICON_SIZE,tex.getWidth(),ICON_SIZE);
		for(int i=0;i<balloons.length;i++)
			balloons[i]=new Image(new TextureRegion(region,i*ICON_SIZE,0,ICON_SIZE,ICON_SIZE));
	}
	
	public Image[] getBalloons() {
		return balloons;
	}
	
	public Balloon setBalloons(Image[] balloons) {
		this.balloons = balloons;
		return this;
	}
	
	public Balloon reset() {
		current=buffer=0;
		return this;
	}
	
	public int getCurrentFrame(){
		return current;
	}
	
	public Image getCurrentImage(){
		return balloons[current];
	}
	
	public void act(float delta) {
		if(++buffer>ANIMATION_SPEED){
			buffer=0;
			if(++current>=ANIMATION_SIZE)
				current=0;
		}
	}
	
	public boolean isStop(){
		return current>=ANIMATION_SIZE-1;
	}
	
	public void act(){
		act(Gdx.graphics.getDeltaTime());
	}
	
	public static enum BalloonType{
		惊讶(0),疑惑(1),乐符(2),心(3),生气(4),汗(5),混乱(6),沉默(7),灵感(8),睡着(9);
		
		private int value = 0;
		
		private BalloonType(int value){
			this.value=value;
		}
		
		public int value(){
			return this.value;
		}
	}
}
