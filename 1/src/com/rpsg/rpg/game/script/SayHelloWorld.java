package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.Script;
import com.rpsg.rpg.utils.MsgUtil;
import com.rpsg.rpg.view.GameViews;

public class SayHelloWorld extends Script{
	
	public void run() {
		MsgUtil.MSG(GameViews.batch, "Hello World\nfuck my ass hole now","ÃÏÕ‚÷Æ“Ù", 22);
		this.dispose();
	}
}
