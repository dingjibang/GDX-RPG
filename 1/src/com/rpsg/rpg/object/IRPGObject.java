package com.rpsg.rpg.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rpsg.rpg.system.Image;

public abstract class IRPGObject extends Actor{
	
	public static final int FACE_L=7;
	public static final int FACE_R=10;
	public static final int FACE_U=4;
	public static final int FACE_D=1;
	
	public Image[] images;
	
	public Vector2 position;
	
	public boolean walked=true;
	
	public IRPGObject(){}
	
	public int currentImageNo=1;
	
	public int foot=0;
	
	public Image getCurrentImage(){
		images[getCurrentFoot()].setPosition(position.x, position.y);
		return images[getCurrentFoot()];
	}
	
	@Override
	public void draw(SpriteBatch batch,float parentAlpha){
		this.getCurrentImage().draw(batch);
	}
	
	public IRPGObject(String path,int width,int height){
		images=IRPGObject.generalImages(new Image(path), width, height);
	}
	
	public IRPGObject(Image txt,int width,int height){
		images=IRPGObject.generalImages(txt, width, height);
	}
	
	public static Image[] generalImages(Image txt,int width,int height){
		Image[] images=new Image[12];
		for(int i=0;i<4;i++)
			for(int j=0;j<3;j++){
				images[(i*3)+j]=new Image(new TextureRegion(txt.getTexture(),j*width,i*height,width,height));
			}
		return images;
	};
	
	public IRPGObject turn(int face){
		if(walked)
			this.currentImageNo=face;
		System.out.println(getCurrentFoot());
		return this;
	}
	
	private float walkSpeed=3f;
	
	private Vector2 lastPosition;
	private int lastStep;
	public IRPGObject walk(int step){
		if(walked){
			lastPosition=new Vector2(position.x, position.y);
			lastStep=step-1;
		}
		walked=false;
		return this;
	}
	
	public int getCurrentFace(){
		if(this.currentImageNo==0 || this.currentImageNo==1 || this.currentImageNo==2)
			return FACE_D;
		if(this.currentImageNo==3 || this.currentImageNo==4 || this.currentImageNo==5)
			return FACE_U;
		if(this.currentImageNo==6 || this.currentImageNo==7 || this.currentImageNo==8)
			return FACE_L;
		else
			return FACE_R;
	}
	private int NEXT_FOOT=50;
	@Override
	public void act(float f){
		if(!walked){
			switch(getCurrentFace()){
			case FACE_D:{
				if (Math.abs(lastPosition.y-position.y)==48)
					testWalk();
				else
					if(Math.abs(lastPosition.y-position.y)+walkSpeed<=48)
						position.y-=walkSpeed;
					else
						position.y-=(48-Math.abs(lastPosition.y-position.y));
				break;
			}
			case FACE_U:{
				if (Math.abs(lastPosition.y-position.y)==48)
					testWalk();
				else
					if(Math.abs(lastPosition.y-position.y)+walkSpeed<=48)
						position.y+=walkSpeed;
					else
						position.y+=(48-Math.abs(lastPosition.y-position.y));
				break;
			}
			case FACE_L:{
				if (Math.abs(lastPosition.x-position.x)==48)
					testWalk();
				else
					if(Math.abs(lastPosition.x-position.x)+walkSpeed<=48)
						position.x-=walkSpeed;
					else
						position.x-=(48-Math.abs(lastPosition.x-position.x));
				break;
			}
			case FACE_R:{
				if (Math.abs(lastPosition.x-position.x)==48)
					testWalk();
				else
					if(Math.abs(lastPosition.x-position.x)+walkSpeed<=48)
						position.x+=walkSpeed;
					else
						position.x+=(48-Math.abs(lastPosition.x-position.x));
				break;
			}
			}
			if((NEXT_FOOT-=walkSpeed)<=0){
				if(++foot==2)
					foot=-1;
				NEXT_FOOT=50;
			}
		}else{
			foot=0;
		}
	}
	
	public void testWalk(){
		if(lastStep!=0){
			lastStep--;
			lastPosition=new Vector2(position.x, position.y);
		}
		else
			walked=true;
	}
	
	public void setWalkSpeed(float s){
		this.walkSpeed=s;
	}
	
	public float getWalkSpeed(){
		return walkSpeed;
	}
	
	public int getCurrentFoot(){
		return getCurrentFace()+(foot==2?0:foot);
	}
	
	@Override
	public float getX(){
		return this.position.x;
	}
	
	@Override
	public float getY(){
		return this.position.y;
	}
	
	@Override
	public float getWidth(){
		return this.getCurrentImage().getWidth();
	}
	
	@Override
	public float getHeight(){
		return this.getCurrentImage().getHeight();
	}
}
