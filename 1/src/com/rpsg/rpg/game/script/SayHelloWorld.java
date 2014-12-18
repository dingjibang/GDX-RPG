package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.utils.display.MsgUtil;
import com.rpsg.rpg.view.GameViews;

public class SayHelloWorld extends Script{
	
	@Override
	public void init() {
		MsgUtil.MSG(GameViews.batch, "我蹲在角落，但我却看穿了一切","天外之音", 22,this);
		MsgUtil.MSG(GameViews.batch, "人类为何要互相伤害呢？\n你难道又要引发战争么？","Java", 22,this);
	}
}
