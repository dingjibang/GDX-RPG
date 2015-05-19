package com.rpsg.rpg.view.menu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Music;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.HeroController;
import com.rpsg.rpg.system.ui.HeroImage;
import com.rpsg.rpg.system.ui.View;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.display.AlertUtil;
import com.rpsg.rpg.utils.display.ColorUtil;
import com.rpsg.rpg.utils.display.FontUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.view.GameViews;

public class MenuBaseView extends View{
	Stage stage;
	List<HeroImage> heros=new ArrayList<HeroImage>();
	Image map;
	ShapeRenderer render;
	public void init() {
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
		
		Image infobg=Res.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"info_bg.png");
		infobg.setPosition(GameUtil.screen_width, 360);
		infobg.setColor(1,1,1,0);
		infobg.addAction((Actions.delay(0.05f, Actions.parallel(Actions.fadeIn(0.2f),Actions.moveTo(470,360,0.1f)))));
		stage.addActor(infobg);
		
		Image infolevel=Res.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"info_level.png");
		infolevel.setPosition(521, 389);
		infolevel.setColor(1,1,1,0);
		infolevel.addAction((Actions.delay(0.05f, Actions.parallel(Actions.fadeIn(0.15f),Actions.moveTo(480.5f,388.5f,0.15f)))));
		stage.addActor(infolevel);
		Image fgshadow=Res.get(Setting.GAME_RES_IMAGE_FG+HeroController.getHeadHero().fgname+"/Normal.png");
		fgshadow.setPosition(900, 0);
		fgshadow.setColor(0,0,0,0f);
		fgshadow.scaleX(-0.32f).scaleY(0.32f);
		fgshadow.addAction(Actions.parallel(Actions.moveTo(1050, 0,0.15f),Actions.color(new Color(0,0,0,.5f),0.5f)));
		stage.addActor(fgshadow);
		
		Image map_bg=Res.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"map_bg.png");
		map_bg.setPosition(150,30);
		map_bg.setColor(1,1,1,0);
		map_bg.addAction(Actions.fadeIn(0.3f));
		stage.addActor(map_bg);
		
		Image fg=Res.get(Setting.GAME_RES_IMAGE_FG+HeroController.getHeadHero().fgname+"/Normal.png");
		fg.setPosition(GameUtil.screen_width, 0);
		fg.setColor(1,1,1,0);
		fg.setScale(0.32f);
		fg.setScaleX(-0.32f);
		fg.addAction(Actions.parallel(Actions.fadeIn(0.2f),Actions.moveTo(1080, 0,0.2f)));
		stage.addActor(fg);
		
		Image walk_bg=Res.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"walk_bg.png");
		walk_bg.setPosition(155, 400);
		walk_bg.setColor(1,1,1,0);
		walk_bg.addAction(Actions.parallel(Actions.fadeIn(0.2f),Actions.moveTo(155,360,0.2f)));
		stage.addActor(walk_bg);
		
	
		int offset=0;
		for(Hero h:HeroController.heros){
			heros.add(HeroImage.generateImage(h.images, (int)(170+284/2-(h.getWidth())*HeroController.heros.size()+(offset+=60)), 410));
		}
//		if(GameViews.global.day==ColorUtil.DAY){
			time=Res.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"info_time_day.png");
//		}
		time.setPosition(740, 363);
		
		if(Gdx.files.internal(Setting.GAME_RES_IMAGE_MENU_MAP+GameViews.gameview.map.getProperties().get("minimap")+".png").exists()){
			map=Res.get(Setting.GAME_RES_IMAGE_MENU_MAP+GameViews.gameview.map.getProperties().get("minimap")+".png");
			map.setPosition(218, 46);
			map.setColor(1,1,1,0);
			map.setSize(400, 235);
			map.addAction(Actions.fadeIn(0.3f));
			stage.addActor(map);
		}else{
			AlertUtil.add("无法读取当前地图的缩略图！", AlertUtil.Red);
		}
		ImageButton exit=new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"exit.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"exitc.png"));
		exit.setPosition(960, 550);
		exit.addAction(Actions.moveTo(960, 510,0.1f));
		exit.addListener(new InputListener(){
			public void touchUp (InputEvent event, float x, float y, int pointer, int b) {
				GameViews.gameview.stackView.onkeyDown(Keys.ESCAPE);
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int b) {
				Music.playSE("snd210");
				return true;
			}
		});
		stage.addActor(exit);
		
		render=new ShapeRenderer();
		render.setAutoShapeType(true);
	}
	
	Color blue=new Color(80f/255f,111f/255f,187f/255f,1);
	Color green=new Color(115f/255f,1,83f/255f,1);
	Color yellow=new Color(232f/255f,202f/255f,12f/255f,1);
	int frame=0;
	int step=1;
	String currTime=GameViews.global.tyear+" 年 "+GameViews.global.tmonth+" 月 "+GameViews.global.tday+" 日";
	String currDay=GameViews.global.mapColor==ColorUtil.DAY?"昼":(GameViews.global.mapColor==ColorUtil.NIGHT?"夜":"暝");
	Image time;
	int deg=(int) (365f*((float)HeroController.getHeadHero().prop.get("exp")/(float)(float)HeroController.getHeadHero().prop.get("maxexp")));
	int stepdeg=0;
	
	int hp=80;
	int mp=100;
	
	int stephp=0;
	int stepmp=0;
	public void draw(SpriteBatch batch) {
		stage.draw();
		render.begin();
		render.set(ShapeType.Filled);
		render.setColor(blue);
		if(stepdeg<deg)
			stepdeg+=5;
		render.arc(522, 430, 39, 0, stepdeg,100);
		render.setColor(Color.WHITE);
		render.circle(522, 430, 37, 100);
		if(stephp<hp)
			stephp+=5;
		if(stepmp<mp)
			stepmp+=5;
		render.setColor(green);
		render.rect(570,410,((float)hp/100f)*190*((float)stephp/(float)hp),6);
		render.setColor(yellow);
		render.rect(570,394,((float)mp/100f)*190*((float)stepmp/(float)mp),6);
		render.end();
		FontUtil.draw(batch, HeroController.getHeadHero().jname, 13, Color.WHITE, 663, 470, 1000,-5,0);
		FontUtil.draw(batch, HeroController.getHeadHero().name, 28, Color.WHITE, 622, 450, 1000);
		FontUtil.draw(batch, HeroController.getHeadHero().prop.get("level")+"", 40, blue, 506-FontUtil.getTextWidth(HeroController.getHeadHero().prop.get("level")+"", 40, -10)/2, 454, 1000,-10,0);
		FontUtil.draw(batch, GameViews.global.gold+" G", 18, blue, 488+91/2-FontUtil.getTextWidth(GameViews.global.gold+" G", 18, -8), 379, 1000,-8,0);
		FontUtil.draw(batch, "状态正常", 17, Color.WHITE, 149+285/2-FontUtil.getTextWidth("状态正常", 17, 2)/2, 385, 1000,2,0);
		FontUtil.draw(batch, currTime, 17, blue, 558+144/2-FontUtil.getTextWidth(currTime, 17, -8)/2, 378, 1000,-8,0);
		FontUtil.draw(batch, currDay, 16, Color.GRAY, 714, 378, 1000,-7,0);
		FontUtil.draw(batch, "LV", 14, blue, 511, 413, 1000,-7,0);
		String mapName=(String)GameViews.gameview.map.getProperties().get("name");
		if(mapName==null)
			mapName="未知地图";
		FontUtil.draw(batch, mapName+" ["+HeroController.getHeadHero().mapx+","+HeroController.getHeadHero().mapy+"]", 20, Color.WHITE, 175, 317, 1000,0,0);
		time.draw(batch);
		for(HeroImage h:heros)
			h.draw(batch, step==3?1:step);
		batch.flush();
	}

	public void logic() {
		stage.act();
		if(++frame==30){
			frame=0;
			if(++step==4)
				step=0;
		}
	}

	public void onkeyTyped(char character) {
		
	}

	public void onkeyDown(int keyCode) {
		
	}

	public void onkeyUp(int keyCode) {
		
	}

	public void dispose() {
		render.dispose();
		stage.dispose();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return stage.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return stage.touchDragged(screenX, screenY, pointer);
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return stage.touchUp(screenX, screenY, pointer, button);
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}


}
