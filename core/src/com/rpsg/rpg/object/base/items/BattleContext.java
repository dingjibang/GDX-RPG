package com.rpsg.rpg.object.base.items;

import java.util.List;

import com.rpsg.rpg.object.rpg.Target;

public class BattleContext {
	public Target self;
	public Target enemy;
	public List<Target> friend;
	public List<Target> enemies;

	public BattleContext(Target self, Target enemy, List<Target> friend, List<Target> enemies) {
		super();
		this.self = self;
		this.enemy = enemy;
		this.friend = friend;
		this.enemies = enemies;
		
		//去重复
		if(enemies.contains(enemy)) enemies.remove(enemy);
		if(friend.contains(self)) friend.remove(self);
	}
	
}
