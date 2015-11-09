package com.rpsg.rpg.system.ui;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;

public class HeroSelectBox extends Group {
	
	private Hero hero;
	private boolean forAllHeros = false;
	
	public HeroSelectBox(int width, int height, boolean forAllHeros) {
		size(width, height);
		sizeChanged();
		this.forAllHeros = forAllHeros;
		generate();
	}
	
	public HeroSelectBox generate(){
		clear();
		
		addActor(Res.get(Setting.UI_BASE_IMG).size((int)getWidth(),(int)getHeight()).a(.15f));
		
		int margin = 10;
		int w = (int) ((getWidth()-margin*3)/2);
		int h = (int) ((getHeight()-margin*3)/2);
		
		List<Hero> heros = RPG.ctrl.hero.heros;
		generateHeroBox(margin, h+margin*2, w, h,heros.size()>0?heros.get(0):null);
		generateHeroBox(w+margin*2, h+margin*2, w, h,heros.size()>1?heros.get(1):null);
		generateHeroBox(margin, margin, w, h,heros.size()>2?heros.get(2):null);
		generateHeroBox(w+margin*2, margin, w, h,heros.size()>3?heros.get(3):null);
		
		if(forAllHeros){
			addActor(Res.get(Setting.UI_BASE_IMG).size((int)getWidth(),(int)getHeight()).color(Color.valueOf("33333399")));
			addActor(new Label("全体使用的",40).align(0, 0).width((int)getWidth()).height((int)getHeight()));
		}
		
		return this;
	}
	
	
	private HeroSelectBox generateHeroBox(int x,int y,int w,int h,final Hero hero){
		addActor(Res.get(Setting.UI_BASE_IMG).size(w,h).a(.15f).position(x, y).object(hero==null?"nullHero":hero).onClick(new Runnable() {
			public void run() {
				set(hero);
			}
		}));
		if(hero!=null){
			addActor(new HeroImage(hero,0).position(x+10, y+10));
			addActor(new Label(hero.name,20).position(x+65, y+53));
			addActor(Res.get(Setting.UI_BASE_IMG).size(141, 42).position(x+65, y+8).disableTouch());
			addActor(Res.get(Setting.UI_BASE_PRO).size((int)((float)hero.prop.get("hp")/(float)hero.prop.get("maxhp")*137), 18).position(x+67, y+30).color(Color.valueOf("c33737")).disableTouch());
			addActor(Res.get(Setting.UI_BASE_PRO).size((int)((float)hero.prop.get("mp")/(float)hero.prop.get("maxmp")*137), 18).position(x+67, y+10).color(Color.valueOf("3762c3")).disableTouch());
			addActor(new Label(hero.prop.get("hp")+"/"+hero.prop.get("maxhp"),14).width(130).align(x+67, y+30).color(Color.LIGHT_GRAY));
			addActor(new Label(hero.prop.get("mp")+"/"+hero.prop.get("maxmp"),14).width(130).align(x+67, y+10).color(Color.LIGHT_GRAY));
		}
//		$.add(this).children().setDebug(true);
		$.add(this).children().find(Label.class).setTouchable(Touchable.disabled);
		return this;
	}
	
	public HeroSelectBox position(int x,int y){
		setPosition(x, y);
		return this;
	}
	
	public Hero get(){
		return hero;
	}
	
	public HeroSelectBox set(Hero hero){
		if(hero==null)
			return this;
		this.hero = hero;
		$.add(this).children().findUserObjectInstanceOf(String.class,Hero.class).setAlpha(.15f).cleanActions().getFather().find(hero).setAlpha(.4f).addAction(Actions.forever(Actions.sequence(Actions.alpha(.2f,.2f),Actions.alpha(.5f,.2f))));
		return this;
	}

	public HeroSelectBox size(int width, int height) {
		setSize(width, height);
		return this;
	}
}
