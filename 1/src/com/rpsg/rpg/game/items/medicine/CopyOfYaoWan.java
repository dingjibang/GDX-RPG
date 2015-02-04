package com.rpsg.rpg.game.items.medicine;

import com.rpsg.rpg.object.base.items.Medicine;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.utils.display.AlertUtil;

public class CopyOfYaoWan extends Medicine{

	private static final long serialVersionUID = 1L;
	
	public CopyOfYaoWan() {
		illustration="��ֵľ�ҩ�У�����������ģ�����壬���Կ�����Լ�������䥴������������ҩ����û����֪����������ᷢ��ʲô��";
		count=1;
		name="�䥴����";
	}

	public boolean use(Hero hero) {
		if(!hero.full("hp")){
			hero.addProp("hp", 50);
			return throwSelf("ʹ�óɹ���",AlertUtil.Green);
		}else{
			AlertUtil.add(hero.name+"������ֵ������",AlertUtil.Yellow);
			return false;
		}
	}

}
