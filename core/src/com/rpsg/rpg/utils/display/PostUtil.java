package com.rpsg.rpg.utils.display;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.GdxFrame;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.CollideType;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.InputController;
import com.rpsg.rpg.system.controller.MoveController;
import com.rpsg.rpg.system.ui.ImageButton;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.view.GameView;
import com.rpsg.rpg.view.GameViews;

public class PostUtil {
	
	public static Stage stage;
	static int showSpeed=8;
	static Touchpad pad;
	static double p4=Math.PI/4;
	static GdxQuery others;
	static boolean VZPress=false;
	public static boolean showMenu=true;
	public static boolean isVZPress(){
		return VZPress;//?!(VZPress=false):false;
	}
	public static boolean setVZPress(boolean v){
		return VZPress=v;
	}
	public static boolean first=true; 
	
	public static void init(){
		first=false;
		stage=new Stage(new ScalingViewport(Scaling.stretch, 1024, 576, new OrthographicCamera()),GameViews.gameview.stage.getBatch());
		
		others=$.add($.add(new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"menu.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"menu_active.png")).pos(GameUtil.screen_width-65, 15).onClick(new Runnable() {public void run() {
			GameViews.gameview.onkeyDown(Keys.ESCAPE);
		}})).appendTo(stage).setUserObject("menu"));
		TouchpadStyle tstyle=new TouchpadStyle();
		tstyle.background=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"pad_bg.png");
		System.out.println(tstyle.background.getMinWidth());
		tstyle.knob=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"pad_knob.png");
		others.add($.add(pad=new Touchpad(0, tstyle)).setPosition(35, 25).setVisible(!GameUtil.isDesktop));
		others.add($.add(new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_NEW_GLOBAL+"button_a.png"), Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_NEW_GLOBAL+"button_a_p.png"))).setPosition(830, 65).setVisible(!GameUtil.isDesktop).addListener(new InputListener(){
			public void touchUp (InputEvent event, float x, float y, int pointer, int b) {
				VZPress=false;
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int b) {
				return VZPress=true;
			}
		}));
		others.appendTo(stage);
		Logger.info("Post特效创建成功。");
	}
	public static void draw( boolean menuEnable){
		if(showMenu){
			others.not("menu").setVisible(Setting.persistence.touchMod);
			if(Setting.persistence.touchMod && GameViews.gameview.stackView==null){
				float x=pad.getKnobPercentX();
				float y=pad.getKnobPercentY();
				double tan=Math.atan2(y,x);
				if(tan<p4*3 && tan > p4)
					MoveController.up();
				else if(tan>p4*3 || (tan < -p4*3 && tan < 0))
					MoveController.left();
				else if(tan>-p4*3 && tan <-p4)
					MoveController.down();
				else if((tan>-p4 && tan <0) || (tan>0 && tan < p4))
					MoveController.right();
			}
			others.cleanActions();
			for(Actor actor:others.getItems())
				actor.addAction(GameViews.gameview.stackView==null?Actions.fadeIn(0.1f):Actions.fadeOut(0.1f));
			stage.act();
			if(menuEnable)
				stage.draw();
		}
	}
	
	public static boolean mouseMoved(int x,int y){
		return stage.mouseMoved(x, y);
	}
	
	public static boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return showMenu?stage.touchUp(screenX, screenY, pointer, button):false;
	}
	
	public static boolean touchDragged(int screenX, int screenY, int pointer) {
		return showMenu?stage.touchDragged(screenX, screenY, pointer):false;
	}
	
	public static boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return showMenu?stage.touchDown(screenX, screenY, pointer, button):false;
	}

	public static void keyTyped(char c) {
	}
	
	public static BaseScriptExecutor showMenu(final Script script,final boolean show){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				showMenu=show;
			}
		});
	}
}
