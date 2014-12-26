package com.rpsg.rpg.utils.display;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.PolygonRegion;

public class RadarUtil {
	private static PolygonRegion region;
	private static TextureRegion txtr;
	private static PolygonSpriteBatch sb;
	private static PolygonSprite sprite;
	
	private static int max=100;
	private static int[] prop=new int[]{55,30,30,30,30};
	public static void init(){
		txtr=new TextureRegion(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOGO+"bg.png")));
		region=new PolygonRegion(txtr,  new float[] {}, new short[] {});
		sprite=new PolygonSprite(region);
		sb=new PolygonSpriteBatch();
		reinit();
	}
	
	public static void draw(){
		sb.begin();
		sprite.setPosition(300, 300);
		sprite.draw(sb);
		sb.end();
	}
	
	public static void reinit(){
		region=new PolygonRegion(txtr,  generateVertices(), new short[] {
				0,1,2,
				0,2,3,
				0,3,4,
				0,4,5,
				0,5,1
			});
		sprite.setRegion(region);
	}
	
	private static float[] generateVertices(){
		prop=new int[]{30,30,40,30,90};
		int edges=prop.length;
		float angle=360/edges;
		float[] f=new float[edges*2+2];
		f[0]=f[1]=0;
		for(int i=1;i<=edges;i++){
			float currentAngle=(i-1)*angle;
			float length=200*((float)prop[i-1]/max);
			f[i*2]=(float)(length*Math.cos(currentAngle *Math.PI /180));
			f[i*2+1]=(float)(length*Math.sin(currentAngle *Math.PI /180));
			
		}
//		System.out.println(Arrays.toString(f));
		return f;
	}
	
}
