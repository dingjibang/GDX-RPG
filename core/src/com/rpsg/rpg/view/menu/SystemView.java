package com.rpsg.rpg.view.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
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
//		stage.setDebugAll(true);
		
		Table parentTable = new Table().align(Align.topLeft);
		ScrollPane pane;
		
		TextButtonStyle tstyle = new TextButtonStyle();
		tstyle.down = Setting.UI_BUTTON;
		tstyle.up = Res.getDrawable(Setting.IMAGE_MENU_NEW_EQUIP+"throwbut.png");
		tstyle.font = Res.font.get(22);
		
		//**游戏档案 start
		{
			Table table = new Table().left().padLeft(50);
			table.add(new Label("游戏档案",55)).left().padTop(80).row();
			table.add(Res.get(Setting.IMAGE_MENU_NEW_SYSTEM+"split.png")).padTop(20).left().row();
			table.addActor(new Label("Save & Load",20).width(300).align(Align.right).x(483).y(7));
			table.addActor(Res.get(Setting.UI_BASE_IMG).size(734, 220).x(50).y(-260).color(Color.DARK_GRAY).a(.5f));
			table.addActor(Res.get(Setting.UI_BASE_IMG).size(734, 80).x(50).y(-260).color(Color.BLACK).a(.2f));
			table.addActor(Res.get(Setting.UI_BASE_IMG).size(164, 94).x(88).y(-162).a(.5f));
			table.addActor(new Image(MenuController.bg).size(160,90).position(90, -160));
			table.addActor(new Label(RPG.maps.getName(),40).width(300).position(300, -115));
			table.addActor(new FrameLabel("",20).frame(new CustomRunnable<Label>() {
				public void run(Label t) {
					t.setText("地图坐标：["+RPG.ctrl.hero.getHeadHero().mapx+":"+RPG.ctrl.hero.getHeadHero().mapy+"]，游戏已进行"+RPG.time.getGameRunningTime());
				}
			}).width(300).x(300).y(-138));
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
						@Override
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
			buttons.setPosition(417, -220);
			table.addActor(buttons);
			
			parentTable.add(table).align(Align.topLeft).row();
			
		}
		//**游戏档案 end
		
		stage.addActor($.add(pane = new ScrollPane(parentTable)).setSize(830,576).setX(194).getItem());
//		wgroup.validate();
		pane.layout();
		
		return this;
	}
	
	public void draw(SpriteBatch batch) {
		stage.draw();
	}
	public void logic() {
		stage.act();
	}
	
	public void onkeyDown(int keyCode) {
		stage.keyDown(keyCode);
	}

	public void dispose() {
		Persistence.save();
		stage.dispose();
	}

	
}
