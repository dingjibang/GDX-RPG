package com.rpsg.rpg.system.ui;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.Buff;
import com.rpsg.rpg.object.base.items.Item.ItemDeadable;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.LazyBitmapFontConctoller;
import com.rpsg.rpg.view.hover.ViewBuffView;

public class HeroStatusBox extends WidgetGroup {

	Image bg;
	Label hp, mp;
	Hero hero;
	public Progress hpbar,mpbar;
	Group buffTable = new Group();
	Image selectBox = new Image(Setting.UI_BUTTON);

	public HeroStatusBox(Hero hero) {
		this.hero = hero;
		
		addActor(bg = Res.get(Setting.IMAGE_BATTLE+"herobox.png"));
		addActor(Res.get(Setting.IMAGE_FG+hero.fgname+"/bhead.png"));
//		addActor($.add(Res.get(hero.jname, 26)).setPosition(45,63).setAlpha(.1f).getItem());
//		addActor($.add(Res.get(hero.name, 28)).setPosition(25,73).getItem());
		
		addActor(hpbar = new Progress(Res.getNP(Setting.IMAGE_BATTLE+"bg_00001_00001.png").scale(.5f), Res.getNP(Setting.IMAGE_BATTLE+"hp_00001_00001.png").scale(.5f), 
				Res.getNP(Setting.IMAGE_BATTLE+"cache_00001_00001.png").scale(.5f), 0, hero.target.getProp("maxhp")));
		hpbar.setPosition(102, 64);
		
		addActor(mpbar = new Progress(Res.getNP(Setting.IMAGE_BATTLE+"bg_00001_00001.png").scale(.5f), Res.getNP(Setting.IMAGE_BATTLE+"mp_00001_00001.png").scale(.5f), 
				Res.getNP(Setting.IMAGE_BATTLE+"cache_00001_00001.png").scale(.5f), 0, hero.target.getProp("maxmp")));
		mpbar.setPosition(102, 29);
		
		Color shit = new Color(.33f,.16f,.07f,1);
		addActor(hp = Res.font.getLabel("", 20,LazyBitmapFontConctoller.ENGLISH_GENERATOR).position(119, 85).align(Align.left).color(shit));
		addActor(mp = Res.font.getLabel("", 20,LazyBitmapFontConctoller.ENGLISH_GENERATOR).position(119, 50).align(Align.left).color(shit));
		addActor(buffTable);
		addActor($.add(selectBox).hide().getItem());
		
	}
	
	public HeroStatusBox position(int x,int y){
		setPosition(x, y);
		return this;
	}
	
	@Override
	public void act(float delta) {
		hpbar.value(hero.target.getProp("hp"));
		mpbar.value(hero.target.getProp("mp"));
		hp.setText("HP:  "+hero.target.getProp("hp") + "/" + hero.target.getProp("maxhp"));
		mp.setText("MP:  "+hero.target.getProp("mp") + "/" + hero.target.getProp("maxmp"));
		
		if(hero.target.modifiedBuff()){
			buffTable.clear();
			List<Buff> list = hero.target.getBuffList();
			list.addAll(hero.target.getCallbackBuffList());
			for (int i = 0; i < list.size(); i++) {
				Buff buff = list.get(i);
				Image icon = buff.getIcon();
				buffTable.addActor(icon.x(i * 40).size(32,32));
				icon.addListener(new InputListener(){
					public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
						super.enter(event, x, y, pointer, fromActor);
					}
					@Override
					public boolean mouseMoved(InputEvent event, float x, float y) {
						RPG.popup.add(ViewBuffView.class,$.omap("top",event.getStageY()).add("left",event.getStageX()).add("buff",buff));
						return super.mouseMoved(event, x, y);
					}
					public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
						super.exit(event, x, y, pointer, toActor);
					}
				});
			}
			
		}
		
		super.act(delta);
	}

	public void select(CustomRunnable<HeroStatusBox> callback, ItemDeadable deadable) {
		boolean isDead = hero.target.isDead();
		if(deadable == ItemDeadable.all || (isDead && deadable == ItemDeadable.yes) || (!isDead && deadable == ItemDeadable.no))
		$.add(selectBox).show().click(() -> {
			callback.run(HeroStatusBox.this);
		});
	}
	
	public void stopSelect(){
		$.add(selectBox).hide();
		selectBox.clearListeners();
	}
	
}
