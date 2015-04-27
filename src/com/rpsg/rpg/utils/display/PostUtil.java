package com.rpsg.rpg.utils.display;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Bloom;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.SL;
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
import com.rpsg.rpg.view.GameViews;
import com.rpsg.rpg.view.hover.ConfirmView;
import com.rpsg.rpg.view.hover.LoadView;

public class PostUtil {
	
	static Stage stage;
	static Label name,y,m,d,yy,mm,day,map,money,jname,level,next;
	static int height=0,maxHeight=160;
	static boolean display=false;
	static int showSpeed=8;
	static WidgetGroup group;
	static Touchpad pad;
	static double p4=Math.PI/4;
	public static void init(){
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
		
		TouchpadStyle tstyle=new TouchpadStyle();
		tstyle.background=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"pad_bg.png");
		tstyle.knob=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"pad_knob.png");
		pad=new Touchpad(0, tstyle);
		pad.setPosition(35, 25);
		stage.addActor(pad);
		pad.setVisible(Setting.persistence.touchMod);
		
		group=new WidgetGroup();
		group.addActor(name=new Label("", 42).setWidth(1000));
		group.addActor(y=new Label("", 42).setWidth(1000));
		group.addActor(m=new Label("", 42).setWidth(1000));
		group.addActor(d=new Label("", 42).setWidth(1000));
		group.addActor(yy=new Label("年", 42).setWidth(1000));
		group.addActor(mm=new Label("月", 24).setWidth(1000));
		group.addActor(day=new Label("", 42).setWidth(1000));
		group.addActor(map=new Label("", 32).setWidth(1000));
		group.addActor(money=new Label("", 42).setWidth(1000));
		group.addActor(jname=new Label("", 20).setWidth(1000));
		group.addActor(level=new Label("", 42).setWidth(1000));
		group.addActor(next=new Label("", 42).setWidth(1000));
		
		stage.addActor(group);
		
		name.setPos(97, 132).setColor(1,1,1,0.85f);
		jname.setPos(120, 100).setPad(-4).setColor(1,1,1,0.2f);
		level.setPos(200,105).setPad(-15).setColor(1,1,1,0.6f);
		next.setPos(100,77).setPad(-15).setColor(1,1,1,0.3f);
		next.setZIndex(1);
		y.setPos(350,115).setPad(-13).setColor(1,1,1,0.85f);
		day.setPos(460,135).setColor(1,1,1,0.3f);
		yy.setPos(410,80).setColor(1,1,1,0.25f);
		m.setPos(465,90).setPad(-13).setColor(1,1,1,0.85f);
		mm.setPos(495,75).setColor(1,1,1,0.2f);
		d.setPos(515,90).setPad(-13).setColor(1,1,1,0.85f);
		map.setPos(435,40).setColor(1,1,1,0.7f);
		money.setPos(140, 40).setPad(-13).setColor(1,1,1,0.85f);
		
		group.getChildren().forEach((a)->{
			if(a.getClass().equals(Label.class)){
				Label l=(Label)a;
				l.setWidth((float)(l.getText().length()*l.fontSize*0.85f));
				l.setHeight(l.fontSize);
				l.setUserObject(new Color(l.getColor()));
				l.setScaleY(-1);
				l.addListener(new InputListener(){
					public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
						for(Actor actor:group.getChildren())
							if(actor!=l && actor.getUserObject()!=null)
								actor.setColor(1,1,1,0.1f);
						l.setColor(1,1,1,1);
					}
					public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
						for(Actor actor:group.getChildren())
							if(actor.getUserObject()!=null)
								actor.setColor((Color)actor.getUserObject());
					}
				});
			}
		});
		
		group.addActor(new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"fast_menu.png")).pos(630, 27).onClick(()->{
			InputController.keyDown(Keys.ESCAPE, GameViews.gameview);
		}));
		group.addActor(new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"fast_save.png")).pos(730, 27).onClick(()->{
			Pixmap px=ScreenUtil.getScreenshot(0, 0, GameUtil.getScreenWidth(), GameUtil.getScreenHeight(), false);
			HoverController.add(ConfirmView.getDefault("确定要快速存档么？", (view)->{
				SL.save((Setting.GAME_SAVE_FILE_MAX_PAGE-2)*4,px,null);
				((HoverView)view).disposed=true;
			}).setExitCallBack(()->{
				px.dispose();
			}));
		}));
		group.addActor(new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"fast_read.png")).pos(830, 27).onClick(()->{
			LoadView save=new LoadView();
			save.superInit();
			HoverController.add(save);
			save.autobut.click();
			for(Actor a:save.stage.getActors())
				if(a.getUserObject()!=null && a.getUserObject().getClass().equals(SLData.exMask.class)){
					((Image)a).click();
					break;
				}
			save.savebutton.click();
		}));
		
		stage.addActor(new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"menu.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"menu_active.png")).pos(GameUtil.screen_width-65, 15).onClick(()->{
			keyTyped(' ');
		}));
		
		Logger.info("Post特效创建成功。");
	}
	
	static int a=0;
	public static void draw(PostProcessor post, boolean menuEnable){
		name.setText(HeroController.getHeadHero().name);
		jname.setText(HeroController.getHeadHero().jname);
		level.setText("LV "+HeroController.getHeadHero().prop.get("level"));
		next.setText("NEXT "+(HeroController.getHeadHero().prop.get("maxexp")-HeroController.getHeadHero().prop.get("exp"))+" Exp");
		y.setText(GameViews.global.tyear+"");
		day.setText(GameViews.global.mapColor==ColorUtil.DAY?"白天":(GameViews.global.mapColor==ColorUtil.NIGHT?"夜晚":"黄昏"));
		m.setText(GameViews.global.tmonth+"");
		d.setText(GameViews.global.tday+"");
		map.setText(GameViews.gameview.map.getProperties().get("name")+"");
		money.setText("GOLD "+GameViews.global.gold);
		
		group.getChildren().forEach((a)->{
			if(a.getClass().equals(Label.class)){
				Label l=(Label)a;
				l.setWidth((float)(l.getText().length()*l.fontSize*0.85f));
				l.setHeight(l.fontSize);
			}
		});
		
		if(display && height <maxHeight)
			height=(height+showSpeed+(++a)>= maxHeight)?maxHeight:height+showSpeed+a;
		else if(!display && height > 0)
			height=(height-showSpeed-(--a)<= 0)?0:height-showSpeed-a;
		group.setY(height-maxHeight);
		
		FrameBuffer buffer = null;
		if(height>0 && menuEnable && Setting.persistence.betterLight)
			buffer=new FrameBuffer(Format.RGB565, 1024, 576, true);
			
		Bloom bloom=GameViews.bloom;
		bloom.setBaseIntesity(0);
		bloom.setBloomIntesity(0.75f);
		bloom.setBloomSaturation(0.8f);
		bloom.setThreshold(0.1f);
		
		if(height>0 && menuEnable && Setting.persistence.betterLight)
			post.render(buffer,Bloom.class);
		
		bloom.setBaseIntesity(1.2f);
		bloom.setBaseSaturation(1f);
		bloom.setBloomIntesity(0.7f);
		bloom.setBloomSaturation(1.2f);
		bloom.setThreshold(0.3f);
		if(height>0 && menuEnable){
			stage.getBatch().begin();
			if(!Setting.persistence.betterLight)
				stage.getBatch().setColor(0f,0f,0f,0.7f);
			if(Setting.persistence.betterLight)
				stage.getBatch().draw(new TextureRegion(buffer.getColorBufferTexture(),73,height,878,-height),73,0);
			else
				stage.getBatch().draw(Res.getTexture(Setting.GAME_RES_IMAGE_GLOBAL+"optb.png"),73,0,878,height);
			stage.getBatch().end();
		}
		
		stage.act();
		stage.draw();
		
		if(height>0  && menuEnable && Setting.persistence.betterLight)
			buffer.dispose();
		pad.setVisible(Setting.persistence.touchMod  && height<=0);
		if(Setting.persistence.touchMod){
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
