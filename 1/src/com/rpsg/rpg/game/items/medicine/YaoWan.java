package com.rpsg.rpg.game.items.medicine;

import com.rpsg.rpg.io.Music;
import com.rpsg.rpg.object.base.items.Medicine;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.utils.display.AlertUtil;

public class YaoWan extends Medicine{

	private static final long serialVersionUID = 1L;
	
	public YaoWan() {
		illustration="要完要完，使用后HP恢复10。";
		count=5;
		name="药丸";
	}

	public boolean use(Hero hero) {
		if(!hero.full("hp")){
			hero.addProp("hp", 10);
			Music.playSE("bc");
			return throwSelf("使用成功。",AlertUtil.Green);
		}else{
			Music.playSE("err");
			AlertUtil.add(hero.name+"的生命值已满。",AlertUtil.Yellow);
			return false;
		}
	}

}
