package com.rpsg.rpg.object.rpg;

import java.awt.Point;
import java.awt.Rectangle;

import com.rpsg.rpg.core.RPG;

public class RandomWalkNPC extends PublicNPC {

	public RandomWalkNPC(String id, String path, Integer width, Integer height) {
		super(id, path, width, height);
		bounds = null;
		maxLength = 3;
		minWalkLength = 1;
	}
	
	public Rectangle bounds;
	public int maxLength;
	public int minWalkLength;
	protected Point _point;
	protected Boolean isStopped;
	protected int _step = -1;

	private static final long serialVersionUID = 1L;
	
	@Override
	public void act(float f) {
		if(walked){
			int maxWalkLength=maxLength;
			Point bo2 = null;
			if(_step == -1)
			_step = (int)(Math.random()*(maxWalkLength-minWalkLength)+minWalkLength);

			if(bounds!=null)
				bo2=new Point(0,0);

			int face = 0;
			Hero Hero = RPG.ctrl.hero.getHeadHero();

			if(_point==null){
				face = (int)(Math.random()*4);
				if(face == 3)
					face=RPGObject.FACE_D;
				else if(face == 2)
					face=RPGObject.FACE_U;
				else if(face == 1)
					face=RPGObject.FACE_L;
				else if(face == 0) 
					face=RPGObject.FACE_R;
			}else if(_point.x!= -1 && _point.y!= -1){
				face = getFaceByPoint(_point.x, _point.y);
			}else{
				face = getFaceByPoint(Hero.mapx,Hero.mapy);
			}

			if(bo2!=null){
				if(face==RPGObject.FACE_D){
					if(bo2.y+_step<bounds.y){
						bo2.y+=_step;
					}else{
						if(bo2.y<bounds.y) _step=bounds.y-_step; else _step=0;
						bo2.y=bounds.y;
					}
				}else if(face==RPGObject.FACE_U){
					if(bo2.y-_step>-bounds.y){
						bo2.y-=_step;
					}else{
						if(bo2.y>-bounds.y) _step=Math.abs( Math.abs(bounds.y)-_step); else _step=0;
						bo2.y=-bounds.y;
					}
				}else if(face==RPGObject.FACE_R){
					if(bo2.x+_step<bounds.x){
						bo2.x+=_step;
					}else{
						if(bo2.x<bounds.x) _step=bounds.x-_step; else _step=0;
						bo2.x=bounds.x;
					}
				}else if(face==RPGObject.FACE_L){
					if(bo2.x-_step>-bounds.x){
						bo2.x-=_step;
					}else{
						if(bo2.x>-bounds.x) _step=Math.abs(Math.abs(bounds.x)-_step); else _step=0;
						bo2.x=-bounds.x;
					}
				}
			}

			turn(_step>0?face:RPGObject.getReverseFace(face));
			walk(_step>0?_step:0).testWalk();
			//move(_step>0?_step:0);
			_point = null;
			_step = -1;
		}
		super.act(f);
	}
	
	public void StartRandomWalking(){
		isStopped = false;
	}
	
	public void StopRandomWalking(){
		isStopped = true;
	}

}
