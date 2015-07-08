package com.rpsg.rpg.view.menu;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Music;
import com.rpsg.rpg.object.base.EmptyAssociation;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.object.base.Resistance;
import com.rpsg.rpg.object.base.items.Equipment;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.HeroController;
import com.rpsg.rpg.system.ui.DefaultIView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.ImageButton;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.utils.display.RadarUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.TimeUtil;
import com.rpsg.rpg.view.GameViews;

public class StatusView extends DefaultIView {
	Group group;
	public void init() {
		stage = new Stage(new ScalingViewport(Scaling.stretch,GameUtil.screen_width, GameUtil.screen_height,new OrthographicCamera()), MenuView.stage.getBatch());
		group=(Group) $.add(new Group()).getItem();
		ScrollPane pane=(ScrollPane) $.add(new ScrollPane(group)).setPosition(190, 0).getItem();
		$.add(pane).setDebug(true).setSize(835, GameUtil.screen_height);
		group.setSize(100, 200);
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_GLOBAL+"menu_fg_shadow.png")).appendTo(group).setPosition(140, y(80));
		stage.addActor(pane);
		stage.setDebugAll(true);
	}
	
	public float y(int y){
		return group.getHeight()-y;
	}

	public void draw(SpriteBatch batch) {
		stage.draw();
	}

	public void logic() {
		stage.act();
	}

	public void onkeyDown(int keyCode) {
		if (Keys.ESCAPE == keyCode || keyCode == Keys.X) {
			this.disposed = true;
		} else
			stage.keyDown(keyCode);
	}

	public void dispose() {
		stage.dispose();
	}
}
