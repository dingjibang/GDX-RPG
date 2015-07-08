package com.rpsg.rpg.view.menu;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
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
	Group group,inner;
	public void init() {
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()),MenuView.stage.getBatch());
		group=(Group) $.add(new Group()).setHeight(1500).getItem();
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_GLOBAL+"menu_fg_shadow.png").disableTouch()).appendTo(group).setPosition(80, y(30)).setColor(1,1,1,0).addAction(Actions.parallel(Actions.fadeIn(.3f),Actions.moveTo(80, y(180),0.3f,Interpolation.pow4Out)));
		inner=(Group) $.add(new Group()).setHeight(1500).appendTo(group).getItem();
		$.add(new ScrollPane(group)).setSize(GameUtil.screen_width-190, GameUtil.screen_height).setX(190).appendTo(stage);
//		stage.setDebugAll(true);
//		parent.click(HeroController.heros.get(0));
		generate();
	}
	
	private void generate() {
		inner.clear();
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_GLOBAL+"m_right.png")).appendTo(inner).setPosition(600, y(150)).onClick(new Runnable() {public void run() {
			next();
		}}).addAction(Actions.fadeIn(0.2f)).setColor(1,1,1,0);
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_GLOBAL+"m_right.png")).setScaleX(-1).appendTo(inner).setPosition(66, y(150)).onClick(new Runnable() {public void run() {
			prev();
		}}).addAction(Actions.fadeIn(0.2f)).setColor(1,1,1,0);
		$.add(new Label(parent.current.name,parent.current.name.length()>7?50:60).align(336, y(90))).appendTo(inner).addAction(Actions.fadeIn(0.2f)).setColor(1,1,1,0);;
		$.add(new Label(parent.current.jname,30).align(360, y(155)).setPad(-8)).setColor(1,1,1,0f).appendTo(inner).addAction(Actions.color(new Color(1,1,1,0.3f),0.2f));
		Group group=(Group) $.add(new Group()).setX(100).addAction(Actions.parallel(Actions.moveTo(100,y(350),0.5f,Interpolation.pow4Out),Actions.fadeIn(0.4f))).setColor(1, 1, 1,0).appendTo(inner).getItem();
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_STATUS+"info.png")).appendTo(group);
		$.add(new Label(parent.current.prop.get("level")+"",60).align(40, 90)).appendTo(group);
		$.add(new Label(parent.current.tag+"",20).align(60, 28)).appendTo(group).setColor(1,1,1,0.3f);
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(327,27).setPosition(142, 75).appendTo(group);
		$.add(Res.get(Setting.UI_BASE_PRO)).setSize(0,27).setPosition(142, 75).appendTo(group).setColor(Color.valueOf("c33737")).addAction(Actions.delay(0.3f,Actions.sizeTo(((float)parent.current.prop.get("exp")/(float)parent.current.prop.get("maxexp"))*327, 27,0.4f,Interpolation.pow4Out)));
		$.add(new Label(parent.current.prop.get("exp")+"/"+parent.current.prop.get("maxexp"),20).align(300, 97).setPad(-5)).appendTo(group).setColor(Color.valueOf("3bb740"));
		$.add(new Label(parent.current.prop.get("level")+1+"",20).align(485, 88)).appendTo(group);
		$.add(new Label(parent.current.lead?"无信息":parent.current.association.name+"（等级"+parent.current.association.level+"）",20).align(320, 65)).appendTo(group);
		$.add(new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_NEW_STATUS+"more_soc_info.png"), Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_NEW_STATUS+"more_soc_info_p.png"))).appendTo(group).setX(142);
	}

	public int y(int y){
		return (int) (group.getHeight()-y);
	}
	
	private void prev(){
		if(parent.getHeros().indexOf(parent.current)!=0){
			parent.click(parent.getHeros().get(parent.getHeros().indexOf(parent.current)-1));
			generate();
		}
	}
	
	private void next(){
		if(parent.getHeros().indexOf(parent.current)!=parent.getHeros().size()-1){
			parent.click(parent.getHeros().get(parent.getHeros().indexOf(parent.current)+1));
			generate();
		}
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
		if(Keys.LEFT == keyCode)
			prev();
		if(Keys.RIGHT == keyCode)
			next();
	}

	public void dispose() {
		stage.dispose();
	}
}
