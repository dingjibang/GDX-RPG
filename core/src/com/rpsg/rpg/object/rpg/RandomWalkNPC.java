package com.rpsg.rpg.object.rpg;

import java.awt.Rectangle;

import com.badlogic.gdx.math.Vector2;
import com.rpsg.rpg.core.RPG;

public class RandomWalkNPC extends PublicNPC {

	public RandomWalkNPC(String id, String path) {
		super(id, path);
		bounds = null;
		maxLength = 3;
		minWalkLength = 1;
	}
	
	public Rectangle bounds;
	public int maxLength;
	public int minWalkLength;
	protected Vector2 point;
	private int currentFrame;
	public int speed = 60;
//	private 
	protected int step = -1;
	
	public boolean stop = false;

	private static final long serialVersionUID = 1L;
	
	@Override
	public void act(float f) {
		if(stop) return;
		
		if(walked){
			
			if(currentFrame++ < speed){
				super.act(f);
				return;
			}
			
			currentFrame = 0;
			
			int maxWalkLength=maxLength;
			Vector2 bo2 = null;
			if(step == -1)
			step = (int)(Math.random()*(maxWalkLength-minWalkLength)+minWalkLength);

			if(bounds!=null)
				bo2=new Vector2(0,0);

			int face = 0;
			Hero Hero = RPG.ctrl.hero.getHeadHero();

			if(point==null){
				face = (int)(Math.random()*4);
				if(face == 3)
					face=RPGObject.FACE_D;
				else if(face == 2)
					face=RPGObject.FACE_U;
				else if(face == 1)
					face=RPGObject.FACE_L;
				else if(face == 0) 
					face=RPGObject.FACE_R;
			}else if(point.x!= -1 && point.y!= -1){
				face = getFaceByPoint((int)point.x, (int)point.y);
			}else{
				face = getFaceByPoint(Hero.mapx,Hero.mapy);
			}

			if(bo2!=null){
				if(face==RPGObject.FACE_D){
					if(bo2.y+step<bounds.y){
						bo2.y+=step;
					}else{
						if(bo2.y<bounds.y) step=bounds.y-step; else step=0;
						bo2.y=bounds.y;
					}
				}else if(face==RPGObject.FACE_U){
					if(bo2.y-step>-bounds.y){
						bo2.y-=step;
					}else{
						if(bo2.y>-bounds.y) step=Math.abs( Math.abs(bounds.y)-step); else step=0;
						bo2.y=-bounds.y;
					}
				}else if(face==RPGObject.FACE_R){
					if(bo2.x+step<bounds.x){
						bo2.x+=step;
					}else{
						if(bo2.x<bounds.x) step=bounds.x-step; else step=0;
						bo2.x=bounds.x;
					}
				}else if(face==RPGObject.FACE_L){
					if(bo2.x-step>-bounds.x){
						bo2.x-=step;
					}else{
						if(bo2.x>-bounds.x) step=Math.abs(Math.abs(bounds.x)-step); else step=0;
						bo2.x=-bounds.x;
					}
				}
			}

			turn(step>0?face:RPGObject.getReverseFace(face));
			walk(step>0?step:0).testWalk();
//			move(_step>0?_step:0);
			point = null;
			step = -1;
		}
		super.act(f);
	}

	void stopRandomWalking(){
		stop = true;
	}
}
