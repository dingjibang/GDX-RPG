package com.rpsg.rpg.object.base;


public class EmptyEquip extends Equip{

	private static final long serialVersionUID = 1L;

	public EmptyEquip() {
		name=gloname;
		statusName="卸下当前装备";
		illustration="把当前穿戴的装备卸下。";
		onlyFor=null;
		disable=true;
	}

	@Override
	public void use() {
		
	}
	
	public static String gloname= "卸下当前装备";

}
