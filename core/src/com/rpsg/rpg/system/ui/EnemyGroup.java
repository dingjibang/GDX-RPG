package com.rpsg.rpg.system.ui;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.rpg.object.rpg.Enemy;

public class EnemyGroup extends Table {

	boolean select = false;
	CustomRunnable<Enemy> onSelect = null;

	public EnemyGroup(int param) {
		$.each(Enemy.name(Enemy.get(param)), e -> add(new EnemyBox(e)).padLeft(35).padRight(35));
	}
	
	
	public EnemyGroup position(float x,float y){
		setPosition(x, y);
		return this;
	}
	
	public void select(CustomRunnable<Enemy> onSelect){
		select = true;
		this.onSelect = onSelect; 
	}
	
	public List<Enemy> list(){
		return $.add(this).children().find(EnemyBox.class).eachAsList(a -> ((EnemyBox)a).enemy, Enemy.class);
	}
	
	@Override
	public void act(float delta) {
		if(select && onSelect != null){
			select = false;
			
		}
		super.act(delta);
	}
}
