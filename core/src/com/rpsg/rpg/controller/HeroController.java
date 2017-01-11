package com.rpsg.rpg.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.rpsg.rpg.object.hero.Hero;
import com.rpsg.rpg.util.Stream;

/**
 * {@link Hero} 控制器
 */
@SuppressWarnings("unchecked")
public class HeroController implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**所有Hero，同时包含了{@link #current}，只是被加载过的*/
	private ArrayList<Hero> all = new ArrayList<>();
	/**当前在队伍中的Hero*/
	private ArrayList<Hero> current = new ArrayList<>();
	
	/**在队伍中新加入一名{@link Hero}*/
	public void add(int id, int index) {
		current.add(index, create(id));
		
		if(current.size() > 4)
			current.remove(current.size() - 1);
	}
	
	/**在队伍中新加入一名{@link Hero}*/
	public void add(int id) {
		add(id, 0);
	}
	
	/**根据ID从当前队伍中删除一名{@link Hero}*/
	public void removeById(int id) {
		current.remove(find(current, id));
	}
	
	/**根据位置从当前队伍中删除一名{@link Hero}*/
	public void removeByIndex(int index) {
		current.remove(index);
	}
	
	/**根据ID，交换当前队伍中的两名{@link Hero}的位置*/
	public void swapById(int id, int anotherId) {
		Hero h1 = find(current, id), h2 = find(current, anotherId);
		if(h1 == null || h2 == null) return;
		Collections.swap(current, current.indexOf(h1), current.indexOf(h2));
	}
	
	/**根据位置，交换当前队伍中的两名{@link Hero}的位置*/
	public void swapByIndex(int idx1, int idx2) {
		Collections.swap(current, idx1, idx2);
	}
	
	/**从队伍中删除一名{@link Hero}*/
	
	/**在{@link all}里加入一名{@link Hero}，如果已经存在则不加了=。=*/
	public Hero create(int id) {
		Hero hero = find(all, id);
		
		if(hero == null){
			hero = new Hero(id);
			all.add(hero);
		}
		
		return hero;
	}
	
	private Hero find(List<Hero> list, int id){
		return Stream.of(all).filter(h -> h.id == id).findAny().orElse(null);
	}
	
	/**获取当前{@link Hero}*/public List<Hero> current() { return (List<Hero>)current.clone(); };

}
