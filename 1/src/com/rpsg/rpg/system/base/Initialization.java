package com.rpsg.rpg.system.base;

import com.rpsg.rpg.system.control.MapControler;
import com.rpsg.rpg.utils.display.FG;
import com.rpsg.rpg.utils.display.Msg;
import com.rpsg.rpg.view.GameView;

public class Initialization {
	public static void init(GameView gv){
		ThreadPool.init();
		MapControler.init(gv);
		Msg.init();
		FG.init();
	}
}
