package com.rpsg.rpg.view.menu;

import java.util.Collections;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.GdxFrame;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.gdxQuery.GdxQueryRunnable;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.HeroController;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.system.ui.StackView;
import com.rpsg.rpg.system.ui.View;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.TimeUtil;
import com.rpsg.rpg.view.GameViews;

public class MenuView extends StackView{
	
	public static Stage stage;
	static GdxFrame frames;
	static WidgetGroup leftBar;
	@Override
	public void init() {
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
		$.add(leftBar=new WidgetGroup()).appendTo(stage).setPosition(-500, 0).addAction(Actions.moveTo(0, 0,0.25f));
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_GLOBAL+"bg.png")).setHeight(1024).setPosition(0, 0).appendTo(leftBar);
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_GLOBAL+"ico_pos.png")).setPosition(0, 350).appendTo(leftBar).addAction(Actions.moveTo(35, 350,0.4f));
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_GLOBAL+"ico_gold.png")).setPosition(0, 295).appendTo(leftBar).addAction(Actions.moveTo(35, 295,0.4f));
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_GLOBAL+"ico_flag.png")).setPosition(0, 232).appendTo(leftBar).addAction(Actions.moveTo(35, 232,0.4f));
		frames=$.add($.add(new Label("",24).setWidth(1000).right(true).setPos(380, 558)).appendTo(leftBar).setColor(1,1,1,0).addAction(Actions.fadeIn(0.3f)),new GdxQueryRunnable() {public void run(GdxQuery self) {
			((Label)self.getItem()).setText(GameViews.global.tyear+"年"+GameViews.global.tmonth+"月"+GameViews.global.tday+"日");
		}});
		frames.add($.add(new Label("",18).setWidth(1000).right(true).setPos(383, 525)).appendTo(leftBar).setColor(1,1,1,0).addAction(Actions.fadeIn(0.3f)),new GdxQueryRunnable() {public void run(GdxQuery self) {
			((Label)self.getItem()).setText("游戏已进行"+TimeUtil.getGameRunningTime());
		}});
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_GLOBAL+"hr.png")).setPosition(-200, 490).appendTo(leftBar).setColor(1,1,1,0).addAction(Actions.delay(0.2f, Actions.parallel(Actions.fadeIn(0.1f),Actions.moveTo(20, 490,0.1f))));
		frames.add($.add(new Label("",24).setWidth(1000)).setPosition(75, 377).appendTo(leftBar).setColor(1,1,1,0).addAction(Actions.fadeIn(0.3f)),new GdxQueryRunnable() {public void run(GdxQuery self) {
			((Label)self.getItem()).setText((String)GameViews.gameview.map.getProperties().get("name")+"["+HeroController.getHeadHero().mapx+","+HeroController.getHeadHero().mapy+"]");
		}});
		frames.add($.add(new Label("",24).setWidth(1000)).setPosition(75,322).appendTo(leftBar).setColor(1,1,1,0).addAction(Actions.fadeIn(0.3f)),new GdxQueryRunnable() {public void run(GdxQuery self) {
			((Label)self.getItem()).setText("持有"+GameViews.global.gold+"金币");
		}});
		frames.add($.add(new Label("",24).setWidth(1000)).setPosition(75, 265).appendTo(leftBar).setColor(1,1,1,0).addAction(Actions.fadeIn(0.3f)),new GdxQueryRunnable() {public void run(GdxQuery self) {
			((Label)self.getItem()).setText("任务模块制作中");
		}});
		frames.add($.add(new Label("",16).setWidth(1000)).setPosition(80, 235).appendTo(leftBar).setColor(1,1,1,0).addAction(Actions.fadeIn(0.3f)),new GdxQueryRunnable() {public void run(GdxQuery self) {
			((Label)self.getItem()).setText("任务模块制作中");
		}});
		
//		frames.add($.add(new Label("",24).setWidth(1000)).setPosition(675, 558).appendTo(stage),new GdxQueryRunnable() {public void run(GdxQuery self) {
//			((Label)self.getItem()).setText(GameViews.global.tyear+"年"+GameViews.global.tmonth+"月"+GameViews.global.tday+"日");
//		}});
	}

	@Override
	public void draw(SpriteBatch batch) {
		frames.logic();
		stage.act();
		stage.draw();
		for(View view:viewStack)
			view.draw(batch);
	}

	@Override
	public void logic() {
		
	}

	public void onkeyTyped(char character) {
		if(viewStack.size()!=0)
			viewStack.get(viewStack.size()-1).onkeyTyped(character);
	}

	public void onkeyDown(int keyCode) {
		if(/** viewStack.size()==1 && **/(Keys.ESCAPE==keyCode || keyCode==Keys.X)){
			this.dispose();
			com.rpsg.rpg.system.controller.InputController.currentIOMode=IOMode.MAP_INPUT_NORMAL;
			GameViews.gameview.stackView=null;
		}else{
			if(viewStack.size()!=0)
				viewStack.get(viewStack.size()-1).onkeyDown(keyCode);
		}
	}
	
	

	public void onkeyUp(int keyCode) {
		if(viewStack.size()!=0)
			viewStack.get(viewStack.size()-1).onkeyUp(keyCode);
	}

	public void dispose() {
		stage.dispose();
		for(View view:viewStack)
			view.dispose();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		stage.touchDown(screenX, screenY, pointer, button);
		if(viewStack.size()!=0)
			viewStack.get(viewStack.size()-1).touchDown(screenX, screenY, pointer, button);
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		stage.touchUp(screenX, screenY, pointer, button);
		if(viewStack.size()!=0)
			viewStack.get(viewStack.size()-1).touchUp(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		stage.touchDragged(screenX, screenY, pointer);
		if(viewStack.size()!=0)
			viewStack.get(viewStack.size()-1).touchDragged(screenX, screenY, pointer);
		return false;
	}
	
	public void tryToAdd(Class<? extends View> iv){
		boolean inc=false;
		for(int i=0;i<viewStack.size();i++){
			Class<? extends View> view=viewStack.get(i).getClass();
			if(view.equals(iv)){
				Collections.swap(viewStack, i, viewStack.size()-1);
				inc=true;
				break;
			}
		}
		if(!inc)
			try {
				viewStack.add(iv.newInstance());
				viewStack.get(viewStack.size()-1).init();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
	}

	@Override
	public boolean scrolled(int amount) {
		stage.scrolled(amount);
		if(viewStack.size()!=0)
			viewStack.get(viewStack.size()-1).scrolled(amount);
		return false;
	}
	
	@Override
	public void disposes() {
		for(int size=GameViews.gameview.stackView.viewStack.size();size>0;size--){
			onkeyDown(Keys.ESCAPE);
			if(viewStack.size()!=1)
				viewStack.remove(viewStack.size()-1);
		}
	}

}
