package com.rpsg.rpg.utils.display;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color; 
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Bloom;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.SL;
import com.rpsg.rpg.object.base.ObjectRunnable;
import com.rpsg.rpg.object.base.SLData;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.HeroController;
import com.rpsg.rpg.system.controller.HoverController;
import com.rpsg.rpg.system.controller.InputController;
import com.rpsg.rpg.system.controller.MoveController;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.ImageButton;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.utils.game.TimeUtil;
import com.rpsg.rpg.view.GameViews;
import com.rpsg.rpg.view.hover.ConfirmView;
import com.rpsg.rpg.view.hover.LoadView;

public class PostUtil {
	
	public static Stage stage;
	static int height=0,maxHeight=160;
	static boolean display=false;
	static int showSpeed=8;
	static Touchpad pad;
	static WidgetGroup group;
	static double p4=Math.PI/4;
	static Label day,gameTime,mapName,gold,task,tasklist;
	public static void init(){
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
		
		stage.addActor(new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"menu.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"menu_active.png")).pos(GameUtil.screen_width-65, 15).onClick(new Runnable() {
			@Override
			public void run() {
				keyTyped(' ');
			}
		}));
		
		TouchpadStyle tstyle=new TouchpadStyle();
		tstyle.background=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"pad_bg.png");
		tstyle.knob=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"pad_knob.png");
		pad=new Touchpad(0, tstyle);
		pad.setPosition(35, 25);
		stage.addActor(pad);
		pad.setVisible(Setting.persistence.touchMod);
		
		group=new WidgetGroup();
		$.add(Res.get(Setting.GAME_RES_IMAGE_GLOBAL+"menu_leftbar.png").position(522, 0)).appendTo(group);
		$.add(day=new Label("",24).setWidth(1000)).setPosition(675, 558).appendTo(group);
		$.add(gameTime=new Label("",18).setWidth(1000)).setPosition(680,525).appendTo(group);
		$.add(mapName=new Label("",24).setWidth(1000)).setPosition(680, 377).appendTo(group);
		$.add(gold=new Label("",24).setWidth(1000)).setPosition(680,322).appendTo(group);
		$.add(task=new Label("",24).setWidth(1000)).setPosition(680, 275).appendTo(group);
		$.add(tasklist=new Label("",18).setWidth(1000)).setPosition(686, 245).appendTo(group);
		
		stage.addActor(group);
		
		
		
		Logger.info("Post特效创建成功。");
	}
	
	public static void draw( boolean menuEnable){
		day.setText(GameViews.global.tyear+"年"+GameViews.global.tmonth+"月"+GameViews.global.tday+"日");
		gameTime.setText("游戏已进行"+TimeUtil.getGameRunningTime());
		mapName.setText((String)GameViews.gameview.map.getProperties().get("name")+"["+HeroController.getHeadHero().mapx+","+HeroController.getHeadHero().mapy+"]");
		gold.setText("持有"+GameViews.global.gold+"金币");
		task.setText("任务模块制作中");
		tasklist.setText("任务模块制作中");
//		pad.setVisible(Setting.persistence.touchMod  && height<=0);
//		if(Setting.persistence.touchMod){
//			float x=pad.getKnobPercentX();
//			float y=pad.getKnobPercentY();
//			double tan=Math.atan2(y,x);
//			if(tan<p4*3 && tan > p4)
//				MoveController.up();
//			else if(tan>p4*3 || (tan < -p4*3 && tan < 0))
//				MoveController.left();
//			else if(tan>-p4*3 && tan <-p4)
//				MoveController.down();
//			else if((tan>-p4 && tan <0) || (tan>0 && tan < p4))
//				MoveController.right();
//		}
		
		stage.act();
		stage.draw();
		
		
		
		if(display && group.getActions().size==0)
			group.addAction(Actions.moveTo(0,0,0.2f,Interpolation.pow3));
		else if(group.getActions().size==0)
			group.addAction(Actions.moveTo(600,0,0.1f,Interpolation.pow3));
	}
	
	public static float getGroupX(){
		return group.getX()/600;
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
		if(c==' ' && (height==0 || height == maxHeight))
			display=!display;
	}
}
