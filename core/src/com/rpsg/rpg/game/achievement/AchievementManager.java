package com.rpsg.rpg.game.achievement;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.utils.game.AchUtil;

public class AchievementManager implements Serializable {

	private static final long serialVersionUID = 1L;

	public static List<Class<?>> Ach = new ArrayList<Class<?>>();
	public static List<String> compare;
	public static TreeMap<String, List<Class<?>>> judge = new TreeMap<String, List<Class<?>>>();

	public AchievementManager() { // 初始化时获取所有的成就对象
		try {
			for (Class<?> c : AchUtil
					.getAllAssignedClass(BaseAchievement.class)) {
				Ach.add(c);
				System.out.println(c.getName());
			}
			compareupdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void compareupdate() throws Exception {
		for (Class<?> c : Ach) {
			Field field = c.getField("compare");
			String compare = (String) field.get(c.getClass());
			Field field1 = c.getField("status");
			Integer status = (Integer) field1.get(c.getClass());

			if (status == 0) {
				if (judge.containsKey(compare)) {
					judge.get(compare).add(c);
				} else {
					List<Class<?>> l = new ArrayList<Class<?>>();
					l.add(c);
					judge.put(compare, l);
				}
			}
		}
	}

	public static void determine(Global g, String comparefield) {
		for (Class<?> c : judge.get(comparefield)) {
			try {
				Method m = c.getMethod(comparefield, Global.class);
				m.invoke(null, g);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * public static void main(String arg[]) { new AchievementManager();
	 * Set<String> keySet = judge.keySet(); for (String keyName : keySet) {
	 * System.out.println("键名：" + keyName);
	 * System.out.println(judge.get(keyName));
	 * 
	 * } }
	 */
}
