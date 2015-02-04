package com.rpsg.rpg.object.rpg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.view.GameViews;

public abstract class IRPGObject extends Actor implements Comparable<IRPGObject>,Serializable{
	
	private static final long serialVersionUID = 1L;
	public static final int FACE_L=7;
	public static final int FACE_R=10;
	public static final int FACE_U=4;
	public static final int FACE_D=1;
	
	public transient Image[] images;
	
	public int lastWalkSize,lastZ,lastFace;
	public int layer;
	public int mapx,mapy;
	public Vector2 position;
	
	public boolean walked=true;
	
	public IRPGObject(){}
	
	public int currentImageNo=1;
	
	public int foot=0;
	
	public boolean waitWhenCollide=true; 
	public boolean enableCollide=true;
	
	public Collide collide=new Collide();
	
	public String imgPath;
	public int bodyWidth,bodyHeight;
	public List<Walker> walkStack=new ArrayList<Walker>(); 
	
	
	public Image getCurrentImage(){
		images[getCurrentFoot()].setPosition(position.x, position.y);
		return images[getCurrentFoot()];
	}
	
	@Override
	public int compareTo(IRPGObject i) {
		if(i.mapy>this.mapy)
			return -1;
		else if(i.mapy==this.mapy)
			if(i.mapx>this.mapx)
				return -1;
			else if(i.mapx==this.mapx)
				return 0;
			else
				return 1;
		else
			return 1;
				
			
	}
	
	public void draw(SpriteBatch batch,float parentAlpha){
		this.getCurrentImage().setColor(this.getColor());
		this.getCurrentImage().draw(batch,parentAlpha);
	}
	
	public IRPGObject(String path,int width,int height){
		imgPath=path;
		bodyWidth=width;
		bodyHeight=height;
		images=IRPGObject.generateImages(path, width, height);
	}
	
	
	public static Image[] generateImages(String txt,int width,int height){
		Image[] images=new Image[12];
		for(int i=0;i<4;i++)
			for(int j=0;j<3;j++){
				images[(i*3)+j]=new Image(new TextureRegion(new Image(txt).getTexture(),j*width,i*height,width,height));
			}
		return images;
	};
	
	public IRPGObject turn(int face){
			this.currentImageNo=face;
		return this;
	}
	
	public float walkSpeed=3f;
	
	private Vector2 lastPosition;
	public IRPGObject walk(int step){
		walkStack.add(new Walker(this.getCurrentFace(),step));
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
	
	private int NEXT_FOOT=0;
	private int lastx,lasty,lastlength;
	@Override
	public void act(float f){
		if(isStop() && lastlength++>5)
			foot=0;
		toWalk();
	}
	
	public boolean isStop(){
		boolean flag=false;
		flag=((lastx==(int)position.x) && (lasty==(int)position.y));
		lastx=(int) position.x;
		lasty=(int) position.y;
		return flag;
	}
	
	public boolean toWalk(){
		if(!walked){
			lastlength=0;
			switch(getCurrentFace()){
			case FACE_D:{
				if (Math.abs(lastPosition.y-position.y)==48){
					if(testWalk()==null)
						return false;
				}else
					if(Math.abs(lastPosition.y-position.y)+walkSpeed<=48)
						position.y-=walkSpeed;
					else{
						position.y-=(48-Math.abs(lastPosition.y-position.y));
						return true;
					}
				break;
			}
			case FACE_U:{
				if (Math.abs(lastPosition.y-position.y)==48){
					if(testWalk()==null)
						return false;
				}else
					if(Math.abs(lastPosition.y-position.y)+walkSpeed<=48)
						position.y+=walkSpeed;
					else{
						position.y+=(48-Math.abs(lastPosition.y-position.y));
						return true;
					}
				break;
			}
			case FACE_L:{
				if (Math.abs(lastPosition.x-position.x)==48){
					if(testWalk()==null)
						return false;
				}else
					if(Math.abs(lastPosition.x-position.x)+walkSpeed<=48)
						position.x-=walkSpeed;
					else{
						position.x-=(48-Math.abs(lastPosition.x-position.x));
						return true;
					}
				break;
			}
			case FACE_R:{
				if (Math.abs(lastPosition.x-position.x)==48){
					if(testWalk()==null)
						return false;
				}else
					if(Math.abs(lastPosition.x-position.x)+walkSpeed<=48)
						position.x+=walkSpeed;
					else{
						position.x+=(48-Math.abs(lastPosition.x-position.x));
						return true;
					}
				break;
			}
			}
			if((NEXT_FOOT-=walkSpeed)<=0){
				if(++foot==3)
					foot=-1;
				NEXT_FOOT=50;
			}
		}
		return false;
	}
	
	public Boolean testWalk(){
		if(walkStack.size()!=0){
			if(enableCollide && ((getCurrentFace()==FACE_L && !collide.left) || (getCurrentFace()==FACE_R && !collide.right) 
			|| (getCurrentFace()==FACE_U && !collide.top) || (getCurrentFace()==FACE_D && !collide.bottom))){
				lastWalkSize=0;
				if(!waitWhenCollide){
					testWalkerSize();
				}else
					return null;
			}else{
				if(walkStack.get(0).step!=0){
					lastZ=this.layer;
					lastFace=this.getCurrentFace();
					lastWalkSize=walkStack.get(0).step;
					
					switch(getCurrentFace()){
					case FACE_D:{mapy++;break;}
					case FACE_U:{mapy--;break;}
					case FACE_L:{mapx--;break;}
					case FACE_R:{mapx++;break;}
					}
					walkStack.get(0).step--;
					lastPosition=new Vector2(position.x, position.y);
					walked=false;
					toWalk();
					return true;
				}else{
					testWalkerSize();
				}
			}
		}else{
			walked=true;
		}
		return false;
	}
	
	public boolean walkAble(){
//		collide.
		return false;
	}
	
	private void testWalkerSize(){
		walkStack.remove(walkStack.get(0));
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
	
	public IRPGObject generatePosition(int mapx,int mapy,int layer){
		try {
			TiledMapTileLayer l=(TiledMapTileLayer)GameViews.gameview.map.getLayers().get(layer);
			this.mapx=mapx;
			this.mapy= mapy;
			this.layer=layer;
			this.position=new Vector2(mapx*48,(l.getHeight()-this.mapy-1)*48);
			lastPosition=new Vector2(this.position);
		} catch (Exception e) {
		}
		return this;
	}
	
	public static int getReverseFace(int face){
		if(face==FACE_L)
			return FACE_R;
		if(face==FACE_D)
			return FACE_U;
		if(face==FACE_R)
			return FACE_L;
		else
			return FACE_D;
	}
	
	public int getReverseFace(){
		return getReverseFace(getCurrentFace());
	}
	
	public void dispose(){
		for(Image i:images)
			i.dispose();
	}
	
}
