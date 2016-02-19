package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;

public class HeroStatusBox extends Group {

	Image bg;
	Label hp, mp;
	Hero hero;
	public Progress hpbar,mpbar;

	public HeroStatusBox(Hero hero) {
		this.hero = hero;
		
		addActor(bg = Res.get(Setting.IMAGE_BATTLE+"herobox.png"));
		addActor(Res.get(Setting.IMAGE_FG+hero.fgname+"/bhead.png"));
//		addActor($.add(Res.get(hero.jname, 26)).setPosition(45,63).setAlpha(.1f).getItem());
//		addActor($.add(Res.get(hero.name, 28)).setPosition(25,73).getItem());
		
		addActor(hpbar = new Progress(Res.getNP(Setting.IMAGE_BATTLE+"bg_00001_00001.png").scale(.5f), Res.getNP(Setting.IMAGE_BATTLE+"hp_00001_00001.png").scale(.5f), 
				Res.getNP(Setting.IMAGE_BATTLE+"cache_00001_00001.png").scale(.5f), 0, hero.getProp("maxhp")));
		hpbar.setPosition(102, 64);
		
		addActor(mpbar = new Progress(Res.getNP(Setting.IMAGE_BATTLE+"bg_00001_00001.png").scale(.5f), Res.getNP(Setting.IMAGE_BATTLE+"mp_00001_00001.png").scale(.5f), 
				Res.getNP(Setting.IMAGE_BATTLE+"cache_00001_00001.png").scale(.5f), 0, hero.getProp("maxmp")));
		mpbar.setPosition(102, 29);
		
		Color shit = new Color(.33f,.16f,.07f,1);
		addActor(hp = Res.get("", 15).position(119, 85).align(Align.left).color(shit));
		addActor(mp = Res.get("", 15).position(119, 50).align(Align.left).color(shit));
		
	}
	
	public HeroStatusBox position(int x,int y){
		setPosition(x, y);
		return this;
	}
	
	@Override
	public void act(float delta) {
		hpbar.value(hero.getProp("hp"));
		mpbar.value(hero.getProp("mp"));
		hp.setText("HP:  "+hero.getProp("hp") + "/" + hero.getProp("maxhp"));
		mp.setText("MP:  "+hero.getProp("mp") + "/" + hero.getProp("maxmp"));
		super.act(delta);
	}
}
