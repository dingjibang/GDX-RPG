package com.rpsg.rpg.view.menu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpgobj.Hero;
import com.rpsg.rpg.system.base.HeroImage;
import com.rpsg.rpg.system.base.IView;
import com.rpsg.rpg.system.base.Image;
import com.rpsg.rpg.system.base.ResourcePool;
import com.rpsg.rpg.system.control.HeroControler;
import com.rpsg.rpg.utils.display.ColorUtil;
import com.rpsg.rpg.utils.display.FontUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.view.GameViews;

public class MenuBaseView extends IView{
	Stage stage;
	List<HeroImage> heros=new ArrayList<HeroImage>();
	Image map;
	public void init() {
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
		Image bg=ResourcePool.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"bg.png");
		bg.setPosition(50, 0);
		bg.setColor(1,1,1,0);
		bg.addAction(Actions.fadeIn(0.2f));
		stage.addActor(bg);
		Image magic=ResourcePool.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"bg_magic.png");
		magic.setPosition(600, 150);
		magic.setColor(1,1,1,0.15f);
		magic.setOrigin(0);
		magic.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.rotateBy(0.1f)));
		stage.addActor(magic);
		
		Image infobg=ResourcePool.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"info_bg.png");
		infobg.setPosition(GameUtil.screen_width, 360);
		infobg.setColor(1,1,1,0);
		infobg.addAction((Actions.delay(0.05f, Actions.parallel(Actions.fadeIn(0.2f),Actions.moveTo(470,360,0.1f)))));
		stage.addActor(infobg);
		
		Image infolevel=ResourcePool.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"info_level.png");
		infolevel.setPosition(520, 389);
		infolevel.setColor(1,1,1,0);
		infolevel.addAction((Actions.delay(0.05f, Actions.parallel(Actions.fadeIn(0.15f),Actions.moveTo(480,389,0.15f)))));
		stage.addActor(infolevel);
		
		Image fgshadow=ResourcePool.get(Setting.GAME_RES_IMAGE_MENU_FG+"arisu_shadow.png");
		fgshadow.setPosition(GameUtil.screen_width, 0);
		fgshadow.setColor(1,1,1,0);
		fgshadow.addAction(Actions.parallel(Actions.fadeIn(0.2f),Actions.moveTo(710, 0,0.2f)));
		stage.addActor(fgshadow);
		
		Image fg=ResourcePool.get(Setting.GAME_RES_IMAGE_MENU_FG+"arisu.png");
		fg.setPosition(GameUtil.screen_width, 0);
		fg.setColor(1,1,1,0);
		fg.addAction(Actions.parallel(Actions.fadeIn(0.1f),Actions.moveTo(730, 0,0.2f)));
		stage.addActor(fg);
		
		Image walk_bg=ResourcePool.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"walk_bg.png");
		walk_bg.setPosition(155, 400);
		walk_bg.setColor(1,1,1,0);
		walk_bg.addAction(Actions.parallel(Actions.fadeIn(0.2f),Actions.moveTo(155,360,0.2f)));
		stage.addActor(walk_bg);
		
		Image map_bg=ResourcePool.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"map_bg.png");
		map_bg.setPosition(155,30);
		map_bg.setColor(1,1,1,0);
		map_bg.addAction(Actions.fadeIn(0.3f));
		stage.addActor(map_bg);
		int offset=0;
		for(Hero h:HeroControler.heros){
			heros.add(HeroImage.generateImage(h.images, (int)(170+284/2-(h.getWidth())*HeroControler.heros.size()+(offset+=60)), 410));
		}
//		if(GameViews.global.day==ColorUtil.DAY){
			time=ResourcePool.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"info_time_day.png");
//		}
		time.setPosition(725, 363);
		
		map=ResourcePool.get(Setting.GAME_RES_IMAGE_MENU_MAP+GameViews.gameview.map.getProperties().get("minimap")+".png");
		map.setPosition(230, 47);
		map.setColor(1,1,1,0);
		map.addAction(Actions.fadeIn(0.3f));
		stage.addActor(map);
		
		ScrollPane pane=new ScrollPane(ResourcePool.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"leftbar_bg.png"));
		pane.setPosition(0, 0);
		pane.setScrollingDisabled(false, true);
		pane.setHeight(GameUtil.screen_height);
		pane.setSmoothScrolling(true);
		pane.addListener(new InputListener() {
			public void touchDragged (InputEvent event, float x, float y, int pointer) {
				System.out.println("???");
				pane.setScrollX(40);
//				return false;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer,int botton) {
//				return false;
			}
			
			public boolean touchDown (InputEvent event, float x, float y, int pointer,int botton) {
				return true;
			}
		});
		ImageButton button =new ImageButton(ResourcePool.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"lbut_equip.png").getDrawable());
		pane.setWidget(button);
		stage.addActor(pane);
		
	}
	Color blue=new Color(80f/255f,111f/255f,187f/255f,1);
	int frame=0;
	int step=0;
	String currTime=GameViews.global.tyear+"年 "+GameViews.global.tmonth+"月 "+GameViews.global.tday+"日";
	String currDay=GameViews.global.day==ColorUtil.DAY?"昼":(GameViews.global.day==ColorUtil.NIGHT?"夜":"暝");
	Image time;
	public void draw(SpriteBatch batch) {
		stage.draw();
		FontUtil.draw(batch, "Yuuki Arisu", 13, Color.WHITE, 663, 470, 1000,-5,0);
		FontUtil.draw(batch, "结城有栖", 28, Color.WHITE, 610, 450, 1000);
		FontUtil.draw(batch, GameViews.global.level+"", 40, blue, 443+85/2-FontUtil.getTextWidth(GameViews.global.level+"", 40, -10)/2, 450, 1000,-10,0);
		FontUtil.draw(batch, GameViews.global.gold+" G", 18, blue, 478, 379, 1000,-8,0);
		FontUtil.draw(batch, "状态正常", 17, Color.WHITE, 144+285/2-FontUtil.getTextWidth("状态正常", 17, 2)/2, 385, 1000,2,0);
		FontUtil.draw(batch, currTime, 16, blue, 510+144/2-FontUtil.getTextWidth(currTime, 16, -7)/2, 378, 1000,-7,0);
		FontUtil.draw(batch, currDay, 16, Color.GRAY, 692, 378, 1000,-7,0);
		FontUtil.draw(batch, (String)GameViews.gameview.map.getProperties().get("name")+" ["+HeroControler.getHeadHero().mapx+","+HeroControler.getHeadHero().mapy+"]", 16, Color.WHITE, 180, 320, 1000,0,0);
		time.draw(batch);
		for(HeroImage h:heros)
			h.draw(batch, step==3?1:step);
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


}
