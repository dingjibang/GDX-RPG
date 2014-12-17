package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.Script;
import com.rpsg.rpg.utils.MsgUtil;
import com.rpsg.rpg.view.GameViews;

public class SayHelloWorld extends Script{
	public static String text="≤‚ ‘»˝ÀƒŒÂ";
	public void run() {
		MsgUtil.MSG(GameViews.batch, "",text, 22);
		this.dispose();
	}
}
