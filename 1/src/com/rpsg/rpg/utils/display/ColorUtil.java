package com.rpsg.rpg.utils.display;

import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.view.GameViews;


public class ColorUtil {
	public final static int DAY=0;
	public final static int NIGHT=1;
	public final static int DUSK=2;
	
	public static BaseScriptExecutor set(final Script script,int color){
		return script.$(()->{
			GameViews.global.mapColor=color;
		});
	}
	
	public static void draw(SpriteBatch batch){
		RayHandler ray=GameViews.gameview.ray;
		ray.setCombinedMatrix(GameViews.gameview.camera.combined);
		if(GameViews.global.mapColor==NIGHT){
			ray.setAmbientLight(0.3f,0.3f,0.6f,0.3f);
			ray.updateAndRender();
		}
		if(GameViews.global.mapColor==DUSK){
			ray.setAmbientLight(0.8f,0.8f,0.6f,0.8f);
			ray.updateAndRender();
		}
	}
	
	public static void drawhover(SpriteBatch batch){
		RayHandler ray=GameViews.gameview.ray;
		if(GameViews.global.mapColor==NIGHT){
			ray.setAmbientLight(0.8f,0.8f,0.8f,1);
			ray.render();
		}
	}
}
