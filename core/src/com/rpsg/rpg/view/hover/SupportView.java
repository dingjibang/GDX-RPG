package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;

public class SupportView extends SidebarView {
	Hero hero;
	public void init() {
		Hero hero = (Hero) param.get("hero");
		if(hero.support!=null){
			$.add(Res.get(Setting.IMAGE_FG+hero.fgname+"/Normal.png").position(1150, 0).scaleY(0.35f).color(1,1,1,.5f).scaleX(-0.35f)).setOrigin(Align.bottomLeft).appendTo(group);
			$.add(Res.get(hero.jname,43)).setPosition(540, 440).setColor(1, 1, 1, .2f).appendTo(group);
			$.add(Res.get("角色等级"+hero.prop.get("level")+"，社群("+hero.association.name+")等级"+hero.association.level,36)).setPosition(430, 380).setColor(1, 1, 1, .7f).appendTo(group);
			$.add(Res.get(hero.support.name,50)).setPosition(430, 250).setColor(0.15f,0.76f,0.22f).appendTo(group);
			$.add(Res.get(hero.support.description,20).warp(true)).setPosition(480, 190).appendTo(group).setWidth(510);
			
		}
	}
	
}
