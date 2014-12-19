package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.rpgobj.IRPGObject;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.utils.display.Msg;
import com.rpsg.rpg.utils.game.Base;
import com.rpsg.rpg.utils.game.Move;

public class SayHelloWorld extends Script{
	
	int a;
	@Override
	public void init() {
		add(()->a=999);
		a=980;
		add(()->insert(Msg.say(this,"a="+a,"≤‚ ‘", 50)));
		Base.changeSelf(this,SayFuckMe.class);
	}
}
