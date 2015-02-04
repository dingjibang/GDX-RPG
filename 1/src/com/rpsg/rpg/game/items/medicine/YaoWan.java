package com.rpsg.rpg.game.items.medicine;

import com.rpsg.rpg.io.Music;
import com.rpsg.rpg.object.base.items.Medicine;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.utils.display.AlertUtil;

public class YaoWan extends Medicine{

	private static final long serialVersionUID = 1L;
	
	public YaoWan() {
		illustration="Ҫ��Ҫ�꣬ʹ�ú�HP�ָ�10��";
		count=5;
		name="ҩ��";
	}

	public boolean use(Hero hero) {
		if(!hero.full("hp")){
			hero.addProp("hp", 10);
			Music.playSE("bc");
			return throwSelf("ʹ�óɹ���",AlertUtil.Green);
		}else{
			Music.playSE("err");
			AlertUtil.add(hero.name+"������ֵ������",AlertUtil.Yellow);
			return false;
		}
	}

}
