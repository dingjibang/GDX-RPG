package com.rpsg.rpg.view.menu;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.CustomRunnable;
import com.rpsg.rpg.object.base.Persistence;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.MenuController;
import com.rpsg.rpg.system.ui.CheckBox;
import com.rpsg.rpg.system.ui.DefaultIView;
import com.rpsg.rpg.system.ui.FrameLabel;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.system.ui.TextButton;
import com.rpsg.rpg.system.ui.View;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.view.GameViews;
import com.rpsg.rpg.view.hover.ConfirmView;
import com.rpsg.rpg.view.hover.LoadView;
import com.rpsg.rpg.view.hover.SaveView;

public class SystemView extends DefaultIView{
	public View init() {
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()),MenuView.stage.getBatch());
		stage.setDebugAll(true);
		
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
		tstyle.up = Res.getDrawable(Setting.IMAGE_MENU_NEW_EQUIP+"throwbut.png");
		tstyle.font = Res.font.get(22);
		
		CheckBoxStyle cstyle=new CheckBoxStyle();
		cstyle.checkboxOff=Res.getDrawable(Setting.IMAGE_GLOBAL+"optb_s.png");
		cstyle.checkboxOn=Res.getDrawable(Setting.IMAGE_GLOBAL+"optb.png");
		cstyle.font=Res.font.get(24);
		
		//**游戏档案 start
		{
			Table table = new Table().left().padLeft(50);
			table.add(new Label("游戏档案",55)).left().padTop(50).row();
			table.add(Res.get(Setting.IMAGE_MENU_NEW_SYSTEM+"split.png")).padTop(15).left().row();
			table.add(new Label("Save & Load",20).align(Align.right)).width(300).align(Align.right).height(0).padTop(-40).row();
			table.add(Res.get(Setting.UI_BASE_IMG).color(Color.DARK_GRAY).a(.7f)).prefSize(734, 160).padTop(20).row();
			table.add(Res.get(Setting.UI_BASE_IMG).color(Color.BLACK).a(.3f)).prefSize(734, 80).row();
			table.add(Res.get(Setting.UI_BASE_IMG).a(.5f)).size(164,94).padTop(-320).padLeft(41).align(Align.left).row();
			table.add(new Image(MenuController.bg)).size(160,90).padTop(-320).padLeft(43).align(Align.left).row();
			table.add(new Label(RPG.maps.getName(),40)).width(450).padTop(-355).padLeft(233).align(Align.left).row();
			table.add(new FrameLabel("",20).frame(new CustomRunnable<FrameLabel>() {
				public void run(FrameLabel t) {
					t.setNoLayoutText("["+RPG.ctrl.hero.getHeadHero().mapx+":"+RPG.ctrl.hero.getHeadHero().mapy+"]    队伍共有 "+RPG.ctrl.hero.heros.size()+" 人，游戏已进行 "+RPG.time.getGameRunningTime());
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
				c.padLeft(25).padRight(25).width(200).height(50);
			table.add(buttons).padTop(-80);
			table.layout();
			parentTable.add(table).align(Align.topLeft).row();
			
		}
		//**游戏档案 end
		
		//**图形选项 start
		{
			Table table = new Table().left().padLeft(50);
			table.add(new Label("图形选项",55)).left().padTop(50).row();
			table.add(Res.get(Setting.IMAGE_MENU_NEW_SYSTEM+"split.png")).padTop(15).left().row();
			table.add(new Label("Graphics Options",20).align(Align.right)).width(300).align(Align.right).height(0).padTop(-40).row();
			table.add(new CheckBox("开启平滑纹理",cstyle).onClick(new CustomRunnable<CheckBox>() {public void run(CheckBox t) {
				set.scaleAliasing = t.isChecked();
			}}).check(set.scaleAliasing).getLabelCell().padLeft(30).getActor().getParent()).left().padTop(20).row();
			table.add(new Label("对纹理进行双线性过滤操作，使纹理变得更加平滑，屏幕窗口拉抻时，纹理不会产生锯齿，适用于手机/平板。",20).warp(true).color(Color.LIGHT_GRAY)).width(660).padLeft(75).left().padTop(20).row();
			table.add(new CheckBox("开启平滑纹理",cstyle).onClick(new CustomRunnable<CheckBox>() {public void run(CheckBox t) {
				set.scaleAliasing = t.isChecked();
			}}).check(set.scaleAliasing).getLabelCell().padLeft(30).getActor().getParent()).left().padTop(20).row();
			table.add(new Label("对纹理进行双线性过滤操作，使纹理变得更加平滑，屏幕窗口拉抻时，纹理不会产生锯齿，适用于手机/平板。",20).warp(true).color(Color.LIGHT_GRAY)).width(660).padLeft(75).left().padTop(20).row();
			table.layout();
			parentTable.add(table).align(Align.topLeft).row();
		}
		//**图形选项 end
		
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
