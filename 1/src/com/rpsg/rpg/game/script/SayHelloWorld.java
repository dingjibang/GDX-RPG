package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.rpgobj.IRPGObject;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.object.script.ScriptExecutor;
import com.rpsg.rpg.utils.display.MsgUtil;
import com.rpsg.rpg.utils.game.BaseUtil;
import com.rpsg.rpg.utils.game.MoveUtil;
import com.rpsg.rpg.utils.game.TimeUtil;

public class SayHelloWorld extends Script{
	
	int a=0;
	@Override
	public void init() {
		MsgUtil.setLocker(this,true);
		MoveUtil.faceToHero(this);
		MsgUtil.MSG(this,"����ը��","ħ��ɳ", 50);
//		MsgUtil.MSG(this,"����Ϊ��Ҫ�����˺��أ�\n���ѵ���Ҫ����ս��ô��","��Ծ��", 22);
//		TimeUtil.wait(this, 60);
//		MoveUtil.turn(this,IRPGObject.FACE_D);
//		TimeUtil.wait(this, 10);
//		MsgUtil.MSG(this,"���˲�����˵�ˣ������ɵ��","��Ծ��", 22);
//		MoveUtil.turn(this,IRPGObject.FACE_R);
//		MoveUtil.move(this, 4);
//		BaseUtil.changeSelf(this,SayFuckMe.class);
		add(new ScriptExecutor(this) {public void init() {
				a=1;this.dispose();
		}});
		add(new ScriptExecutor(this) {
			public void init() {
				if(a==0)
					insert(MsgUtil.MSG(script,"a=0��","system", 22));
				else
					insert(MsgUtil.MSG(script,"a=1��","system", 22));
				this.dispose();
			}
		});
		MsgUtil.setLocker(this,false);
		MoveUtil.turn(this,IRPGObject.FACE_R);
	}
}
