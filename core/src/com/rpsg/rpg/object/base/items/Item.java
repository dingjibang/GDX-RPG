package com.rpsg.rpg.object.base.items;


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

}
