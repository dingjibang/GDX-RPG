package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.utils.display.Msg;
import com.rpsg.rpg.utils.game.Move;

public class SayFuckMe extends Script{
	
	@Override
	public void init() {
		Msg.say(this,"在屏幕输入一句话","测试", 40);
		Move.move(this, 3);
		Msg.say(this,"另一句话","测试", 40);
	}
}
