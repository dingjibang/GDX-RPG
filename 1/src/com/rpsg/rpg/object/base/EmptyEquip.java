package com.rpsg.rpg.object.base;


public class EmptyEquip extends Equip{

	public EmptyEquip() {
		name="卸下当前装备";
		statusName="卸下当前装备";
		attack=0;
		defense=0;
		magicAttack=0;
		magicDefense=0;
		speed=0;
		hit=0;
		illustration="把当前穿戴的装备卸下。";
		onlyFor=null;
	}

	@Override
	public void use() {
		
	}

}
