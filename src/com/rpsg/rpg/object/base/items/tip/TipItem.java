package com.rpsg.rpg.object.base.items.tip;

import com.rpsg.rpg.object.base.items.Item;


public class TipItem extends Item{
	private static final long serialVersionUID = 1L;

	public TipItem(){
		name="提示";
		illustration="您可以在上方选择道具分类，左侧为当前分类的道具列表，单机道具查看道具信息，双击道具进行功能操作。";
	}

	@Override
	public boolean use() {
		return false;
	}
}
