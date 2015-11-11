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
	
	/**道具是否为一次性的*/
	public boolean removeAble = true;
	
	/**道具使用动画**/
	public int animation = 0;
	
	/**
	 * 道具指向
	 * @author dingjibang
	 */
	public static enum ItemForward{
		friend,//我方
		enemy,//敌人
		all//全部
	}
	
	/**
	 * 道具使用范围
	 * @author dingjibang
	 */
	public static enum ItemRange{
		one,//一人
		all//全部
	}

}
