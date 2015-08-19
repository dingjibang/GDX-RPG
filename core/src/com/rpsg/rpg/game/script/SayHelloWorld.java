package com.rpsg.rpg.game.script;

import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.utils.display.WeatherUtil;

public class SayHelloWorld extends Script{
	
	public void init() {
		set(new BaseScriptExecutor() {
			@Override
			public void init() {
				if(RPG.ctrl.weather.type==WeatherUtil.WEATHER_NO){
//					_$(setWeather(WeatherUtil.WEATHER_RAIN));
				}else{
//					_$(setWeather(WeatherUtil.WEATHER_NO));
				}
			}
		});
//		$(new BaseScriptExecutor() {
//			  @Override
//			  public void init() {
//				  if(++RPG.global.mapColor>2)
//					  RPG.global.mapColor=0;
//			  }
//		  }
//		);

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
