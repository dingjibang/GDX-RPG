package com.rpsg.rpg.game.sc;

import com.rpsg.rpg.io.Music;
import com.rpsg.rpg.object.base.items.SpellCard;
import com.rpsg.rpg.object.rpgobj.Hero;
import com.rpsg.rpg.utils.display.AlertUtil;

public class patientBack extends SpellCard{
	private static final long serialVersionUID = 1L;

	public patientBack(){
		name="�ش���";
		illustration="ʹ�ú󣬽��Է�������״̬�Ķ��ѻָ�50��+10%ħ��������ֵ�������ڷ�ս��״̬��ʹ�á�";
		story="return of spring����";
		magicConsume=30;
		addParam.put("hp", 50);
		added=10;
		type=TYPE_USEINMAP;
	}
	
	public boolean use(Hero user,Hero to){
		if(!to.full("hp"))
			if(user.subProp("mp",magicConsume)){
				AlertUtil.add(to.toString()+"�ɹ��ָ���"+addParam.get("hp")+"������ֵ��", AlertUtil.Green);
				to.addProp("hp", addParam.get("hp"));
				Music.playSE("bc");
			}else{
				AlertUtil.add(user.toString()+"������������ʹ��������ܡ�", AlertUtil.Red);
				Music.playSE("err");
			}
		else{
			AlertUtil.add(to.toString()+"������ֵ������", AlertUtil.Yellow);
			Music.playSE("err");
		}
		return false;
	}
}
