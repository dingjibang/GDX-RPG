package com.rpsg.rpg.view.menu;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Persistence;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.MenuController;
import com.rpsg.rpg.system.ui.DefaultIView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.system.ui.View;
import com.rpsg.rpg.utils.game.GameUtil;

public class SystemView extends DefaultIView{
	public View init() {
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()),MenuView.stage.getBatch());
		stage.setDebugAll(true);
		
		Table parentTable = new Table().align(Align.topLeft);
		ScrollPane pane;
		//**游戏档案 start
		{
			Table table = new Table().left().padLeft(50);
			table.add(new Label("游戏档案",55)).left().padTop(80).row();
			table.add(Res.get(Setting.IMAGE_MENU_NEW_SYSTEM+"split.png")).padTop(20).left().row();
			table.add(new Label(RPG.maps.getName(),55)).left().padTop(80).row();
			table.addActor(new Label("Save & Load",20).width(300).align(Align.right).x(483).y(10));
			table.addActor(Res.get(Setting.UI_BASE_IMG).size(734, 200).x(50).y(-230).color(Color.DARK_GRAY).a(.5f));
			table.addActor(new Image(MenuController.bg).size(160,90).position(90, -150));
			parentTable.add(table).align(Align.topLeft);
			
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
