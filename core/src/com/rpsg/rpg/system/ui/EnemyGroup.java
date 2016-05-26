package com.rpsg.rpg.system.ui;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.rpg.object.base.items.Item.ItemDeadable;
import com.rpsg.rpg.object.rpg.Enemy;
import com.rpsg.rpg.object.rpg.Selectable;
import com.rpsg.rpg.object.rpg.Target;

public class EnemyGroup extends Table implements Selectable{

	boolean select = false;
	ItemDeadable deadable = ItemDeadable.all;
	CustomRunnable<Target> onSelect = null;

	public EnemyGroup(int param) {
		$.each(Enemy.name(Enemy.get(param)), e -> add(new EnemyBox(e)).padLeft(35).padRight(35));
	}
	
	
	public EnemyGroup position(float x,float y){
		setPosition(x, y);
		return this;
	}
	
	public void select(CustomRunnable<Target> onSelect,ItemDeadable deadable){
		select = true;
		this.onSelect = onSelect; 
		this.deadable = deadable;
	}
	
	public ArrayList<Enemy> list(){
		return $.add(this).children().find(EnemyBox.class).eachAsList(e -> ((EnemyBox)e).enemy, Enemy.class);
	}
	
	@Override
	public void act(float delta) {
		if(select && onSelect != null){
			select = false;
			$.add(this).children().each(e -> ((EnemyBox) e).select(select ->{
				$.add(this).children().each(enemyBox -> ((EnemyBox) enemyBox).stopSelect());
				onSelect.run(select.enemy.target);
			},deadable));
		}
		super.act(delta);
	}


	public void remove(Enemy enemy) {
		EnemyBox box = getBox(enemy);
		if(box != null){
			Cell<?> cell = null;
			for(Cell<?> c : getCells())
				if(c.getActor() == box)
					cell = c;
			if(cell != null){
				removeActor(box);
				getCells().removeValue(cell, true);
			}
		}
		
	}
	
	/**
	 *	get EnemyBox by enemy(maybe null~) 
	 */
	public EnemyBox getBox(Enemy enemy){
		EnemyBox box = null;
		
		for(Actor a : $.add(this).children().getItems())
			if(((EnemyBox)a).enemy == enemy)
				box = ((EnemyBox)a);
		
		return box;
	}


}
