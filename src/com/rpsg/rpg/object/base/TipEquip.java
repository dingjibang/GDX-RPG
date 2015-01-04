package com.rpsg.rpg.object.base;


public class TipEquip extends Equip{

	private static final long serialVersionUID = 1L;

	public TipEquip() {
		name="提示";
		statusName="提示";
		illustration="在左侧选择一项装备，在上方选择卸下当前装备或丢弃、更换为其他装备。使用鼠标或触屏拖动上方装备栏可以将列表上下滚动。";
		onlyFor=null;
		disable=true;
	}
	
	public TipEquip(String tip,String ill){
		this();
		name=tip;
		illustration=ill;
	}

	@Override
	public void use() {
		
	}

}
