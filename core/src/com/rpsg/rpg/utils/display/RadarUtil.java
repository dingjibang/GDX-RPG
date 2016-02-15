package com.rpsg.rpg.utils.display;


import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.rpsg.rpg.system.ui.PolygonRegion;

/**@deprecated*/
@Deprecated
public class RadarUtil {
	private static PolygonRegion region;
	private static TextureRegion txtr;
	private static PolygonSpriteBatch sb;
	private static PolygonSprite sprite;
	
	public static int max;
	
	public static void init(int propMaxSize,TextureRegion txt,int radius){
		txtr=txt;
		region=new PolygonRegion(txtr,  new float[] {}, new short[] {});
		sprite=new PolygonSprite(region);
		sb=new PolygonSpriteBatch();
		max=propMaxSize;
	}

	public static void setProjectionMatrix(Matrix4 m4){
		sb.setProjectionMatrix(m4);

	}

	public static void setTransformMatrix(Matrix4 m4){
		sb.setTransformMatrix(m4);
	}
	
	public static void draw(){
		if(display){
			if(currentFrame<maxFrame)
				currentFrame++;
			logic();
			sb.begin();
			sprite.draw(sb);
			sb.end();
		}
	}
	private static float currentFrame=0;
	private static float maxFrame=0;
	private static int[] currentProp;
	
	private static void logic(){
		int edges=currentProp.length;
		float angle=360/edges;
		float[] f=new float[edges*2+2];
		f[0]=f[1]=0;
		for(int i=1;i<=edges;i++){
			float currentAngle=(i-1)*angle;
			float length=radius*((float)currentProp[i-1]/max)*(currentFrame/maxFrame);
			f[i*2]=(float)(length*Math.cos(currentAngle *Math.PI /180));
			f[i*2+1]=(float)(length*Math.sin(currentAngle *Math.PI /180));
		}
		short[] s=new short[edges*3];
		for(int i=0;i<s.length/3;i++){
			s[i*3]=0;
			s[i*3+1]=(short) ((short) i+1);
			s[i*3+2]=i*3+3!=s.length?(short) ((short) i+2):1;
		}
		region=new PolygonRegion(txtr, f, s);
		sprite.setRegion(region);
	}
	private static int radius;
	private static float y=0,x=0;
	public static void show(int[] prop,int frame,float x,float y,int r){
		currentFrame=0;
		radius=r;
		maxFrame=frame;
		currentProp=prop;
		setPosition(x, y);
		display=true;
	}
	
	public static void setRotate(float ro){
		sprite.setRotation(ro);
		sprite.setOrigin(0, 0);
	}
	
	public static void setX(float xx){
		x=xx;
		sprite.setX(x);
	}
	
	public static void setY(float yy){
		y=yy;
		sprite.setY(y);
	}
	
	public static void setPosition(float xx,float yy){
		x=xx;
		y=yy;
		sprite.setPosition(xx, yy);
	}
	
	public static boolean display=false;
	
	public static void dispose(){
		region.getRegion().getTexture().dispose();
		sb.dispose();
	}
}
