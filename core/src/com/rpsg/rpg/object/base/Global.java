package com.rpsg.rpg.object.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.rpsg.rpg.game.achievement.AchievementManager;
import com.rpsg.rpg.object.base.items.*;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.NPC;
import com.rpsg.rpg.utils.display.WeatherUtil;
import com.rpsg.rpg.utils.game.GameDate;

public class Global implements Serializable {
	private static final long serialVersionUID = 1L;
	// 初始地图
	public String map = "test/inner.tmx";
	// 地图相关
	public ArrayList<NPC> npcs = new ArrayList<NPC>();
	public ArrayList<Hero> currentHeros = new ArrayList<Hero>();
	public ArrayList<Hero> heros = new ArrayList<Hero>();
	public ArrayList<Hero> support = new ArrayList<Hero>();
	public int x = 15;
	public int y = 6;
	public int z = 3;

	public boolean first = true;

	// 时间模块
	public GameDate date = new GameDate();
	public int weather = WeatherUtil.WEATHER_NO;

	// 金钱
	public int gold = 120;

	// 物品、装备等道具

	public Map<String, List<? extends Item>> items = new HashMap<String, List<? extends Item>>();
	{
		if (items.isEmpty()) {
			items.put("equipment", new LinkedList<Equipment>());
			items.put("important", new LinkedList<Important>());
			items.put("material", new LinkedList<Material>());
			items.put("medicine", new LinkedList<Medicine>());
			items.put("spellcard", new LinkedList<SpellCard>());
			items.put("cooking", new LinkedList<Cooking>());
		}
	}
	public AchievementManager ach = new AchievementManager();

	public void $(String s) {
		// global.$("gold+10000") global.$("gold.set(10000)")
		// global.$("items.add(YaoWan,3)")
		try {
			String field = "";
			if (s.contains("+")) {
				field = s.substring(0, s.indexOf("+"));
				Field f = this.getClass().getField(field);
				f.setAccessible(true);
				f.set(this,
						(int) f.get(this)
								+ Integer.parseInt(s.substring(s.indexOf("+") + 1)));
			} else if (s.contains("-")) {
				field = s.substring(0, s.indexOf("-"));
				Field f = this.getClass().getField(field);
				f.setAccessible(true);
				f.set(this,
						(int) f.get(this)
								- Integer.parseInt(s.substring(s.indexOf("-") + 1)));
			} else if (s.contains(".set(")) {

			}

			AchievementManager.determine(field);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(String field, String action) { // global.update("gold","+100000")
		try {
			Field f = this.getClass().getField(field);
			int i = (int) f.get(this);
			f.setAccessible(true);
			if (action.charAt(0) == '+') {
				i += Integer.parseInt(action.substring(1));
			} else if (action.charAt(0) == '-') {
				i -= Integer.parseInt(action.substring(1));
			} else {
				i = Integer.parseInt(action);
			}
			f.set(this, i);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AchievementManager.determine(field);
	}

	@SuppressWarnings("unchecked")
	public List<Item> getItems(String name) {
		return (List<Item>) items.get(name);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getItems(Class<T> c) {
		return (List<T>) items.get(c.getSimpleName().toLowerCase());
	}
}
