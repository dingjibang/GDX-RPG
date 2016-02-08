package com.rpsg.rpg.object.rpg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Balloon.BalloonType;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.game.Logger;

public abstract class RPGObject extends Actor implements Comparable<RPGObject>,Serializable{
	
	//面朝向
	public static final int FACE_D=1,FACE_D_L=0,FACE_D_R=2;
	public static final int FACE_L=7,FACE_L_L=6,FACE_L_R=8;
	public static final int FACE_R=10,FACE_R_L=9,FACE_R_R=11;
	public static final int FACE_U=4,FACE_U_L=3,FACE_U_R=5;
	
	/**移动速度*/
	private static final int NORMAL_WALK_SPEED=4;
	
	private static final long serialVersionUID = 1L;
	
	/**阴影资源*/
	static Image shadow=Res.get(Setting.RPGOBJECT_SHADOW);
	
	/**
	 * 根据图片路径以及宽高，生成图片动画数组
	 */
	public static Image[] generateImages(String txt,int width,int height){
		Image[] images=new Image[12];
		for(int i=0;i<4;i++)
			for(int j=0;j<3;j++)
				images[(i*3)+j]=new Image(new TextureRegion(Res.getTexture(txt),j*width,i*height,width,height));
		return images;
	}
	
	/**
	 * 获取相反的面朝向
	 */
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
	
	public int bodyWidth,bodyHeight;
	
	/**气球*/
	transient Balloon bon=new Balloon(BalloonType.心);
	
	/**碰撞模块*/
	public Collide collide=new Collide();
	/**当前动画图片*/
	public int currentImageNo=1;
	/**是否显示气球表情*/
	private boolean displayBalloon=false;
	/**是否在脚下绘制一坨阴影*/
	public boolean drawShadow = false;
	/**是否是无敌状态（不受Enemy碰撞影响，也就是说不会触发战斗）*/
	private Action fade = null;
	/**帧数修复时间*/
	private int fixTime=0;
	/**步伐（0-left 1-normal 2-right）*/
	public int foot=0;
	/**图片动画资源*/
	public transient Image[] images;
	/**图片动画路径*/
	public String imgPath;
	/**上一次行走的位置*/
	public Vector2 lastPosition;
	/**上一次行走相关变量*/
	public int lastWalkSize,lastZ,lastFace;
	/**移动速度*/
	private int lastx,lasty,lastlength;
	/**Z-index 层数*/
	public int layer; 
	/**Object在地图中的位置*/
	public int mapx,mapy;
	/**是否在不行走时进行原地踏步的动画*/
	public boolean markTime = false;
	/**步伐计算*/
	private int NEXT_FOOT=0;
	/**地图位置*/
	public Vector2 position =new Vector2();
	/**碰撞参数<br>
	 * true时，碰撞到任意物体时会等待这个物体移开，并继续行走<br>
	 * false时，碰撞到物体时会停止移动
	 * */
	public boolean waitWhenCollide=true;
	/**是否启用碰撞*/
	public boolean enableCollide=true;
	/**是否移动结束了的*/
	public boolean walked=true;
	/**移动速度*/
	public float walkSpeed=3f;
	/**移动消息队列*/
	public List<MoveStack> walkStack=new ArrayList<MoveStack>();
	
	public RPGObject(){}
	/**
	 * 创建一个RPGObject
	 * @param path 动画路径
	 * @param width 宽度
	 * @param height 高度
	 */
	public RPGObject(String path,int width,int height){
		imgPath=path;
		bodyWidth=width;
		bodyHeight=height;
		images=RPGObject.generateImages(path, width, height);
	};
	
	/**计算行走*/
	@Override
	public void act(float f) {
		super.act(Gdx.graphics.getDeltaTime());
		
		if (isStop() && lastlength++ > 5 && !markTime)
			foot = 0;
		//帧数补偿
		//当游戏帧数小于60FPS时，将尝试加快IRPGObject的行走速度而达到补偿的目的
		int fps= Gdx.graphics.getFramesPerSecond();
		fixTime += 60-fps;
		if (fixTime < 0){
			fixTime = 0;
		}else if(fixTime >= 60){
			int fixCount= (int) (fixTime/60f);
			for(int i=0;i<fixCount;i++){
				toWalk();
			}
			fixTime-=fixCount*60;
		}

		toWalk();
		
		if(displayBalloon && !bon.isStop())
			bon.act(fps);
		else
			displayBalloon=false;
		
		if(markTime) actWalkAnimate();
	}
	
	/**计算动画*/
	public RPGObject actWalkAnimate(){
		if((NEXT_FOOT-=walkSpeed)<=0){
			if(++foot==3)
				foot=-1;
			NEXT_FOOT=(int) (walkSpeed*12);
		}
		return this;
	}
	
	/**
	 * 下面这段代码是2015年7月30日20:29:42写的，我发誓3个月后来看一定会自己揍自己一拳：）
	 * 2016年2月5日16:08:01 揍一拳！
	 * 计算步伐
	 * @return
	 */
	private int calcRevStep(){
		return walkStack.isEmpty() || walkStack.get(0).step == 0 ? lastWalkSize > 0 ? 1 : -1 : walkStack.get(0).step > 0 ? 1 : -1;
	}
	/**与另一个RPGObject比较，以地图左上为最小，右下为最大。*/
	@Override
	public int compareTo(RPGObject i) {
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
	/**绘制*/
	@Override
	public void draw(Batch batch,float parentAlpha){
		if(isVisible()){
			Image current = getCurrentImage();
			if(this.drawShadow){
				shadow.position(getX()+8f, getY()-0f).color(this.getColor()).draw(batch,parentAlpha);
				current.moveBy(0, 3);
			}
			current.setColor(this.getColor());
			current.draw(batch,parentAlpha);
			if(displayBalloon){
				bon.getCurrentImage().setPosition(getX()+Balloon.ANIMATION_SIZE, getY()+getHeight()+Balloon.ANIMATION_SIZE/2);
				bon.getCurrentImage().setColor(this.getColor());
				bon.getCurrentImage().draw(batch,parentAlpha);
			}
		}
	}
	/**生成绝对的位置（px而不是地图坐标）*/
	public RPGObject generateAbsolutePosition(int x,int y,int layer){
		try {
			this.mapx=x/48;
			this.mapy= y/48;
			this.layer=layer;
			this.position=new Vector2(x,y);
			lastPosition=new Vector2(this.position);
		} catch (Exception e) {
			Logger.error("无法生成RPG对象位置！", e);
		}
		return this;
	}
	/**生成相对的位置（地图坐标）*/
	public RPGObject generatePosition(int mapx,int mapy,int layer){
		
		int yoff = (int) Math.ceil(getHeight()/48);
		
		try {
			TiledMapTileLayer l=(TiledMapTileLayer)RPG.maps.loader.layer.get(layer);
			this.mapx=mapx;
			this.mapy= mapy-yoff;
			this.layer=layer;
			this.position=new Vector2(mapx*48,(l.getHeight()-this.mapy-1)*48);
			lastPosition=new Vector2(this.position);
		} catch (Exception e) {
			Logger.error("无法生成RPG对象位置！请检查坐标设置是否正确>>["+e.getMessage()+"]");
			e.printStackTrace();
		}
		return this;
	}
	/**获取当前的面朝向*/
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
	/**获取当前面朝向（修复）*/
	private int getCurrentFaceByRev(){
		if(calcRevStep()==1)
			return getCurrentFace();
		else
			return getReverseFace();
	}
	/**获取当前步伐*/
	public int getCurrentFoot(){
		return getCurrentFace()+(foot==2?0:foot);
	}
	
	/**根据面朝向和步伐获取当前的动画图片*/
	public Image getCurrentImage(){
		images[getCurrentFoot()].setPosition(position.x, position.y);
		return images[getCurrentFoot()];
	}
	/**根据一个点，获取对应点的面朝向*/
	public int getFaceByPoint(int x,int y){
		//boolean xable=MathUtils.random(100)>50;
		int dx,dy;
		dx=Math.abs(x-mapx);
		dy=Math.abs(y-mapy);
		int face =this.getCurrentFace();
		if((dx!=0 && (face==FACE_L||face==FACE_R))|| dy == 0)
			return mapx>x?FACE_L:FACE_R;
		else
			return mapy>y?FACE_U:FACE_D;
	}
	
	@Override
	public float getHeight(){
		return this.getCurrentImage().getHeight();
	}
	/**获取相反的面朝向*/
	public int getReverseFace(){
		return getReverseFace(getCurrentFace());
	}
	/**获取移动速度*/
	public float getWalkSpeed(){
		return walkSpeed;
	}
	
	@Override
	public float getWidth(){
		return this.getCurrentImage().getWidth();
	}
	
	@Override
	public float getX(){
		return this.position.x;
	}
	
	@Override
	public float getY(){
		return this.position.y;
	}
	/**是否停止了的，请使用isWalked变量来判断*/
	private boolean isStop(){
		boolean flag=false;
		flag=((lastx==(int)position.x) && (lasty==(int)position.y));
		lastx=(int) position.x;
		lasty=(int) position.y;
		return flag;
	}
	
	/**重置气球图片*/
	public RPGObject resetBalloon(BalloonType type){
		this.bon.reset();
		return this.setDisplayBalloon(true);
	}
	/**重置移动速度*/
	public RPGObject resetWalkSeped(){
		this.walkSpeed=NORMAL_WALK_SPEED;
		return this;
	}
	/**设置气球心情*/
	public RPGObject setBalloon(BalloonType type){
		this.bon.setBalloons(new Balloon(type).getBalloons()).reset();
		return this.setDisplayBalloon(true);
	}
	/**设置是否显示气球*/
	public RPGObject setDisplayBalloon(boolean flag){
		this.displayBalloon=flag;
		return this;
	}
	
	@Override
	public void setPosition(float x, float y) {
		this.position.set(x,y);
	}
	/**设置移动速度*/
	public RPGObject setWalkSpeed(float s){
		this.walkSpeed=s;
		return this;
	}
	
	@Override
	public void setX(float x) {
		this.position.x = x;
	}
	
	@Override
	public void setY(float y) {
		this.position.y = y;
	}
	/**进行移动运算*/
	public Boolean testWalk(){
		if(walkStack.size()!=0){
			if(enableCollide && ((getCurrentFaceByRev()==FACE_L && !collide.left) || (getCurrentFaceByRev()==FACE_R && !collide.right) 
			|| (getCurrentFaceByRev()==FACE_U && !collide.top) || (getCurrentFaceByRev()==FACE_D && !collide.bottom))){
				walkStack.get(0).step=0;
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
					case FACE_D:{mapy+=calcRevStep();break;}
					case FACE_U:{mapy-=calcRevStep();break;}
					case FACE_L:{mapx-=calcRevStep();break;}
					case FACE_R:{mapx+=calcRevStep();break;}
					}
					walkStack.get(0).step+=walkStack.get(0).step>=0?-1:1;
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
	/**判断是否仍有移动队列*/
	private RPGObject testWalkerSize(){
		walkStack.remove(walkStack.get(0));
		return this;
	}
	/**移动*/
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
						position.y-=walkSpeed*calcRevStep();
					else{
						position.y-=(48-Math.abs(lastPosition.y-position.y))*calcRevStep();
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
						position.y+=walkSpeed*calcRevStep();
					else{
						position.y+=(48-Math.abs(lastPosition.y-position.y))*calcRevStep();
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
						position.x-=walkSpeed*calcRevStep();
					else{
						position.x-=(48-Math.abs(lastPosition.x-position.x))*calcRevStep();
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
						position.x+=walkSpeed*calcRevStep();
					else{
						position.x+=(48-Math.abs(lastPosition.x-position.x))*calcRevStep();
						return true;
					}
				break;
			}
			}
			if(!markTime)
				actWalkAnimate();
		}
		return false;
	}
	/**转向*/
	public RPGObject turn(int face){
		currentImageNo=face;
		return this;
	}
	/**向面朝向的方向行走step步，加入到移动队列中*/
	public RPGObject walk(int step){
		walkStack.add(new MoveStack(this.getCurrentFace(),step));
		return this;
	}
	
	public RPGObject fade(boolean flag){
		if(flag){ 
			addAction(fade = Actions.forever(Actions.sequence(Actions.alpha(.5f,.2f),Actions.alpha(1,.2f))));
		}else{
			removeAction(fade);
			getColor().a = 1;
			fade = null;
		}
		
		return this;
	}
	
	public boolean fade(){
		return fade != null;
	}
	
}
