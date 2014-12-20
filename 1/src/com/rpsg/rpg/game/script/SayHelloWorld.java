package com.rpsg.rpg.game.script;

import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.utils.display.FG;

public class SayHelloWorld extends Script{
	
	int a=0;
	public void init() {
//		faceToHero();
//		if(a==0){
//			say("第一次？");
//			add(()->a=999);
//		}else{
//		}
		FG.show(this, Setting.GAME_RES_IMAGE_FG+"alice/01.png", FG.LEFT);
		FG.show(this, Setting.GAME_RES_IMAGE_FG+"alice/01.png", FG.RIGHT);
		say("你妈飞了");
//		a=980;
//		add(()->insert(say("a="+a,"测试", 50)));
//		changeSelf(SayFuckMe.class);
	}
}
