package com.rpsg.rpg.utils.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitfire.postprocessing.effects.Bloom;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.controller.HeroController;
import com.rpsg.rpg.view.GameViews;

public class WeatherUtil {
	public static int WEATHER_NO=0;
	public static int WEATHER_RAIN=1;
	public static int type;
	
	public static float baseSaturation,bloomIntesity,bloomSaturation,threshold;
	
	private static ParticleEffect eff;
	
	public static String getName(){
		if(type==WEATHER_NO)
			return "晴";
		if(type==WEATHER_RAIN)
			return "雨";
		else
			return "数据异常";
	}
	
	public static void init(int type){
		WeatherUtil.type=type;
		GameViews.global.weather=type;
		if(eff!=null)
			eff.dispose();
		if(type==WEATHER_RAIN){
			eff=new ParticleEffect();
			eff.load(Gdx.files.internal(Setting.GAME_RES_PARTICLE+"rainp.p"),Gdx.files.internal(Setting.GAME_RES_PARTICLE));
		}
		setPost();
	}
	
	private static int lastHeroPositionX;
	static float gameMenuListener=1;
	public static void draw(SpriteBatch batch){
		if(GameViews.gameview.stackView==null && gameMenuListener<1)
			gameMenuListener+=.05;
		if(GameViews.gameview.stackView != null && gameMenuListener>0)
			gameMenuListener-=.05;
		batch.begin();
		Bloom bloom=GameViews.bloom;
		bloom.setBaseIntesity(1.2f*gameMenuListener);
		bloom.setBloomSaturation((1.2f-0.2f)*gameMenuListener+0.2f);
		if(eff!=null){
			if(lastHeroPositionX==0)
				lastHeroPositionX=(int) HeroController.getHeadHero().position.x;
			else{
				if(lastHeroPositionX!=HeroController.getHeadHero().position.x){
					if(lastHeroPositionX>HeroController.getHeadHero().position.x){//LEFT
						eff.getEmitters().get(0).getVelocity().setHigh(500, 500);
					}else{
						eff.getEmitters().get(0).getVelocity().setHigh(-500, -500);
					}
				}else{
					eff.getEmitters().get(0).getVelocity().setHigh(0, 0);
				}
				lastHeroPositionX=(int) HeroController.getHeadHero().position.x;
			}
			eff.draw(batch,Gdx.graphics.getDeltaTime());
			eff.setPosition(0, 524);
			if(eff.isComplete())
				eff.reset();
		}
		batch.end();
	}
	
	private static void setPost() {
		if(type==WEATHER_RAIN){
			baseSaturation=0.7f;
			bloomIntesity=0.8f;
			bloomSaturation=0.2f;
			threshold=0f;
		}else if(type==WEATHER_NO){
			baseSaturation=1f;
			bloomIntesity=0.7f;
			bloomSaturation=1.2f;
			threshold=0.3f;
		}
		Bloom bloom=GameViews.bloom;
		bloom.setBaseSaturation(WeatherUtil.baseSaturation);
		bloom.setBloomIntesity(WeatherUtil.bloomIntesity);
		bloom.setThreshold(WeatherUtil.threshold);
	}
	
	public static BaseScriptExecutor setWeather(final Script script,final int t){
		return script.$((BaseScriptExecutor) new BaseScriptExecutor() {
			@Override
			public void init() {
				WeatherUtil.init(t);
			}
		});
	}
}
