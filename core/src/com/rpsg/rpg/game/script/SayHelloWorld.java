package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.utils.display.ColorUtil;
import com.rpsg.rpg.utils.display.WeatherUtil;
import com.rpsg.rpg.view.GameViews;

public class SayHelloWorld extends Script{
	
	public void init() {
//		$(new BaseScriptExecutor() {
//			@Override
//			public void init() {
//				if(WeatherUtil.type==WeatherUtil.WEATHER_NO){
//					_$(setWeather(WeatherUtil.WEATHER_RAIN));
//				}else{
//					_$(setWeather(WeatherUtil.WEATHER_NO));
//				}
//			}
//		});
		$(new BaseScriptExecutor() {
			  @Override
			  public void init() {
				  if(++GameViews.global.mapColor>2)
					  GameViews.global.mapColor=0;
			  }
		  }
		);

//		lock(true);
//		faceToHero();
//		showMSG();
//		showFGLeft(爱丽丝立绘, 思考);
//		say("你好!");
//		hideFG();
//		hideMSG();
//		lock(false);
	}
}
