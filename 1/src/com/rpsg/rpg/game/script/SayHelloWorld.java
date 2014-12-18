package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.rpgobj.IRPGObject;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.utils.display.MsgUtil;
import com.rpsg.rpg.utils.game.BaseUtil;
import com.rpsg.rpg.utils.game.MoveUtil;
import com.rpsg.rpg.utils.game.TimeUtil;
import com.rpsg.rpg.view.GameViews;

public class SayHelloWorld extends Script{
	

	@Override
	public void init() {
		MsgUtil.setLocker(this,true);
		MsgUtil.MSG(GameViews.batch, "我蹲在角落，但我却看穿了一切","沼跃鱼", 22,this);
		MsgUtil.MSG(GameViews.batch, "人类为何要互相伤害呢？\n你难道又要引发战争么？","沼跃鱼", 22,this);
		MoveUtil.turn(this,IRPGObject.FACE_R);
		TimeUtil.wait(this, 60);
		MoveUtil.turn(this,IRPGObject.FACE_D);
		TimeUtil.wait(this, 10);
		MsgUtil.MSG(GameViews.batch, "算了不和你说了，你这个傻屌","沼跃鱼", 22,this);
		MoveUtil.turn(this,IRPGObject.FACE_R);
		MoveUtil.move(this, 4);
		BaseUtil.changeSelf(this,SayFuckMe.class);
		MsgUtil.setLocker(this,false);
	}
}
