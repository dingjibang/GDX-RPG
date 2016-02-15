package com.rpsg.rpg.utils.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.bitfire.postprocessing.effects.Bloom;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Weather;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.controller.BattleController.State;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.view.GameViews;

public class WeatherUtil {
	
	public Weather type;
	
	public float baseSaturation,bloomIntesity,bloomSaturation,threshold;
	Stage stage;
	
	public Texture txt = new Texture(Setting.IMAGE_BACKGROUND+"bgnd2.jpg");
	
	private ParticleEffect eff;
	
	public String getName(){
		if(type == null)
			return "数据异常";
		
		return type.value();
	}
	
	public void init(Weather type){
		this.type = type;
		RPG.global.weather = type;
		if (eff != null)
			eff.dispose();
		if (stage == null)
			stage = new Stage(new ScalingViewport(Scaling.stretch, 1024, 576, new OrthographicCamera()),GameViews.batch);
		if (type == Weather.rain) {
			eff = new ParticleEffect();
			eff.load(Gdx.files.internal(Setting.PARTICLE + "rainp.p"), Gdx.files.internal(Setting.PARTICLE));
			eff.start();
		} else {
			eff = null;
		}
		logic();
	}
	
	private int lastHeroPositionX;
	float gameMenuListener=1,battleListener=0;
	
	public void draw(SpriteBatch batch){
		batch.end();
		
		stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
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
			eff.setPosition(0,GameUtil.getScreenHeight());
			
			stage.getBatch().begin();
			eff.draw(stage.getBatch(),Gdx.graphics.getDeltaTime());
			stage.getBatch().end();
			
			if(eff.isComplete())
				eff.reset();
		}
		
		batch.begin();
	}
	
	public void logic(){
		if(GameViews.gameview.stackView==null && gameMenuListener<1)
			gameMenuListener+=.05;
		if(GameViews.gameview.stackView != null && gameMenuListener>0)
			gameMenuListener-=.05;
		
		battleListener = (RPG.ctrl.battle.state == State.wait) ? battleListener+ .005f : 0; 
		
		Bloom bloom=GameViews.bloom;
		bloom.setBaseIntesity(baseSaturation*gameMenuListener + battleListener);
		bloom.setBloomSaturation((bloomSaturation-0.2f)*gameMenuListener+0.2f);
		
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
		}else if(type==Weather.rain){
			baseSaturation=0.7f;
			bloomIntesity=0.8f;
			bloomSaturation=0.2f;
			threshold=0f;
			GameViews.vignette.setEnabled(true);
		}else if(type==Weather.no){
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
	
	public BaseScriptExecutor setWeather(final Script script,final Weather t){
		return script.set((BaseScriptExecutor) new BaseScriptExecutor() {
			@Override
			public void init() {
				WeatherUtil.this.init(t);
			}
		});
	}
}
