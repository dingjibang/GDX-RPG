package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.utils.display.Msg;
import com.rpsg.rpg.utils.game.Move;

public class SayFuckMe extends Script{
	
	@Override
	public void init() {
		Msg.say(this,"����Ļ����һ�仰","����", 40);
		Move.move(this, 3);
		Msg.say(this,"��һ�仰","����", 40);
	}
}
