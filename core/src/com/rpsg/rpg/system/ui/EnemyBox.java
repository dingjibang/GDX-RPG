package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Enemy;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;

public class EnemyBox extends Group {

	Image image;
	public Enemy enemy;
	Label name,hp;
	Image hpbox;

	public EnemyBox(Enemy enemy) {
		this.enemy = enemy;
		int _hp = Integer.valueOf(enemy.prop.get("hp")),_maxhp = Integer.valueOf(enemy.prop.get("maxhp"));
		
		addActor(image = Res.get(enemy.imgPath).action(Actions.forever(Actions.parallel(Actions.moveBy(50,0,1f),Actions.moveBy(-50, 0,1)))));
		
//		addActor($.add(Res.get(hero.jname, 26)).setPosition(45,63).setAlpha(.1f).getItem());
//		addActor($.add(Res.get(hero.name, 28)).setPosition(25,73).getItem());
//		addActor(Res.get(Setting.UI_BASE_IMG).size(3, 103).position(10, 7));
//		
//		addActor(timer = Res.get(Setting.UI_BASE_IMG).size(3, 103).position(10, 7));
//		
//		addActor(Res.get(Setting.UI_BASE_IMG).size(218, 10).position(25, 47));
//		addActor(hpbox = Res.get(Setting.UI_BASE_IMG).size(218, 10).position(25, 47).color(Color.valueOf("dc3c3c")));
//		
//		addActor(Res.get(Setting.UI_BASE_IMG).size(218, 10).position(25, 13));
//		addActor(mpbox = Res.get(Setting.UI_BASE_IMG).size(218, 10).position(25, 13).color(Color.valueOf("396da8")));
//		
//		addActor(hp = Res.get(_hp + " / " + maxhp, 18).position(169, 58).align(Align.right));
//		addActor(mp = Res.get(_mp + " / " + maxmp, 18).position(169, 23).align(Align.right));
	}
	
	public EnemyBox position(int x,int y){
		setPosition(x, y);
		return this;
	}
}
