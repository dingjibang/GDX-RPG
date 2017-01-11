package com.rpsg.rpg.object.map;

import java.util.ArrayList;
import java.util.List;

import com.rpsg.rpg.controller.HeroController;
import com.rpsg.rpg.controller.MapController;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.object.hero.Hero;
import com.rpsg.rpg.util.Position;

/**
 * GDX-RPG 人物队列控制器<br>
 * 请在{@link MapController#queue}中访问
 * */
public class PlayerQueue {
	private List<MapSprite> players = new ArrayList<>();
	
	public PlayerQueue() {
		reload();
	}

	/**重新加载player or 坐标，{@link HeroController 外部}也会调用这个方法（当队列发生变化时）*/
	public void reload() {
		players.clear();
		for(Hero hero : Game.archive.get().heros.current())
			players.add(hero.sprite);
		
		//TODO position
		Position position = Game.archive.get().position;
		int foo = position.x;
	}
	
}
