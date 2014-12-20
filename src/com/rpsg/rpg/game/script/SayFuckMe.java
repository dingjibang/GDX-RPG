package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;

public class SayFuckMe extends Script{
	
	public void init() {
		say("在屏幕输入一句话","测试", 40);
		move(3);
		say("另一句话","测试", 40);
	}
}
