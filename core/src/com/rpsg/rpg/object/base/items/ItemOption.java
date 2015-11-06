package com.rpsg.rpg.object.base.items;

public class ItemOption {
	/**
	 * 道具指向
	 * @author dingjibang
	 */
	public static enum ItemForward{
		friend,//我方
		enemy//敌人
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
