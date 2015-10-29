package com.rpsg.rpg.view.menu;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.gdxQuery.GdxQueryRunnable;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.Equipment;
import com.rpsg.rpg.object.base.items.Item;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.CheckBox;
import com.rpsg.rpg.system.ui.DefaultIView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.ImageButton;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.system.ui.View;
import com.rpsg.rpg.utils.game.GameUtil;

public class StatusView extends DefaultIView {
	Group group,inner;
	ScrollPane pane;
	public View init() {
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()),MenuView.stage.getBatch());
		group=(Group) $.add(new Group()).setHeight(1750).getItem();
		$.add(Res.get(Setting.IMAGE_MENU_NEW_GLOBAL+"menu_fg_shadow.png").disableTouch()).appendTo(group).setPosition(50, y(30)).setColor(1,1,1,0).addAction(Actions.parallel(Actions.fadeIn(.3f),Actions.moveTo(50, y(180),0.3f,Interpolation.pow4Out)));
		inner=(Group) $.add(new Group()).setHeight(1750).appendTo(group).getItem();
		pane=(ScrollPane) $.add(new ScrollPane(group)).setSize(GameUtil.screen_width-190, GameUtil.screen_height).setX(190).appendTo(stage).getItem();
//		stage.setDebugAll(true);
		generate();
		return this;
	}
	
	protected void generate() {
		inner.clear();
		$.add(Res.get(Setting.IMAGE_MENU_NEW_GLOBAL+"m_right.png")).appendTo(inner).setPosition(570, y(150)).onClick(new Runnable() {public void run() {
			next();
		}}).addAction(Actions.fadeIn(0.2f)).setColor(1,1,1,0);
		$.add(Res.get(Setting.IMAGE_MENU_NEW_GLOBAL+"m_right.png")).setScaleX(-1).appendTo(inner).setPosition(36, y(150)).onClick(new Runnable() {public void run() {
			prev();
		}}).addAction(Actions.fadeIn(0.2f)).setColor(1,1,1,0);
		$.add(new Label(parent.current.name,parent.current.name.length()>7?50:60).align(107, y(135)).width(450)).appendTo(inner).addAction(Actions.fadeIn(0.2f)).setColor(1,1,1,0);
		$.add(new Label(parent.current.jname,30).align(107, y(165)).width(450)).setColor(1,1,1,0f).appendTo(inner).addAction(Actions.color(new Color(1,1,1,0.3f),0.2f));
		
		Group group=(Group) $.add(new Group()).setX(70).addAction(Actions.parallel(Actions.moveTo(70,y(320),0.5f,Interpolation.pow4Out),Actions.fadeIn(0.4f))).setColor(1, 1, 1,0).appendTo(inner).getItem();
		$.add(Res.get(Setting.IMAGE_MENU_NEW_STATUS+"info.png")).appendTo(group);
		$.add(new Label(parent.current.prop.get("level"),60).align(-28, 35).width(200)).appendTo(group);
		$.add(new Label(parent.current.tag,20).align(-28, 10).width(200)).appendTo(group).setColor(1,1,1,0.5f);
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(327,27).setPosition(142, 75).appendTo(group);
		$.add(Res.get(Setting.UI_BASE_PRO)).setSize(0,27).setPosition(142, 75).appendTo(group).setColor(Color.valueOf("c33737")).addAction(Actions.delay(0.3f,Actions.sizeTo(((float)parent.current.prop.get("exp")/(float)parent.current.prop.get("maxexp"))*327, 27,0.4f,Interpolation.pow4Out)));
		$.add(new Label(parent.current.prop.get("exp")+"/"+parent.current.prop.get("maxexp"),20).align(142, 77).width(327)).appendTo(group).setColor(Color.valueOf("3bb740"));
		$.add(new Label(parent.current.prop.get("level")+1,20).align(470, 74).width(50)).appendTo(group);
		$.add(new Label(parent.current.lead?"无信息":parent.current.association.name+"（等级"+parent.current.association.level+"）",20).align(142, 44).width(327)).appendTo(group);
		$.add(new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_NEW_STATUS+"more_soc_info.png"), Res.getDrawable(Setting.IMAGE_MENU_NEW_STATUS+"more_soc_info_p.png"))).appendTo(group).setX(142);
		
		Group group2=(Group) $.add(new Group()).setX(70).addAction(Actions.parallel(Actions.moveTo(70,y(420),0.7f,Interpolation.pow4Out),Actions.fadeIn(0.4f))).setColor(1, 1, 1,0).appendTo(inner).getItem();
		$.add(Res.get(Setting.IMAGE_MENU_NEW_STATUS+"info2.png")).appendTo(group2);
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(245,24).setPosition(120, 48).appendTo(group2);
		$.add(Res.get(Setting.UI_BASE_PRO)).setSize(0,18).setPosition(123, 51).appendTo(group2).setColor(Color.valueOf("c33737")).addAction(Actions.delay(0.4f,Actions.sizeTo(((float)parent.current.prop.get("hp")/(float)parent.current.prop.get("maxhp"))*239, 18,0.6f,Interpolation.pow4Out)));
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(245,24).setPosition(120, 8).appendTo(group2);
		$.add(Res.get(Setting.UI_BASE_PRO)).setSize(0,18).setPosition(123, 11).appendTo(group2).setColor(Color.valueOf("3762c3")).addAction(Actions.delay(0.4f,Actions.sizeTo(((float)parent.current.prop.get("mp")/(float)parent.current.prop.get("maxmp"))*239, 18,0.6f,Interpolation.pow4Out)));
		$.add(new Label(parent.current.prop.get("hp")+"/"+parent.current.prop.get("maxhp"),22).align(345, 48).width(200)).appendTo(group2);
		$.add(new Label(parent.current.prop.get("mp")+"/"+parent.current.prop.get("maxmp"),22).align(345, 8).width(200)).appendTo(group2);
		
		Group group3=(Group) $.add(new Group()).setX(70).addAction(Actions.parallel(Actions.moveTo(70,y(558),0.8f,Interpolation.pow4Out),Actions.fadeIn(0.4f))).setColor(1, 1, 1,0).appendTo(inner).getItem();
		$.add(Res.get(Setting.IMAGE_MENU_NEW_STATUS+"info3.png")).appendTo(group3);
		$.add(new Label(parent.current.prop.get("attack"),22).align(130, 83).width(100)).appendTo(group3);
		$.add(new Label(parent.current.prop.get("defense"),22).align(390, 83).width(100)).appendTo(group3);
		$.add(new Label(parent.current.prop.get("magicAttack"),22).align(130, 45).width(100)).appendTo(group3);
		$.add(new Label(parent.current.prop.get("magicDefense"),22).align(390, 45).width(100)).appendTo(group3);
		$.add(new Label(parent.current.prop.get("speed"),22).align(130, 8).width(100)).appendTo(group3);
		$.add(new Label(parent.current.prop.get("hit"),22).align(390, 8).width(100)).appendTo(group3);
		
		Group group4=(Group) $.add(new Group()).setX(70).addAction(Actions.parallel(Actions.moveTo(70,y(1032),1f,Interpolation.pow4Out),Actions.fadeIn(0.4f))).setColor(1, 1, 1,0).appendTo(inner).getItem();
		$.add(Res.get(Setting.IMAGE_MENU_NEW_STATUS+"equipbox.png")).appendTo(group4);
		int yoff=0;
		for(String key:parent.current.equips.keySet()){
			Equipment equip=parent.current.equips.get(key);
			yoff+=89;
			$.add(Res.get(equip==null?Item.getDefaultIcon():equip.getIcon())).appendTo(group4).setPosition(12f, yoff-80).setSize(73,70);
			$.add(new Label(equip==null?"":equip.illustration+equip.illustration+equip.illustration,16).position(110, yoff-70).overflow(true).width(385)).appendTo(group4);
			$.add(new Label(equip==null?"无装备":equip.name,30).width(385).align(equip!=null?Align.left:Align.center).position(110, yoff-(equip==null?60:48))).appendTo(group4);
		}
		
		Group group5=(Group) $.add(new Group()).setX(70).addAction(Actions.parallel(Actions.moveTo(70,y(1442),1f,Interpolation.pow4Out),Actions.fadeIn(0.4f))).setColor(1, 1, 1,0).appendTo(inner).getItem();
		$.add(Res.get(Setting.IMAGE_MENU_NEW_STATUS+"p.png")).appendTo(group5);
		int count=-1;
		for(String key:parent.current.resistance.keySet())
			$.add(Res.get(Setting.IMAGE_MENU_NEW_STATUS+parent.current.resistance.get(key).name()+".png")).appendTo(group5).setPosition(38+(174*(++count%3)), 247-(count>=3 && count <6?115:count>=6?228:0));
		CheckBoxStyle cstyle=new CheckBoxStyle();
		cstyle.checkboxOff=Res.getDrawable(Setting.IMAGE_MENU_NEW_STATUS+"help.png");
		cstyle.checkboxOn=Res.getDrawable(Setting.IMAGE_MENU_NEW_STATUS+"help_p.png");// help button press
		cstyle.font = Res.font.get(10);
		final Image phelp=(Image) $.add(Res.get(Setting.IMAGE_MENU_NEW_STATUS+"p_help.png")).appendTo(group5).setColor(1,1,1,0).getItem();//resistance help button
		$.add(new CheckBox("", cstyle)).appendTo(group5).setPosition(392,342).run(new GdxQueryRunnable() {public void run(final GdxQuery self) {self.onClick(new Runnable() {public void run() {
			phelp.addAction(self.isChecked()?Actions.fadeIn(0.3f):Actions.fadeOut(0.3f));
		}});}});
		
		if(parent.current.lead){
			Group group6=(Group) $.add(new Group()).setX(70).addAction(Actions.parallel(Actions.moveTo(70,y(1702),1f,Interpolation.pow4Out),Actions.fadeIn(0.4f))).setColor(1, 1, 1,0).appendTo(inner).getItem();
			$.add(Res.get(Setting.IMAGE_MENU_NEW_STATUS+"person.png")).appendTo(group6);
			$.add(Res.get(Setting.UI_BASE_IMG)).setSize(420,24).setPosition(87, 159).appendTo(group6);
			$.add(Res.get(Setting.UI_BASE_PRO)).setSize(0,18).setPosition(90, 162).appendTo(group6).setColor(Color.valueOf("717171")).addAction(Actions.delay(0.4f,Actions.sizeTo(((float)parent.current.prop.get("courage")/100f)*414, 18,0.6f,Interpolation.pow4Out)));
			$.add(Res.get(Setting.UI_BASE_IMG)).setSize(420,24).setPosition(87, 121).appendTo(group6);
			$.add(Res.get(Setting.UI_BASE_PRO)).setSize(0,18).setPosition(90, 124).appendTo(group6).setColor(Color.valueOf("717171")).addAction(Actions.delay(0.4f,Actions.sizeTo(((float)parent.current.prop.get("express")/100f)*414, 18,0.6f,Interpolation.pow4Out)));
			$.add(Res.get(Setting.UI_BASE_IMG)).setSize(420,24).setPosition(87, 83).appendTo(group6);
			$.add(Res.get(Setting.UI_BASE_PRO)).setSize(0,18).setPosition(90, 87).appendTo(group6).setColor(Color.valueOf("717171")).addAction(Actions.delay(0.4f,Actions.sizeTo(((float)parent.current.prop.get("respect")/100f)*414, 18,0.6f,Interpolation.pow4Out)));
			$.add(Res.get(Setting.UI_BASE_IMG)).setSize(420,24).setPosition(87, 45).appendTo(group6);
			$.add(Res.get(Setting.UI_BASE_PRO)).setSize(0,18).setPosition(90, 48).appendTo(group6).setColor(Color.valueOf("717171")).addAction(Actions.delay(0.4f,Actions.sizeTo(((float)parent.current.prop.get("perseverance")/100f)*414, 18,0.6f,Interpolation.pow4Out)));
			$.add(Res.get(Setting.UI_BASE_IMG)).setSize(420,24).setPosition(87, 7).appendTo(group6);
			$.add(Res.get(Setting.UI_BASE_PRO)).setSize(0,18).setPosition(90, 10).appendTo(group6).setColor(Color.valueOf("717171")).addAction(Actions.delay(0.4f,Actions.sizeTo(((float)parent.current.prop.get("knowledge")/100f)*414, 18,0.6f,Interpolation.pow4Out)));
			final Image pe_help=(Image) $.add(Res.get(Setting.IMAGE_MENU_NEW_STATUS+"person_help.png")).appendTo(group6).setColor(1,1,1,0).getItem();//resistance help button
			$.add(new CheckBox("", cstyle)).appendTo(group6).setPosition(392,190).run(new GdxQueryRunnable() {public void run(final GdxQuery self) {self.onClick(new Runnable() {public void run() {
				pe_help.addAction(self.isChecked()?Actions.fadeIn(0.3f):Actions.fadeOut(0.3f));
			}});}});
			
		}
		stage.setDebugAll(Setting.persistence.uiDebug);
	}
	

	public int y(int y){
		return (int) (group.getHeight()-y);
	}
	
	private void prev(){
		if(parent.prev())
			generate();
	}
	
	private void next(){
		if(parent.next())
			generate();
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
