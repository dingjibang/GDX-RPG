package com.rpsg.rpg.game.script;

import com.rpsg.rpg.game.hero.Flandre;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.utils.game.Heros;

public class SayHelloWorld extends Script{
	
	public void init() {
//		add(()->GameViews.global.test++);
//		showMSG();
//		say(GameViews.global.test+"");
//		hideMSG();
		Heros.swapHeroQueue(this, Flandre.class);
		Heros.swapHeroQueue(this, 1, 3);
//		add(()->a=999);
//		a=998;
//		showMSG(¾õ);
//		add(()->insert(say("a="+a)));
//		say("a="+a);
//		hideMSG();
//		changeSelf(SayFuckMe.class);
	}
}
