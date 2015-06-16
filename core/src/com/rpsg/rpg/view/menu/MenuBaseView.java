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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
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
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"info_bg.png")).setPosition(GameUtil.screen_width, 360).fadeOut().addAction((Actions.delay(0.05f, Actions.parallel(Actions.fadeIn(0.2f),Actions.moveTo(470,360,0.1f))))).appendTo(stage);
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL + "info_level.png")).setPosition(521, 389).fadeOut().addAction((Actions.delay(0.05f, Actions.parallel(Actions.fadeIn(0.15f),Actions.moveTo(480.5f,388.5f,0.15f))))).appendTo(stage);
		$.add(Res.get(Setting.GAME_RES_IMAGE_FG + HeroController.getHeadHero().fgname + "/Normal.png")).setPosition(900, 0).setScaleX(-0.32f).setScaleY(0.32f).setColor(0,0,0,0).addAction(Actions.parallel(Actions.moveTo(1050, 0, 0.15f), Actions.color(new Color(0, 0, 0, .5f), 0.5f))).setOrigin(Align.bottomLeft).appendTo(stage);
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"map_bg.png")).setPosition(150,30).setColor(1,1,1,0).fadeIn(0.3f).appendTo(stage);
		$.add(Res.get(Setting.GAME_RES_IMAGE_FG+HeroController.getHeadHero().fgname+"/Normal.png")).setPosition(GameUtil.screen_width, 0).fadeOut().setScale(0.32f).setScaleX(-0.32f).addAction(Actions.parallel(Actions.fadeIn(0.2f),Actions.moveTo(1080, 0,0.2f))).setOrigin(Align.bottomLeft).appendTo(stage);
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"walk_bg.png")).setPosition(155, 400).fadeOut().addAction(Actions.parallel(Actions.fadeIn(0.2f),Actions.moveTo(155,360,0.2f))).appendTo(stage);
		
		int offset=0;
		for(Hero h:HeroController.heros)
			heros.add(HeroImage.generateImage(h.images, (int)(170+284/2-(h.getWidth())*HeroController.heros.size()+(offset+=60)), 410));
		
//		if(GameViews.global.day==ColorUtil.DAY){
			time=Res.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"info_time_day.png");
//		}
			
		time.setPosition(740, 363);
		String miniMapFilePath=Setting.GAME_RES_IMAGE_MENU_MAP+GameViews.gameview.map.getProperties().get("minimap")+".png";
		if(Gdx.files.internal(miniMapFilePath).exists())
			$.add(Res.get(miniMapFilePath)).setPosition(218, 46).setColor(1,1,1,0).setSize(400, 235).addAction(Actions.fadeIn(0.3f)).appendTo(stage);
		else
			AlertUtil.add("无法读取当前地图的缩略图！", AlertUtil.Red);
		
		$.add(new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"exit.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"exitc.png"))).setPosition(960, 550).fadeOut().addAction(Actions.parallel(Actions.fadeIn(0.2f),Actions.moveTo(960, 510,0.1f))).onClick(new Runnable() {
			@Override
			public void run() {
				Music.playSE("snd210");
				for (int size = GameViews.gameview.stackView.viewStack.size(); size > 0; size--)
					GameViews.gameview.stackView.onkeyDown(Keys.ESCAPE);
			}
		}).appendTo(stage);
		
		$.add(new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"min.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"minc.png"))).setPosition(910, 550).fadeOut().addAction(Actions.parallel(Actions.fadeIn(0.2f),Actions.moveTo(910, 510,0.1f))).onClick(new Runnable() {
			@Override
			public void run() {
				GameViews.gameview.stackView.onkeyDown(Keys.ESCAPE);
				Music.playSE("snd210");
			}
		}).appendTo(stage);
		
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
	public void draw(SpriteBatch sb) {
		stage.draw();
		render.setTransformMatrix(stage.getBatch().getTransformMatrix());
		render.setProjectionMatrix(stage.getBatch().getProjectionMatrix());
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
		render.rect(570, 410, ((float) hp / 100f) * 190 * ((float) stephp / (float) hp), 6);
		render.setColor(yellow);
		render.rect(570, 394, ((float) mp / 100f) * 190 * ((float) stepmp / (float) mp), 6);
		render.end();
		SpriteBatch batch= (SpriteBatch) stage.getBatch();
		batch.begin();
		FontUtil.draw(batch, HeroController.getHeadHero().jname, 13, Color.WHITE, 663, 470, 1000,-5,0);
		FontUtil.draw(batch, HeroController.getHeadHero().name, 28, Color.WHITE, 622, 450, 1000);
		FontUtil.draw(batch, HeroController.getHeadHero().prop.get("level") + "", 40, blue, 506 - FontUtil.getTextWidth(HeroController.getHeadHero().prop.get("level") + "", 40, -10) / 2, 454, 1000, -10, 0);
		FontUtil.draw(batch, GameViews.global.gold + " G", 18, blue, 488 + 91 / 2 - FontUtil.getTextWidth(GameViews.global.gold + " G", 18, -8), 379, 1000, -8, 0);
		FontUtil.draw(batch, "状态正常", 17, Color.WHITE, 149+285/2-FontUtil.getTextWidth("状态正常", 17, 2) / 2, 385, 1000, 2, 0);
		FontUtil.draw(batch, currTime, 17, blue, 558+144/2-FontUtil.getTextWidth(currTime, 17, -8) / 2, 378, 1000, -8, 0);
		FontUtil.draw(batch, currDay, 16, Color.GRAY, 714, 378, 1000, -7,0);
		FontUtil.draw(batch, "LV", 14, blue, 511, 413, 1000,-7,0);
		String mapName=(String)GameViews.gameview.map.getProperties().get("name");
		if(mapName==null)
			mapName="未知地图";
		FontUtil.draw(batch, mapName+" ["+HeroController.getHeadHero().mapx+","+HeroController.getHeadHero().mapy+"]", 20, Color.WHITE, 175, 317, 1000,0,0);
		time.draw(batch);
		for(HeroImage h:heros)
			h.draw(batch, step==3?1:step);
		batch.flush();
		batch.end();
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
