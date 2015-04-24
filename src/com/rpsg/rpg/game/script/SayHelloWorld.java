package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.utils.display.ColorUtil;
import com.rpsg.rpg.view.*;

public class SayHelloWorld extends Script{
	
	public void init() {
		$(()->{
			if(++GameViews.global.mapColor>2)
				GameViews.global.mapColor=0;
		});
//		lock(true);
//		faceToHero();
//		showMSG();
//		showFGLeft(爱丽丝立绘, 思考);
//		say("你好!");
//		hideFG();
//		hideMSG();
//		lock(false);
	}
}
