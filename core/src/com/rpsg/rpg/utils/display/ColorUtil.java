package com.rpsg.rpg.utils.display;


import box2dLight.RayHandler;

import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.utils.game.GameDate;
import com.rpsg.rpg.utils.game.GameDate.Time;
import com.rpsg.rpg.view.GameViews;


public class ColorUtil {
	
	public static BaseScriptExecutor set(final Script script, final GameDate.Time time){
		return script.set(new BaseScriptExecutor() {
			@Override
			public void init() {
				RPG.global.date.setTime(time);
			}
		});
	}


	public static void draw(){
		RayHandler ray=GameViews.gameview.ray;
		ray.setCombinedMatrix(GameViews.gameview.camera.combined);
		if(RPG.global.date.getTime()==Time.NIGHT){
				ray.setAmbientLight(0.2f,0.2f,0.35f,0.6f);
				ray.updateAndRender();
		}
		if(RPG.global.date.getTime()==Time.DUSK){
			ray.setAmbientLight(0.8f,0.8f,0.6f,0.8f);
			ray.updateAndRender();
		}
	}

}
