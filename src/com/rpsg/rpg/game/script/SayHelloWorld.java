package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.view.GameViews;

public class SayHelloWorld extends Script{
	
	public void init() {
		add(()->GameViews.global.test++);
		showMSG();
		say(GameViews.global.test+"");
		hideMSG();
//		add(()->a=999);
//		a=998;
//		showMSG(¾õ);
//		add(()->insert(say("a="+a)));
//		say("a="+a);
//		hideMSG();
//		changeSelf(SayFuckMe.class);
	}
}
