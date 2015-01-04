package com.rpsg.rpg.object.base;


public class TipEquip extends Equip{

	public TipEquip() {
		name="提示";
		statusName="提示";
		attack=0;
		defense=0;
		magicAttack=0;
		magicDefense=0;
		speed=0;
		hit=0;
		illustration="在左侧选择一项装备，在上方选择卸下当前装备或丢弃、更换为其他装备。";
		onlyFor=null;
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
