package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;

public class HeroStatusBox extends Group {

	Image bg;
	Label hp, mp;
	Image hpbox, mpbox;
	Hero hero;
	Image timer;

	public HeroStatusBox(Hero hero) {
		this.hero = hero;
		int _hp = hero.getProp("hp"),_mp = hero.getProp("mp"),maxhp = hero.getProp("maxhp"),maxmp = hero.getProp("maxmp");
		
		addActor(bg = Res.get(Setting.UI_BASE_IMG).size(256, 115).color(0,0,0,.3f));
		addActor($.add(Res.get(hero.jname, 26)).setPosition(45,63).setAlpha(.1f).getItem());
		addActor($.add(Res.get(hero.name, 28)).setPosition(25,73).getItem());
		addActor(Res.get(Setting.UI_BASE_IMG).size(3, 103).position(10, 7));
		
		addActor(timer = Res.get(Setting.UI_BASE_IMG).size(3, 103).position(10, 7).color(hero.getObjectColor()));
		
		addActor(Res.get(Setting.UI_BASE_IMG).size(218, 10).position(25, 47));
		addActor(hpbox = Res.get(Setting.UI_BASE_IMG).size(218, 10).position(25, 47).color(Color.valueOf("dc3c3c")));
		
		addActor(Res.get(Setting.UI_BASE_IMG).size(218, 10).position(25, 13));
		addActor(mpbox = Res.get(Setting.UI_BASE_IMG).size(218, 10).position(25, 13).color(Color.valueOf("396da8")));
		
		addActor(hp = Res.get(_hp + " / " + maxhp, 18).position(169, 58).align(Align.right));
		addActor(mp = Res.get(_mp + " / " + maxmp, 18).position(169, 23).align(Align.right));
	}
	
	public HeroStatusBox position(int x,int y){
		setPosition(x, y);
		return this;
	}
}
