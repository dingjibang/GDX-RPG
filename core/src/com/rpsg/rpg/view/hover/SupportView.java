package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;

public class SupportView extends SidebarView {
	Hero hero;
	public SupportView(Hero hero){
		this.hero=hero;
	}
	WidgetGroup group;
	public void init() {
		if(hero.support!=null){
			$.add(Res.get(Setting.IMAGE_FG+hero.fgname+"/Normal.png").position(1100, 0).scaleY(0.35f).color(1,1,1,.5f).scaleX(-0.35f)).setOrigin(Align.bottomLeft).appendTo(group);
			group.addActor($.add(Res.get(hero.name,55)).setPosition(490, 520).getItem());
			group.addActor($.add(Res.get(hero.jname,43)).setPosition(560, 490).setColor(1, 1, 1, .3f).getItem());
			group.addActor($.add(Res.get("角色等级"+hero.prop.get("level")+"，社群("+hero.association.name+")等级"+hero.association.level,36)).setPosition(420, 440).setColor(1, 1, 1, .7f).getItem());
			group.addActor($.add(Res.get(hero.support.name,50)).setPosition(430, 280).setColor(hero.support.r, hero.support.g, hero.support.b, hero.support.a).getItem());
			group.addActor($.add(Res.get(hero.support.illustration,20)).setPosition(480, 210).getItem());
		}
	}
	
}
