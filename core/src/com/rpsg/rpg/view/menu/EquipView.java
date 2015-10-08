package com.rpsg.rpg.view.menu;

import java.util.ArrayList;
import java.util.Collections;
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
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.Equipment;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.CheckBox;
import com.rpsg.rpg.system.ui.CheckBox.CheckBoxStyle;
import com.rpsg.rpg.system.ui.DefaultIView;
import com.rpsg.rpg.system.ui.Icon;
import com.rpsg.rpg.system.ui.ImageButton;
import com.rpsg.rpg.system.ui.ImageList;
import com.rpsg.rpg.utils.game.GameUtil;

public class EquipView extends DefaultIView{
	
	List<Hero> heros;
	Group inner,data;
	ImageList ilist;
	public EquipView init() {
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()),MenuView.stage.getBatch());
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(137,79).setColor(0,0,0,0.52f).setPosition(240,470).appendTo(stage);
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(680,48).setColor(0,0,0,0.85f).setPosition(377,486).appendTo(stage);
		CheckBoxStyle cstyle=new CheckBoxStyle();
		cstyle.checkboxOff=Res.getDrawable(Setting.IMAGE_MENU_NEW_EQUIP+"info.png");
		cstyle.checkboxOn=Res.getDrawable(Setting.IMAGE_MENU_NEW_EQUIP+"info_p.png");// help button press
		$.add(new CheckBox("", cstyle, 1)).appendTo(stage).setPosition(880,486).run(new GdxQueryRunnable() {public void run(final GdxQuery self) {self.onClick(new Runnable() {public void run() {
			data.addAction(self.isChecked()?Actions.fadeIn(0.3f):Actions.fadeOut(0.3f));
		}});}});
		
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(755,282).setColor(.2f,.2f,.2f,0.85f).setPosition(240,178).appendTo(stage);
		
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(155,155).setColor(0,0,0,0.55f).setPosition(240,12).appendTo(stage);
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(597,103).setColor(0,0,0,0.55f).setPosition(398,64).appendTo(stage);
		
		
		$.add(new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_NEW_GLOBAL+"button.png"),Setting.UI_BUTTON).setFg(Res.get(Setting.IMAGE_MENU_NEW_EQUIP+"but_take.png"))).onClick(new Runnable() {public void run() {
			useEquip();//TODO
		}}).appendTo(stage).setSize(297,49).setPosition(398, 12).getCell().prefSize(297,49);
		$.add(new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_NEW_GLOBAL+"button.png"),Setting.UI_BUTTON).setFg(Res.get(Setting.IMAGE_MENU_NEW_EQUIP+"but_remove.png"))).onClick(new Runnable() {public void run() {
			removeEquip();//TODO
		}}).appendTo(stage).setSize(297,49).setPosition(698, 12).getCell().prefSize(297,49);
		
		$.add(Res.get(Setting.IMAGE_MENU_NEW_GLOBAL+"m_right.png")).appendTo(stage).setScale(.8f).setPosition(367, 483).onClick(new Runnable() {public void run() {
			next();
		}}).addAction(Actions.fadeIn(0.2f)).setColor(1,1,1,0);
		$.add(Res.get(Setting.IMAGE_MENU_NEW_GLOBAL+"m_right.png")).setScale(.8f).setScaleX(-.8f).appendTo(stage).setPosition(196, 483).onClick(new Runnable() {public void run() {
			prev();
		}}).addAction(Actions.fadeIn(0.2f)).setColor(1,1,1,0);
		
		inner = (Group) $.add(new Group()).appendTo(stage).getItem();
		data=(Group) $.add(new Group()).setColor(1,1,1,0).appendTo(stage).getItem();
		generate();
		
		return this;
	}
	
	private void generate() {
		inner.clear();
		data.clear();
		$.add(Res.get(Setting.IMAGE_MENU_NEW_EQUIP+"data.png")).setSize(187, 312).setPosition(838,174).appendTo(data);
		
		ilist=new ImageList(getEquips(Equipment.EQUIP_SHOES));
		ilist.setSize(738, 282);
		ilist.setPosition(248, 178);
		ilist.generate();
		
		inner.addActor(ilist);
		
	}
	
	private List<Icon> getEquips(String equipType){
		List<Equipment> equips=RPG.ctrl.item.search(Equipment.class.getSimpleName(),Equipment.class);
		List<Icon> io= new ArrayList<>();
		for(Equipment e:equips){
			if(!e.equipType.equalsIgnoreCase(equipType))
				continue;
			Icon obj=new Icon().generateIcon(e, true);
			if(e.onlyFor!=null && !e.onlyFor.equals(parent.getClass()))//filter by hero class
				obj.enable=false;
			io.add(obj);
		}
		Collections.sort(io);//将无效的内容放在后面
		return io;
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
