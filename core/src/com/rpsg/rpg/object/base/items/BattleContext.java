package com.rpsg.rpg.object.base.items;

import java.util.List;

import com.rpsg.rpg.object.rpg.Target;

public class BattleContext {
	public Target self;
	public Target enemy;
	public List<Target> friend;
	public List<Target> enemies;

	public BattleContext(Object self, Object enemy, List<?> friend, List<?> enemies) {
		super();
		this.self = Target.parse(self);
		this.enemy = Target.parse(enemy);
		this.friend = Target.parse(friend);
		this.enemies = Target.parse(enemies);
		
		//去重复
		if(enemies.contains(enemy)) enemies.remove(enemy);
		if(friend.contains(self)) friend.remove(self);
	}
	
}
