package com.rpsg.rpg.system.ui;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.Selectable;
import com.rpsg.rpg.object.rpg.Target;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.utils.game.GameUtil;

public class HeroStatusGroup extends Group implements Selectable{

	boolean select = false;
	CustomRunnable<Target> onSelect = null;
	
	public HeroStatusGroup(ArrayList<Hero> heros) {
		$.add(Res.get(Setting.UI_BASE_IMG).size(GameUtil.screen_width,115).color(0,0,0,.5f)).setPosition(0, 28).appendTo(this);
		for(int i = 0; i < heros.size(); i++)
			addActor($.add(new HeroStatusBox(heros.get(i)).position(i * 256, 28)).transform(false).getItem());
	}
	
	public HeroStatusBox getBox(Hero hero){
		HeroStatusBox box = null;
		
		for(Actor actor : $.add(this).children().getItems())
			if(actor instanceof HeroStatusBox && ((HeroStatusBox)actor).hero == hero)
				box = ((HeroStatusBox)actor);
		
		return box;
	}

	public void select(CustomRunnable<Target> onSelect){
		select = true;
		this.onSelect = onSelect; 
	}
	
	@Override
	public void act(float delta) {
		if(select && onSelect != null){
			select = false;
			GdxQuery child = $.add(this).children(HeroStatusBox.class);
			child.each(e -> ((HeroStatusBox) e).select(select ->{
				child.each(enemyBox -> ((HeroStatusBox) enemyBox).stopSelect());
				onSelect.run(select.hero.target);
			}));
		}
		super.act(delta);
	}
}
