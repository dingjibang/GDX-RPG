package com.rpsg.rpg.object.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.ScriptableObject;

import com.badlogic.gdx.Gdx;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.Cooking;
import com.rpsg.rpg.object.base.items.Equipment;
import com.rpsg.rpg.object.base.items.Important;
import com.rpsg.rpg.object.base.items.Item;
import com.rpsg.rpg.object.base.items.Material;
import com.rpsg.rpg.object.base.items.Medicine;
import com.rpsg.rpg.object.base.items.SpellCard;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.NPC;
import com.rpsg.rpg.utils.game.AchievementManager;
import com.rpsg.rpg.utils.game.GameDate;

/**
 * RPG全局变量类，存储了游戏内所需保存的变量、对象等。持久化本类即为保存游戏，反持久化则为读取游戏。<br>
 * 如果需要更改初始设置（如初始坐标、初始金钱、初始装备等），请勿修改本类，您需要修改 [ROOT]/android/assets/script/system/global.js 这个文件，在游戏第一次运行时会读取本脚本进行注入。
 * @author dingjibang
 */
public class Global implements Serializable {
	private static final long serialVersionUID = 1L;
	// 初始地图
	public String map;
	
	// 地图相关
	public ArrayList<NPC> npcs = new ArrayList<NPC>();
	public ArrayList<Hero> currentHeros = new ArrayList<Hero>();
	public ArrayList<Hero> heros = new ArrayList<Hero>();
	public ArrayList<Hero> support = new ArrayList<Hero>();
	public int x;
	public int y;
	public int z;

	public boolean first = true;

	// 时间模块
	public GameDate date = new GameDate();
	public int weather;

	// 金钱
	public int gold;
	
	//全局变量存储
	public Map<Object,Object> flag = new HashMap<Object,Object>();

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
	
	public void read(){
		Context ctx = Context.enter();
		ScriptableObject scope =ctx.initStandardObjects();
		scope.setPrototype(((NativeJavaObject)Context.javaToJS(Global.this, scope)));
		ctx.evaluateString(scope, Gdx.files.internal(Setting.SCRIPT_SYSTEM+"global.js").readString(), null, 1, null);
		Context.exit();
	}
}
