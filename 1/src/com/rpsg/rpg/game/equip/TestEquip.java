package com.rpsg.rpg.game.equip;

import com.rpsg.rpg.object.base.Equip;

public class TestEquip extends Equip{

	public TestEquip() {
		name="草鞋";
		statusName="草鞋[防御+5 魔防+1]";
		attack=1;
		defense=5;
		magicAttack=-1;
		magicDefense=1;
		speed=1;
		hit=0;
		illustration="很普通的草鞋，贫穷的象征，穿上去感觉怪怪的。";
		onlyFor=null;
		hp=1;
		mp=100;
	}

	@Override
	public void use() {
		
	}

}
