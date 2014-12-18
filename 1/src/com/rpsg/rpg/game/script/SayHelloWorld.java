package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.rpgobj.IRPGObject;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.utils.display.MsgUtil;
import com.rpsg.rpg.utils.game.BaseUtil;
import com.rpsg.rpg.utils.game.MoveUtil;
import com.rpsg.rpg.utils.game.TimeUtil;

public class SayHelloWorld extends Script{
	

	@Override
	public void init() {
		MsgUtil.setLocker(this,true);
		MsgUtil.MSG(this,"�Ҷ��ڽ��䣬����ȴ������һ��","��Ծ��", 22);
		MsgUtil.MSG(this,"����Ϊ��Ҫ�����˺��أ�\n���ѵ���Ҫ����ս��ô��","��Ծ��", 22);
		MoveUtil.turn(this,IRPGObject.FACE_R);
		TimeUtil.wait(this, 60);
		MoveUtil.turn(this,IRPGObject.FACE_D);
		TimeUtil.wait(this, 10);
		MsgUtil.MSG(this,"���˲�����˵�ˣ������ɵ��","��Ծ��", 22);
		MoveUtil.turn(this,IRPGObject.FACE_R);
		MoveUtil.move(this, 4);
		BaseUtil.changeSelf(this,SayFuckMe.class);
		MsgUtil.setLocker(this,false);
	}
}
