package com.rpsg.rpg.utils.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitfire.postprocessing.effects.Bloom;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.view.GameViews;

public class WeatherUtil {
	public static int WEATHER_NO=0;
	public static int WEATHER_RAIN=1;
	public int type;
	
	public float baseSaturation,bloomIntesity,bloomSaturation,threshold;
	
	private ParticleEffect eff;
	
	public String getName(){
		if(type==WEATHER_NO)
			return "晴";
		if(type==WEATHER_RAIN)
			return "雨";
		else
			return "数据异常";
	}
	
	public void init(int type){
		this.type=type;
		RPG.global.weather=type;
		if(eff!=null)
			eff.dispose();
		if(type==WEATHER_RAIN){
			eff=new ParticleEffect();
			eff.load(Gdx.files.internal(Setting.PARTICLE+"rainp.p"),Gdx.files.internal(Setting.PARTICLE));
		}
		setPost();
	}
	
	private int lastHeroPositionX;
	float gameMenuListener=1;
	public void draw(SpriteBatch batch){
		if(GameViews.gameview.stackView==null && gameMenuListener<1)
			gameMenuListener+=.05;
		if(GameViews.gameview.stackView != null && gameMenuListener>0)
			gameMenuListener-=.05;
		batch.begin();
		Bloom bloom=GameViews.bloom;
		bloom.setBaseIntesity(baseSaturation*gameMenuListener);
		bloom.setBloomSaturation((bloomSaturation-0.2f)*gameMenuListener+0.2f);
		if(eff!=null){
			if(lastHeroPositionX==0)
				lastHeroPositionX=(int) RPG.ctrl.hero.getHeadHero().position.x;
			else{
				if(lastHeroPositionX!=RPG.ctrl.hero.getHeadHero().position.x){
					if(lastHeroPositionX>RPG.ctrl.hero.getHeadHero().position.x){//LEFT
						eff.getEmitters().get(0).getVelocity().setHigh(500, 500);
					}else{
						eff.getEmitters().get(0).getVelocity().setHigh(-500, -500);
					}
				}else{
					eff.getEmitters().get(0).getVelocity().setHigh(0, 0);
				}
				lastHeroPositionX=(int) RPG.ctrl.hero.getHeadHero().position.x;
			}
			eff.draw(batch,Gdx.graphics.getDeltaTime());
			eff.setPosition(0, 524);
			if(eff.isComplete())
				eff.reset();
		}
		batch.end();
		setPost();
	}
	
	private void setPost() {
		String post=(String) RPG.maps.getProp().get("POST");
		if(post!=null && post.length()!=0){
			baseSaturation=1f;
			bloomIntesity=0.2f;
			bloomSaturation=1.1f;
			threshold=0f;
			GameViews.vignette.setEnabled(false);
		}else if(type==WEATHER_RAIN){
			baseSaturation=0.7f;
			bloomIntesity=0.8f;
			bloomSaturation=0.2f;
			threshold=0f;
			GameViews.vignette.setEnabled(true);
		}else if(type==WEATHER_NO){
			baseSaturation=1f;
			bloomIntesity=0.7f;
			bloomSaturation=1.2f;
			threshold=0.3f;
			GameViews.vignette.setEnabled(true);
		}
		Bloom bloom=GameViews.bloom;
		bloom.setBaseSaturation(baseSaturation);
		bloom.setBloomIntesity(bloomIntesity);
		bloom.setBloomSaturation(bloomSaturation);
		bloom.setThreshold(threshold);
		bloom.setEnabled(true);
	}
	
	public BaseScriptExecutor setWeather(final Script script,final int t){
		return script.$((BaseScriptExecutor) new BaseScriptExecutor() {
			@Override
			public void init() {
				WeatherUtil.this.init(t);
			}
		});
	}
}
