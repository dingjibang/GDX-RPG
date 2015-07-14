package com.rpsg.rpg.utils.display;

import javafx.scene.input.KeyCode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.InputController;
import com.rpsg.rpg.system.controller.MoveController;
import com.rpsg.rpg.system.ui.ImageButton;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.view.GameViews;

public class PostUtil {
	
	public static Stage stage;
	static int height=0,maxHeight=160;
	static int showSpeed=8;
	static Touchpad pad;
	static double p4=Math.PI/4;
	static GdxQuery others;
	public static void init(){
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
		
		others=$.add(new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"menu.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"menu_active.png")).pos(GameUtil.screen_width-65, 15).onClick(new Runnable() {
			@Override
			public void run() {
				GameViews.gameview.onkeyDown(Keys.ESCAPE);
			}
		}));
		
		TouchpadStyle tstyle=new TouchpadStyle();
		tstyle.background=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"pad_bg.png");
		tstyle.knob=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"pad_knob.png");
		pad=new Touchpad(0, tstyle);
		pad.setPosition(35, 25);
		stage.addActor(pad);
		if(!GameUtil.isDesktop)
			pad.setVisible(false);
		others.appendTo(stage);
		
		GdxQuery q=$.image(Setting.GAME_RES_IMAGE_GLOBAL+"pad_bg.png").setPosition(750, 25).appendTo(stage);
		
		q.onClick(new Runnable() {
			public void run() {			
				GameViews.input.keyDown(Keys.Z);
			}
		});		
		if(!GameUtil.isDesktop)
			q.setVisible(false);
		
		Logger.info("Post特效创建成功。");
		
		
	}
	
	public static void draw( boolean menuEnable){
		pad.setVisible(Setting.persistence.touchMod  && height<=0);
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
		stage.draw();
		
	}
	
	public static boolean mouseMoved(int x,int y){
		return stage.mouseMoved(x, y);
	}
	
	public static boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return stage.touchUp(screenX, screenY, pointer, button);
	}
	
	public static boolean touchDragged(int screenX, int screenY, int pointer) {
		return stage.touchDragged(screenX, screenY, pointer);
	}
	
	public static boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return stage.touchDown(screenX, screenY, pointer, button);
	}

	public static void keyTyped(char c) {
	}
}
