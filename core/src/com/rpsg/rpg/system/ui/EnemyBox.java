package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Enemy;
import com.rpsg.rpg.system.base.Res;

public class EnemyBox extends Group {

	Image image;
	public Enemy enemy;
	Label name,hp;
	Image hpbox;

	public EnemyBox(Enemy enemy) {
		this.enemy = enemy;
		int _hp = Integer.valueOf(enemy.prop.get("hp")),_maxhp = Integer.valueOf(enemy.prop.get("maxhp"));
		
		Group fg = new Group();
		fg.addActor(image = Res.getNP(enemy.imgPath));
		fg.addActor(hp = Res.get(_hp + " / " + _maxhp, 18).align(Align.center));
		hp.position((int)(getWidth()/2 - hp.getWidth()/2), (int)getHeight()+40);
		
		fg.addActor(Res.get(Setting.UI_BASE_IMG).size(130, 10).position(getWidth()/2 - 130/2, getHeight()+20));
		fg.addActor(hpbox = Res.get(Setting.UI_BASE_IMG).size(130, 10).position(getWidth()/2 - 130/2, getHeight()+20).color(Color.valueOf("dc3c3c")));
		
		
		$.add(fg).addAction(Actions.forever(Actions.sequence(Actions.moveBy(0,10,3f,Interpolation.pow2),Actions.moveBy(0, -10,3)))).appendTo(this);
		
		
//		addActor($.add(Res.get(hero.name, 28)).setPosition(25,73).getItem());
//		
//		addActor(timer = Res.get(Setting.UI_BASE_IMG).size(3, 103).position(10, 7));
//		
//		
//		addActor(Res.get(Setting.UI_BASE_IMG).size(218, 10).position(25, 13));
//		addActor(mpbox = Res.get(Setting.UI_BASE_IMG).size(218, 10).position(25, 13).color(Color.valueOf("396da8")));
//		
//		addActor(mp = Res.get(_mp + " / " + maxmp, 18).position(169, 23).align(Align.right));
	}
	
	public EnemyBox position(float x,float y){
		setPosition(x, y);
		return this;
	}
	
	@Override
	public float getWidth() {
		return image.getWidth();
	}
	
	@Override
	public float getHeight() {
		return image.getHeight();
	}
}
