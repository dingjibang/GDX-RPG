package com.rpsg.rpg.utils;

import com.rpsg.rpg.view.GameView;

public class Initialization {
	public static void init(GameView gv){
		MapControler.init(gv);
		MsgUtil.init();
	}
}
