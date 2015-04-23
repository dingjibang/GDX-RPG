package com.rpsg.rpg.view.menu;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Music;
import com.rpsg.rpg.object.base.Persistence;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.HeroController;
import com.rpsg.rpg.system.controller.HoverController;
import com.rpsg.rpg.system.ui.CheckBox;
import com.rpsg.rpg.system.ui.DefaultIView;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.system.ui.Slider;
import com.rpsg.rpg.system.ui.Slider.SliderStyle;
import com.rpsg.rpg.system.ui.TextButton;
import com.rpsg.rpg.system.ui.CheckBox.CheckBoxStyle;
import com.rpsg.rpg.system.ui.TextButton.TextButtonStyle;
import com.rpsg.rpg.utils.display.AlertUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.TimeUtil;
import com.rpsg.rpg.view.GameViews;
import com.rpsg.rpg.view.hover.ConfirmView;
import com.rpsg.rpg.view.hover.SaveView;

public class SystemView extends DefaultIView{
	Label lvl5,ttest;
	boolean isstop;
	public void init() {
		
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
		Table table = new Table();
		
		TextButtonStyle butstyle=new TextButtonStyle();
		butstyle.over=butstyle.checkedOver=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"button_hover.png");
		butstyle.down=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"button_active.png");
		butstyle.up=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"button.png");
		
		CheckBoxStyle cs=new CheckBoxStyle();
		cs.checkboxOff=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"optb_s.png");
		cs.checkboxOn=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"optb.png");
		
		SliderStyle slsty=new SliderStyle();
		slsty.background=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"sliderbar.png");
		slsty.knob=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"slider.png");
		
		WidgetGroup group=new WidgetGroup();
		group.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"savebar.png"));
		Label lvl=new Label("LV "+HeroController.getHeadHero().prop.get("level"),40).setWidth(1000).setPad(-15);
		lvl.setPosition(360, 190);
		group.addActor(lvl);
		Label lvl2=new Label("【"+(String)GameViews.gameview.map.getProperties().get("name")+"】",40).setWidth(1000).setPad(0);
		lvl2.setPosition(480, 190);
		group.addActor(lvl2);
		Label lvl3=new Label("档案所在位置：",18).setWidth(1000).setPad(0);
		lvl3.setPosition(370, 140);
		group.addActor(lvl3);
		Label lvl4=new Label(" ["+HeroController.getHeadHero().mapx+","+HeroController.getHeadHero().mapy+"]",18).setWidth(1000).setPad(0);
		lvl4.setPosition(480, 140);
		group.addActor(lvl4);
		Label lvl5h=new Label("档案进行时间：",18).setWidth(1000).setPad(0);
		lvl5h.setPosition(370, 110);
		group.addActor(lvl5h);
		lvl5=new Label(TimeUtil.getGameRunningTime(),18).setWidth(1000).setPad(-3);
		lvl5.setPosition(500, 110);
		group.addActor(lvl5);
		TextButton sbutton=new TextButton("保存游戏", butstyle).onClick(()->{
			HoverController.add(SaveView.class);
		});
		sbutton.setOffset(17).setSize(250,60);
		sbutton.setPosition(180, 14);
		group.addActor(sbutton);
		TextButton sbutton2=new TextButton("读取游戏", butstyle).onClick(()->{
			HoverController.add(com.rpsg.rpg.view.hover.LoadView.class);
		});
		sbutton2.setOffset(17).setSize(250,60);
		sbutton2.setPosition(444, 14);
		group.addActor(sbutton2);
		TextButton sbutton3=new TextButton("回到菜单", butstyle).onClick(()->{
			HoverController.add(ConfirmView.getDefault("确定要回到主菜单么？如未存档当前档案将会消失", (view)->{
				GameViews.state=GameViews.STATE_LOGO;
				GameViews.gameview.dispose();
				GameViews.gameview=null;
				((HoverView)view).disposed=true;
			}));
		});
		sbutton3.setOffset(17).setSize(250,60);
		sbutton3.setPosition(710, 14);
		group.addActor(sbutton3);
		Image screens = (Image)GameViews.gameview.stackView.params.get("bg");
		screens.setSize(172,97);
		screens.setPosition(182, 91);
		group.addActor(screens);
		
		table.add(group).prefSize(1024,329);
		table.row();
		
		WidgetGroup group2=new WidgetGroup();
		group2.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"graphics.png"));
		CheckBox box=new CheckBox("", cs,22);
		box.setPosition(190,276);
		group2.addActor(box.onClick(()->{
			Setting.persistence.antiAliasing=box.isChecked();
		}).check(Setting.persistence.antiAliasing));
		CheckBox box2=new CheckBox("", cs,22);
		box2.setPosition(190,149);
		group2.addActor(box2.onClick(()->{
			Setting.persistence.scaleAliasing=box2.isChecked();
		}).check(Setting.persistence.scaleAliasing));
		CheckBox box3=new CheckBox("", cs,22);
		box3.setPosition(190,44);
		group2.addActor(box3.onClick(()->{
			Setting.persistence.betterLight=box3.isChecked();
		}).check(Setting.persistence.betterLight));
		table.add(group2).prefSize(1024, 391);
		table.row();
		
		WidgetGroup group3=new WidgetGroup();
		group3.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"performance.png"));
		Slider sd1=new Slider(256, 1024, 1, false, slsty);
		sd1.setStrEnd(" MB").setLabelful(true).setPosition(200, 135);
		sd1.setWidth(620);
		sd1.onScroll(()->Setting.persistence.MemorySize=(int)sd1.getValue()).setValue(Setting.persistence.MemorySize);
		group3.addActor(sd1);
		CheckBox box6=new CheckBox("", cs,22);
		box6.setPosition(190,82);
		group3.addActor(box6.onClick(()->{
			Setting.persistence.cacheResource=box6.isChecked();
		}).check(Setting.persistence.cacheResource));
		table.add(group3).prefSize(1024,318);
		table.row();
		
		WidgetGroup group4=new WidgetGroup();
		group4.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"sound.png"));
		Slider sd2=new Slider(0, 100, 1, false, slsty);
		sd2.setStrEnd(" %").setLabelful(true).setPosition(200, 165);
		sd2.setWidth(620);
		sd2.onScroll(()->Setting.persistence.volume=(int)sd2.getValue()).setValue(Setting.persistence.volume);
		group4.addActor(sd2);
		Slider sd3=new Slider(0, 100, 1, false, slsty);
		sd3.setStrEnd(" %").setLabelful(true).setPosition(200, 50);
		sd3.setWidth(620);
		sd3.onScroll(()->Setting.persistence.musicVolume=(int)sd3.getValue()).setValue(Setting.persistence.musicVolume);
		group4.addActor(sd3);
		Slider sd4=new Slider(0, 100, 1, false, slsty);
		sd4.setStrEnd(" %").setLabelful(true).setPosition(200, -60);
		sd4.setWidth(620);
		sd4.onScroll(()->Setting.persistence.seVolume=(int)sd4.getValue()).setValue(Setting.persistence.seVolume);
		group4.addActor(sd4);
		table.add(group4).prefSize(1024,338);
		table.row();
		
		WidgetGroup group5=new WidgetGroup();
		group5.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"game.png"));
		ttest=new Label(ttstr, 26);
		ttest.setWidth(1000).setPosition(200, 663);
		group5.addActor(ttest);
		Slider sd5=new Slider(1, 15, 1, false, slsty);
		sd5.setStrEnd(" 帧/字").setLabelful(true).setPosition(200, 506);
		sd5.setWidth(620);
		sd5.onScroll(()->{
			Setting.persistence.textSpeed=(int)sd5.getValue();
			stepmax=(int) sd5.getValue();
		}).setValue(Setting.persistence.textSpeed);
		sd5.onScroll();
		group5.addActor(sd5);
		CheckBox box7=new CheckBox("", cs,22);
		box7.setPosition(190,440);
		group5.addActor(box7.onClick(()->{
			Setting.persistence.showFPS=box7.isChecked();
		}).check(Setting.persistence.showFPS));
		CheckBox box8=new CheckBox("", cs,22);
		box8.setPosition(190,340);
		group5.addActor(box8.onClick(()->{
			Setting.persistence.debugMod=box8.isChecked();
		}).check(Setting.persistence.debugMod));
		CheckBox box9=new CheckBox("", cs,22);
		box9.setPosition(190,228);
		group5.addActor(box9.onClick(()->{
			Setting.persistence.onErrorSendMsg=box6.isChecked();
		}).check(Setting.persistence.onErrorSendMsg));
		CheckBox box10=new CheckBox("", cs,22);
		box10.setPosition(190,63);
		group5.addActor(box10.onClick(()->{
			Setting.persistence.touchMod=box6.isChecked();
		}).check(Setting.persistence.touchMod));
		table.add(group5).prefSize(1024,830);
		table.row();
		
		WidgetGroup group6=new WidgetGroup();
		group6.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"about.png"));
		Label gamever=new Label(Setting.GAME_VERSION,23).setWidth(1000).setPad(-7);
		gamever.setPosition(405,811);
		group6.addActor(gamever);
		Label ever=new Label(Setting.GDXRPG_VERSION,23).setWidth(1000).setPad(-7);
		ever.setPosition(415,749);
		group6.addActor(ever);
		Label sys=new Label(System.getProperty("os.name"),23).setWidth(1000).setPad(-7);
		sys.setPosition(295,780);
		group6.addActor(sys);
		Label jv=new Label(System.getProperty("java.version"),23).setWidth(1000).setPad(-7);
		jv.setPosition(305,719);
		group6.addActor(jv);
		TextButton sbutton5=new TextButton("访问官网", butstyle).onClick(()->{
			try {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://www.rpsg-team.com");
			} catch (Exception e) {
				AlertUtil.add("无法正确打开网页。", AlertUtil.Red);
			}
		});
		sbutton5.setOffset(17).setSize(250,60);
		sbutton5.setPosition(414, 64);
		group6.addActor(sbutton5);
		table.add(group6).prefSize(1024,999);
		table.getCells().forEach((c)->c.padTop(40).padBottom(50));
//		table.setDebug(true);
//		group.setSize(table.getWidth(), table.getHeight());
		isstop=false;
		
		ScrollPane pane=new ScrollPane(table);
		pane.setSize(1024,576);
		pane.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(isstop){
					pane.cancel();
					return false;
				}
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
			}
			public void touchDragged (InputEvent event, float x, float y, int pointer) {
				if(isstop){
					pane.cancel();
				}
			}
		});
//		pane.setOverscroll(false, false);
		
		stage.addActor(pane);
//		Image bg=Res.get(Setting.GAME_RES_IMAGE_MENU_ITEM+"item_bg.png");
//		bg.setColor(1,1,1,0);
//		bg.setPosition(160,28);
//		bg.addAction(Actions.fadeIn(0.2f));
//		stage.addActor(bg);
		
		table.getCells().forEach((cell)->((WidgetGroup)cell.getActor()).getChildren().forEach((obj)->{
			if(!(obj instanceof Image))
				obj.addListener(new InputListener(){
					public boolean touchDown (InputEvent event, float xx, float y, int pointer, int button) {
						if(obj instanceof Slider){
							float x=(((Slider)obj).getValue()/((Slider)obj).getMaxValue());
							obj.setColor(x<0.5?new Color(1,2*x,0,1):new Color(2-2*x,1,0,1));
						}
						if(!(obj instanceof Image)) 
							Music.playSE("snd210");
						isstop=true;
						return true;
					}

					public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
						isstop=false;
					}

					public void touchDragged (InputEvent event, float xx, float y, int pointer) {
						if(obj instanceof Slider){
							float x=(((Slider)obj).getValue()/((Slider)obj).getMaxValue());
							obj.setColor(x<0.5?new Color(1,2*x,0,1):new Color(2-2*x,1,0,1));
						}
						isstop=true;
					}
				});
			if(obj instanceof Slider){
				float x=(((Slider)obj).getValue()/((Slider)obj).getMaxValue());
				obj.setColor(x<0.5?new Color(1,2*x,0,1):new Color(2-2*x,1,0,1));
			}
		}));
		
		ImageButton exit=new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"exit.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"exitc.png"));
		exit.setPosition(960, 550);
		exit.addAction(Actions.moveTo(960, 510,0.1f));
		exit.addListener(new InputListener(){
			public void touchUp (InputEvent event, float x, float y, int pointer, int b) {
				disposed=true;
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int b) {
				return true;
			}
		});
		stage.addActor(exit);
	}
	Color blue=new Color(80f/255f,111f/255f,187f/255f,1);
	Color green=new Color(219f/255f,255f/255f,219f/255f,1);
	Color cblue=new Color(219f/255f,238f/255f,255f/255f,1);
	
	public void draw(SpriteBatch batch) {
		stage.draw();
		SpriteBatch sb=(SpriteBatch) stage.getBatch();
		sb.begin();
		sb.end();
	}
	int step=0;
	int stepmax=1;
	String ttstr=Setting.GAME_MENU_SYSTEM_TEST_MESSAGE;
	public void logic() {
		stage.act();
		lvl5.setText(TimeUtil.getGameRunningTime());
		if(--step<=0){
			step=stepmax;
			if(ttest.getText().length()>=ttstr.length())
				ttest.setText("");
			ttest.setText(ttstr.substring(0, ttest.getText().length()+1));
		}
	}
	
	public void onkeyDown(int keyCode) {
		if(Keys.ESCAPE==keyCode || keyCode==Keys.X){
			this.disposed=true;
		}else
			stage.keyDown(keyCode);
	}

	public void dispose() {
		Persistence.save();
		stage.dispose();
	}

	
}
