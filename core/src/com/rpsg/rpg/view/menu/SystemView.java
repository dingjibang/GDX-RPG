package com.rpsg.rpg.view.menu;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Persistence;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.MenuController;
import com.rpsg.rpg.system.ui.CheckBox;
import com.rpsg.rpg.system.ui.FrameLabel;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.IMenuView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.system.ui.Slider;
import com.rpsg.rpg.system.ui.Slider.SliderStyle;
import com.rpsg.rpg.system.ui.TextButton;
import com.rpsg.rpg.system.ui.View;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.view.GameViews;
import com.rpsg.rpg.view.hover.ConfirmView;
import com.rpsg.rpg.view.hover.LoadView;
import com.rpsg.rpg.view.hover.SaveView;

public class SystemView extends IMenuView{
	public View init() {
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()),MenuView.stage.getBatch());
		stage.setDebugAll(Setting.persistence.uiDebug);
		
		generate();
		return this;
	}
	
	private float lastTop = 0;
	ScrollPane pane;
	
	private void generate() {
		stage.clear();
		
		final Persistence set = Setting.persistence;
		
		if(pane != null)
			lastTop = pane.getScrollY();
		
		Table parentTable = new Table().align(Align.topLeft);
		
		TextButtonStyle tstyle = new TextButtonStyle();
		tstyle.down = Setting.UI_BUTTON;
		tstyle.up = Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"throwbut.png");
		tstyle.font = Res.font.get(22);
		
		CheckBoxStyle cstyle=new CheckBoxStyle();
		cstyle.checkboxOff=Res.getDrawable(Setting.IMAGE_GLOBAL+"optb_s.png");
		cstyle.checkboxOn=Res.getDrawable(Setting.IMAGE_GLOBAL+"optb.png");
		cstyle.font=Res.font.get(24);
		
		SliderStyle sstyle=new SliderStyle();
		sstyle.background=Res.getDrawable(Setting.IMAGE_GLOBAL+"sliderbar.png");
		sstyle.knob=Res.getDrawable(Setting.IMAGE_GLOBAL+"slider.png");
		
		//**游戏档案 start
		{
			Table table = new Table().left().padLeft(50);
			table.add(new Label("游戏档案",55)).left().padTop(50).row();
			table.add(Res.get(Setting.IMAGE_MENU_SYSTEM+"split.png")).padTop(15).left().row();
			table.add(new Label("Save & Load",20).align(Align.right)).width(300).align(Align.right).height(0).padTop(-40).row();
			table.add(Res.get(Setting.UI_BASE_IMG).color(Color.DARK_GRAY).a(.65f)).prefSize(734, 160).padTop(20).row();
			table.add(Res.get(Setting.UI_BASE_IMG).color(Color.BLACK).a(.5f)).prefSize(734, 80).row();
			table.add(Res.get(Setting.UI_BASE_IMG).a(.5f)).size(164,94).padTop(-320).padLeft(41).align(Align.left).row();
			table.add(new Image(MenuController.bg)).size(160,90).padTop(-320).padLeft(43).align(Align.left).row();
			table.add(new Label(RPG.maps.getName(),40)).width(450).padTop(-355).padLeft(233).align(Align.left).row();
			table.add(new FrameLabel("",20).frame(new CustomRunnable<FrameLabel>() {
				public void run(FrameLabel t) {
					t.setNoLayoutText("["+RPG.ctrl.hero.getHeadHero().mapx+":"+RPG.ctrl.hero.getHeadHero().mapy+"]    队伍共有 "+RPG.ctrl.hero.currentHeros.size()+" 人，游戏已进行 "+RPG.time.getGameRunningTime());
				}
			})).width(450).padTop(-270).padLeft(233).align(Align.left).row();
			Table buttons = new Table().center();
			buttons.add(new TextButton("保存游戏", tstyle).onClick(new Runnable() {
				public void run() {
					RPG.popup.add(SaveView.class);
				}
			}));
			buttons.add(new TextButton("读取游戏", tstyle).onClick(new Runnable() {
				public void run() {
					RPG.popup.add(LoadView.class);
				}
			}));
			buttons.add(new TextButton("回到菜单", tstyle).onClick(new Runnable() {
				public void run() {
					RPG.popup.add(ConfirmView.okCancel("确定要回到主菜单么？如未存档当前档案将会消失", new CustomRunnable<HoverView>() {
						public void run(HoverView view) {
							GameViews.state = GameViews.STATE_LOGO;
							GameViews.gameview.dispose();
							GameViews.gameview = null;
							((HoverView) view).disposed = true;
						}
					}));
				}
			}));
			for(Cell<?> c:buttons.getCells())
				c.padLeft(17).padRight(17).width(200).height(50);
			table.add(buttons).padTop(-80);
			table.layout();
			parentTable.add(table).align(Align.topLeft).row();
			
		}
		//**游戏档案 end
		
		//**图形选项 start
		{
			Table table = new Table().left().padLeft(50);
			table.add(new Label("图形选项",55)).left().padTop(50).row();
			table.add(Res.get(Setting.IMAGE_MENU_SYSTEM+"split.png")).padTop(15).left().row();
			table.add(new Label("Graphics Options",20).align(Align.right)).width(300).align(Align.right).height(0).padTop(-40).row();
			
			table.add(new CheckBox("开启平滑纹理",cstyle).onClick(new CustomRunnable<CheckBox>() {public void run(CheckBox t) {
				set.scaleAliasing = t.isChecked();
			}}).check(set.scaleAliasing).getLabelCell().padLeft(30).getActor().getParent()).left().padTop(50).row();
			table.add(new Label("对纹理进行双线性过滤操作，使纹理变得更加平滑，屏幕窗口拉抻时，纹理不会产生锯齿，适用于手机/平板。",20).warp(true).color(Color.valueOf("dddddd"))).width(660).padLeft(75).left().padTop(20).row();
			
			table.add(new CheckBox("开启图形抗锯齿",cstyle).onClick(new CustomRunnable<CheckBox>() {public void run(CheckBox t) {
				set.antiAliasing = t.isChecked();
			}}).check(set.antiAliasing).getLabelCell().padLeft(30).getActor().getParent()).left().padTop(20).row();
			table.add(new Label("开启抗锯齿，你懂的。",20).warp(true).color(Color.valueOf("dddddd"))).width(660).padLeft(75).left().padTop(20).row();
			
			table.add(new CheckBox("高级画质",cstyle).onClick(new CustomRunnable<CheckBox>() {public void run(CheckBox t) {
				set.betterDisplay = t.isChecked();
			}}).check(set.betterDisplay).getLabelCell().padLeft(30).getActor().getParent()).left().padTop(20).row();
			table.add(new Label("对画面使用二次渲染，可以提高游戏视觉效果，但可能会降低一些性能。",20).warp(true).color(Color.valueOf("dddddd"))).width(660).padLeft(75).left().padTop(20).row();
			
			table.add(new CheckBox("显示天气效果",cstyle).onClick(new CustomRunnable<CheckBox>() {public void run(CheckBox t) {
				set.weather = t.isChecked();
			}}).check(set.weather).getLabelCell().padLeft(30).getActor().getParent()).left().padTop(20).row();
			table.add(new Label("选择是否启用天气效果，可能降低一些性能，对游戏的剧情不产生影响。",20).warp(true).color(Color.valueOf("dddddd"))).width(660).padLeft(75).left().padTop(20).row();
			
			table.layout();
			parentTable.add(table).align(Align.topLeft).row();
		}
		//**图形选项 end
		
		//**游戏选项 start
		{
			Table table = new Table().left().padLeft(50);
			table.add(new Label("游戏选项",55)).left().padTop(50).row();
			table.add(Res.get(Setting.IMAGE_MENU_SYSTEM+"split.png")).padTop(15).left().row();
			table.add(new Label("Game Options",20).align(Align.right)).width(300).align(Align.right).height(0).padTop(-40).row();
			
			table.add(new CheckBox("触屏模式",cstyle).onClick(new CustomRunnable<CheckBox>() {public void run(CheckBox t) {
				set.touchMod = t.isChecked();
			}}).check(set.touchMod).getLabelCell().padLeft(30).getActor().getParent()).left().padTop(50).row();
			table.add(new Label("开启后将对触屏进行一部分优化，适合手机/平板。",20).warp(true).color(Color.valueOf("dddddd"))).width(660).padLeft(75).left().padTop(20).row();
			
			table.add(new CheckBox("触屏粒子",cstyle).onClick(new CustomRunnable<CheckBox>() {public void run(CheckBox t) {
				set.touchParticle = t.isChecked();
			}}).check(set.touchParticle).getLabelCell().padLeft(30).getActor().getParent()).left().padTop(20).row();
			table.add(new Label("开启后，点击/滑动屏幕会有粒子特效。",20).warp(true).color(Color.valueOf("dddddd"))).width(660).padLeft(75).left().padTop(20).row();
			
			table.add(new CheckBox("点击地面时自动寻路",cstyle).onClick(new CustomRunnable<CheckBox>() {public void run(CheckBox t) {
				set.pathFind = t.isChecked();
			}}).check(set.pathFind).getLabelCell().padLeft(30).getActor().getParent()).left().padTop(20).row();
			table.add(new Label("当点击游戏的地图时，会进行自动寻路操作。",20).warp(true).color(Color.valueOf("dddddd"))).width(660).padLeft(75).left().padTop(20).row();
			
			table.add(new CheckBox("默认奔跑状态",cstyle).onClick(new CustomRunnable<CheckBox>() {public void run(CheckBox t) {
				set.runmod = t.isChecked();
			}}).check(set.runmod).getLabelCell().padLeft(30).getActor().getParent()).left().padTop(20).row();
			table.add(new Label("启动后，默认是“奔跑”状态，按住ctrl(B键)可以还原。",20).warp(true).color(Color.valueOf("dddddd"))).width(660).padLeft(75).left().padTop(20).row();
			
			table.add(new CheckBox("平滑视角",cstyle).onClick(new CustomRunnable<CheckBox>() {public void run(CheckBox t) {
				set.softCamera = t.isChecked();
			}}).check(set.softCamera).getLabelCell().padLeft(30).getActor().getParent()).left().padTop(20).row();
			table.add(new Label("移动时，视角会变得平滑。",20).warp(true).color(Color.valueOf("dddddd"))).width(660).padLeft(75).left().padTop(20).row();
			
			table.layout();
			parentTable.add(table).align(Align.topLeft).row();
		}
		//**游戏选项 end
		
		//**音频选项 start
		{
			Table table = new Table().left().padLeft(50);
			table.add(new Label("音频选项",55)).left().padTop(50).row();
			table.add(Res.get(Setting.IMAGE_MENU_SYSTEM+"split.png")).padTop(15).left().row();
			table.add(new Label("Sound Options",20).align(Align.right)).width(300).align(Align.right).height(0).padTop(-40).row();
			
			table.add(new Label("游戏整体音量",24)).left().padTop(50).row();
			table.add(new Slider(0, 100, 1, false, sstyle).onScroll(new CustomRunnable<Slider>() {
				public void run(Slider s) {
					set.volume = (int) s.getValue();
				}
			}).setStrEnd(" %").setLabelful(true).value(set.volume)).width(580).left().padLeft(30).padTop(30).row();
			
			table.add(new Label("音乐音量",24)).left().padTop(50).row();
			table.add(new Slider(0, 100, 1, false, sstyle).onScroll(new CustomRunnable<Slider>() {
				public void run(Slider s) {
					set.musicVolume = (int) s.getValue();
				}
			}).setStrEnd(" %").setLabelful(true).value(set.musicVolume)).width(580).left().padLeft(30).padTop(30).row();
			
			table.add(new Label("音效音量",24)).left().padTop(50).row();
			table.add(new Slider(0, 100, 1, false, sstyle).onScroll(new CustomRunnable<Slider>() {
				public void run(Slider s) {
					set.seVolume = (int) s.getValue();
				}
			}).setStrEnd(" %").setLabelful(true).value(set.seVolume)).width(580).left().padLeft(30).padTop(30).row();
			
			table.layout();
			parentTable.add(table).align(Align.topLeft).row();
		}
		//**音频选项 end
		
		//**其他选项 start
		{
			Table table = new Table().left().padLeft(50);
			table.add(new Label("其他选项",55)).left().padTop(50).row();
			table.add(Res.get(Setting.IMAGE_MENU_SYSTEM+"split.png")).padTop(15).left().row();
			table.add(new Label("Other Options",20).align(Align.right)).width(300).align(Align.right).height(0).padTop(-40).row();
			
			table.add(new CheckBox("缓存游戏资源",cstyle).onClick(new CustomRunnable<CheckBox>() {public void run(CheckBox t) {
				set.cacheResource = t.isChecked();//TODO
			}}).check(set.cacheResource).getLabelCell().padLeft(30).getActor().getParent()).left().padTop(20).row();
			table.add(new Label("游戏将尝试缓存已读取的资源，在下次进入菜单、地图时会加快读取，但会增加内存的消耗。",20).warp(true).color(Color.valueOf("dddddd"))).width(660).padLeft(75).left().padTop(20).row();
			
			table.add(new CheckBox("在屏幕上显示帧数",cstyle).onClick(new CustomRunnable<CheckBox>() {public void run(CheckBox t) {
				set.showFPS = t.isChecked();
			}}).check(set.showFPS).getLabelCell().padLeft(30).getActor().getParent()).left().padTop(50).row();
			table.add(new Label("你是强迫症么？",20).warp(true).color(Color.valueOf("dddddd"))).width(660).padLeft(75).left().padTop(20).row();
			
			table.add(new CheckBox("输出调试信息",cstyle).onClick(new CustomRunnable<CheckBox>() {public void run(CheckBox t) {
				set.debugMod = t.isChecked();
			}}).check(set.debugMod).getLabelCell().padLeft(30).getActor().getParent()).left().padTop(50).row();
			table.add(new Label("输出游戏的调试信息到控制台（开发者）。",20).warp(true).color(Color.valueOf("dddddd"))).width(660).padLeft(75).left().padTop(20).row();
			
			table.add(new CheckBox("调试布局",cstyle).onClick(new CustomRunnable<CheckBox>() {public void run(CheckBox t) {
				set.uiDebug = t.isChecked();
			}}).check(set.uiDebug).getLabelCell().padLeft(30).getActor().getParent()).left().padTop(20).row();
			table.add(new Label("启用Stage Debug（开发者）。",20).warp(true).color(Color.valueOf("dddddd"))).width(660).padLeft(75).left().padTop(20).row();
			
			table.add(new CheckBox("当游戏发生异常时，尝试上报错误问题",cstyle).onClick(new CustomRunnable<CheckBox>() {public void run(CheckBox t) {
				set.uiDebug = t.isChecked();
			}}).check(set.uiDebug).getLabelCell().padLeft(30).getActor().getParent()).left().padTop(20).row();
			table.add(new Label("上报的信息将不会收集您的任何私有资料。",20).warp(true).color(Color.valueOf("dddddd"))).width(660).padLeft(75).left().padTop(20).row();
			table.add($.add(new Label("点击我可以查看《隐私说明》",20).warp(true).color(Color.valueOf("8FBEE6"))).setTouchable(Touchable.enabled).onClick(new Runnable() {
				public void run() {
					GameUtil.openURL("http://www.baidu.com");
				}
			}).getItem()).width(660).padLeft(75).left().padTop(20).row();
			
			
			table.layout();
			parentTable.add(table).align(Align.topLeft).row();
		}
		//**其他选项 end
		
		//**关于 start
		{
			Table table = new Table().left().padLeft(10);
			table.add(new Label("关于",55)).left().padTop(50).padLeft(40).row();
			table.add(Res.get(Setting.IMAGE_MENU_SYSTEM+"split.png")).padTop(15).padLeft(40).left().row();
			table.add(new Label("About",20).align(Align.right)).width(300).align(Align.right).height(0).padLeft(40).padTop(-40).row();
			
			table.add(Res.get(Setting.IMAGE_LOGO+"logo_without_border.png")).padTop(200).row();
			table.add(new Label("秘封异闻录",35)).padTop(30).row();
			
			table.add(new Label("秘封异闻录RPSG制作组所制作的一款《东方Project》同人游戏。\n它采用开源角色扮演框架 GDX-RPG 制作。",20).center()).center().padTop(80).row();
			table.add(new Label("秘封异闻录游戏版本："+Setting.GAME_VERSION+"\nGDX-RPG引擎版本："+Setting.GDXRPG_VERSION,20).center()).center().padTop(30).row();
			table.add(Res.get(Setting.IMAGE_LOGO+"title.png")).padTop(450).padBottom(50).row();
			table.add(new TextButton("访问官网", tstyle).onClick(new Runnable() {
				public void run() {
					GameUtil.openURL("http://www.rpsg-team.com/");
				}
			})).padBottom(200).size(250,55);
			table.layout();
			parentTable.add(table).align(Align.topLeft).row();
		}
		//**游戏选项 end
		
		$.add(parentTable).children().children().find(Slider.class,TextButton.class,Label.class).each(new CustomRunnable<Actor>() {
			public void run(final Actor t) {
				t.addListener(new InputListener(){
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						pane.cancel();
						return true;
					}
					public void touchDragged(InputEvent event, float x, float y, int pointer) {
						pane.cancel();
					}
				});
			}
		});
		stage.addActor($.add(pane = new ScrollPane(parentTable)).setSize(830,576).setX(194).getItem());
		pane.layout();
		pane.setSmoothScrolling(false);
		pane.setScrollY(lastTop != lastTop ? 0 : lastTop);
	}

	public void draw(SpriteBatch batch) {
		stage.draw();
	}
	public void logic() {
		stage.act();
	}
	
	public void onkeyDown(int keyCode) {
		if(keyCode == Keys.R)
			generate();//for debug TODO
		stage.keyDown(keyCode);
	}

	public void dispose() {
		Persistence.save();
		stage.dispose();
	}

	
}
