package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.utils.display.MsgUtil;

public class SayFuckMe extends Script{
	

	@Override
	public void init() {
		MsgUtil.setLocker(this,true);
		MsgUtil.MSG(this,"�������ɶ���в���","��Ծ��", 40);
		MsgUtil.setLocker(this,false);
	}
}
