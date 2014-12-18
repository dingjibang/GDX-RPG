package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.utils.display.MsgUtil;
import com.rpsg.rpg.view.GameViews;

public class SayFuckMe extends Script{
	

	@Override
	public void init() {
		MsgUtil.setLocker(this,true);
		MsgUtil.MSG(GameViews.batch, "你过来干啥？有病？","沼跃鱼", 40,this);
		MsgUtil.setLocker(this,false);
	}
}
