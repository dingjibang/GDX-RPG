package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;

public class SayHelloWorld extends Script{
	
	int a;
	public void init() {
		add(()->a=999);
		a=998;
		showMSG(��);
		add(()->insert(say("a="+a)));
		say("a="+a);
		hideMSG();
	}
}
