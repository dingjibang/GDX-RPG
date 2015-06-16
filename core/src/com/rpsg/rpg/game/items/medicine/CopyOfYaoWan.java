package com.rpsg.rpg.game.items.medicine;

import com.rpsg.rpg.object.base.items.Medicine;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.utils.display.AlertUtil;

public class CopyOfYaoWan extends Medicine{

	private static final long serialVersionUID = 1L;
	
	public CopyOfYaoWan() {
		illustration="奇怪的旧药盒，背面文字已模糊不清，但仍可以隐约看到“ヤゴコロ先生的坐药”，没有人知道吃了这个会发生什么。";
		count=1;
		name="ヤゴコロ";
	}

	public boolean use(Hero hero) {
		if(!hero.full("hp")){
			hero.addProp("hp", 50);
			return throwSelf("使用成功。",AlertUtil.Green);
		}else{
			AlertUtil.add(hero.name+"的生命值已满。",AlertUtil.Yellow);
			return false;
		}
	}

}
