package com.rpsg.rpg.object.base.items;

import java.util.List;

import com.rpsg.rpg.object.rpg.Target;

public class Item extends BaseItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**道具指向*/
	public ItemForward forward = ItemForward.friend;
	
	/**道具使用范围*/
	public ItemRange range = ItemRange.one;
	
	/**道具使用场景**/
	public ItemOccasion occasion = ItemOccasion.all;
	
	/**道具是否可以给满身疮痍的人使用**/
	public ItemDeadable deadable = ItemDeadable.no;
	
	/**道具是否为一次性的*/
	public boolean removeable = true;
	
	/**道具使用动画**/
	public int animation = 0;
	
	/**
	 * 道具指向
	 * @author dingjibang
	 */
	public static enum ItemForward{
		friend,//我方
		enemy,//敌人
		all,//全部
		link,//对与自己连携的对象使用的。
		self//自己
	}
	
	/**
	 * 道具使用范围
	 * @author dingjibang
	 */
	public static enum ItemRange{
		one,//一人
		all//全部
	}
	
	/**
	 * 道具使用场景
	 * @author dingjibang
	 */
	public static enum ItemOccasion{
		battle,//仅战斗时
		map,//仅非战斗时
		all//所有场景
	}
	
	/**
	 * 道具是否可以给满身疮痍的人使用。
	 * @author dingjibang
	 *
	 */
	public static enum ItemDeadable{
		yes,//仅能给满身疮痍的人使用。
		no,//仅能给活着的人使用。
		all//可以给所有人使用。
	}
	
	
	public static class Context {
		public static enum Type{battle,map} 
		
		public Type type;
		public Target self;
		public Target enemy;
		public List<Target> friend;
		public List<Target> enemies;

		public Context(Object self, Object enemy, List<?> friend, List<?> enemies) {
			super();
			this.self = Target.parse(self);
			this.enemy = Target.parse(enemy);
			this.friend = Target.parse(friend);
			this.enemies = Target.parse(enemies);
			
			//去重复
			if(enemies.contains(enemy)) enemies.remove(enemy);
			if(friend.contains(self)) friend.remove(self);
		}

		public Context target(Target enemy2) {
			this.enemy = enemy2;
			return this;
		}
		
		Context setType(Type $type){
			type = $type;
			return this;
		}
		
		public static Context battle(Object self, Object enemy, List<?> friend, List<?> enemies){
			return new Context(self,enemy,friend,enemies).setType(Type.battle);
		}
		
		public static Context map(Object self, Object enemy, List<?> friend, List<?> enemies){
			return new Context(self,enemy,friend,enemies).setType(Type.map);
		}
		
	}


}
