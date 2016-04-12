package com.rpsg.rpg.system.ui;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
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
		return $.add(this).children().find(EnemyBox.class).eachAsList(e -> ((EnemyBox)e).enemy, Enemy.class);
	}
	
	@Override
	public void act(float delta) {
		if(select && onSelect != null){
			select = false;
			$.add(this).children().each(e -> ((EnemyBox) e).select((select)->{
				$.add(this).children().each(enemyBox -> ((EnemyBox) enemyBox).stopSelect());
				onSelect.run(select.enemy);
			}));
		}
		super.act(delta);
	}


	public void remove(Enemy enemy) {
		EnemyBox box = null;
		
		for(Actor a : $.add(this).children().getItems())
			if(((EnemyBox)a).enemy == enemy)
				box = ((EnemyBox)a);
		
		if(box != null)
			removeActor(box);
	}
}
