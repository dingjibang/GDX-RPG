package com.rpsg.rpg.object.base.items;

import java.io.Serializable;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Target;

/**
 * <i>GDX-RPG</i> 道具（ITEM）超类
 * <br>
 * Item是游戏中道具的数据结构。<br>
 * 在目前，Item拥有以下几种子类型：<br>
 * <br>
 * {@link Item}(道具) - 最普通的道具，和Item类本身相同。<br>
 * {@link Equipment} (装备) - 装备类，继承Item。<br>
 * {@link Spellcard} (符卡) - 符卡类，继承Item（未完成）。<br>
 * <br>
 * <i>GDX-RPG</i>的所有道具数据，均存储于[/rpg/android/assets/script/data]这个位置。<br>
 * 存储的规范是，使用数字（即道具的唯一ID）进行命名，文件后缀为.grd(GDX RPG Data)。<br>
 * 相应的，存储的格式是<b>Json<b>格式。<br>
 * <br>
 * @author dingjibang
 */
public abstract class BaseItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**本道具是否可以丢弃*/
	public boolean throwable = true;
	
	/**道具名称*/
	public String name = "";
	
	/**道具数量*/
	public int count = 1;
	
	/**道具描述信息*/
	public String description = "";
	
	/**道具是否不可用*/
	public boolean disable = false;
	
	/**道具ID（需要唯一性/ID和物品的图标有关联）*/
	public int id=0;
	
	/**道具类型*/
	public String type=null;
	
	/**买入金钱**/
	public int buy = 0;
	
	/**卖出金钱**/
	public int sell = 0;

	/**道具是否可叠加的*/
	public boolean packable = true; 
	
	/**道具效果**/
	public Effect effect;
	
	public String getIcon(){
		String name = Setting.IMAGE_ICONS+"i"+id+".png";
		if(Gdx.files.internal(name).exists())
			return name;
		return getDefaultIcon();
	}
	
	public static String getDefaultIcon(){
		return Setting.IMAGE_ICONS+"i0.png";
	}
	
	/**
	 * 使用这个道具<br>
	 * 原理：执行变量script里寄存的js语句。<br>
	 * 注意：使用use()方法前，可能需要进行变量 <i>注入</i> （比如使用这个道具的人(user))
	 * @return
	 */
	public abstract Result use(Context ctx);
	
	public String toString() {
		return name;
	}
	
	public void remove(){
		RPG.ctrl.item.remove(this);
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
			if(this.enemies.contains(this.enemy)) this.enemies.remove(this.enemy);
			if(this.friend.contains(this.self)) this.friend.remove(this.self);
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
