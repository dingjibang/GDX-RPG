package com.rpsg.rpg.view.menu;

import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.gdxQuery.GdxQueryRunnable;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.CheckBox;
import com.rpsg.rpg.system.ui.CheckBox.CheckBoxStyle;
import com.rpsg.rpg.system.ui.DefaultIView;
import com.rpsg.rpg.system.ui.ImageButton;
import com.rpsg.rpg.utils.game.GameUtil;

public class EquipView extends DefaultIView{
	
	List<Hero> heros;
	Group inner;
	public EquipView init() {
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()),MenuView.stage.getBatch());
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(137,79).setColor(0,0,0,0.52f).setPosition(240,470).appendTo(stage);
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(680,48).setColor(0,0,0,0.85f).setPosition(377,486).appendTo(stage);
		CheckBoxStyle cstyle=new CheckBoxStyle();
		cstyle.checkboxOff=Res.getDrawable(Setting.IMAGE_MENU_NEW_EQUIP+"info.png");
		cstyle.checkboxOn=Res.getDrawable(Setting.IMAGE_MENU_NEW_EQUIP+"info_p.png");// help button press
		$.add(new CheckBox("", cstyle, 1)).appendTo(stage).setPosition(880,486).run(new GdxQueryRunnable() {public void run(final GdxQuery self) {self.onClick(new Runnable() {public void run() {
//			phelp.addAction(self.isChecked()?Actions.fadeIn(0.3f):Actions.fadeOut(0.3f)); TODO LOGIC for info button
		}});}});
		
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(755,282).setColor(0,0,0,0.85f).setPosition(240,178).appendTo(stage);
		
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(155,155).setColor(0,0,0,0.55f).setPosition(240,12).appendTo(stage);
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(597,103).setColor(0,0,0,0.55f).setPosition(398,64).appendTo(stage);
		
		
		$.add(new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_NEW_GLOBAL+"button.png"),Setting.UI_BUTTON).setFg(Res.get(Setting.IMAGE_MENU_NEW_EQUIP+"but_take.png"))).onClick(new Runnable() {public void run() {
			useEquip();//TODO
		}}).appendTo(stage).setSize(297,49).setPosition(398, 12).getCell().prefSize(297,49);
		$.add(new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_NEW_GLOBAL+"button.png"),Setting.UI_BUTTON).setFg(Res.get(Setting.IMAGE_MENU_NEW_EQUIP+"but_remove.png"))).onClick(new Runnable() {public void run() {
			removeEquip();//TODO
		}}).appendTo(stage).setSize(297,49).setPosition(698, 12).getCell().prefSize(297,49);
		
		$.add(Res.get(Setting.IMAGE_MENU_NEW_GLOBAL+"m_right.png")).appendTo(stage).setScale(.8f).setPosition(570, 150).onClick(new Runnable() {public void run() {
			next();
		}}).addAction(Actions.fadeIn(0.2f)).setColor(1,1,1,0);
		$.add(Res.get(Setting.IMAGE_MENU_NEW_GLOBAL+"m_right.png")).setScale(.8f).setScaleX(-.8f).appendTo(stage).setPosition(36, 150).onClick(new Runnable() {public void run() {
			prev();
		}}).addAction(Actions.fadeIn(0.2f)).setColor(1,1,1,0);
		
		inner = new Group();
		generate();
		
		return this;
	}
	
	private void generate() {
		inner.clear();
		
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
	
	private void useEquip(){
		
	}
	
	private void removeEquip(){
		
	}

	public void logic() {
		stage.act();
	}
	
	public void onkeyDown(int keyCode) {
		if(Keys.ESCAPE==keyCode || Keys.X==keyCode)
			this.disposed=true;
		stage.keyDown(keyCode);
	}

}
