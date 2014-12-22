package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;

public class SayFuckMe extends Script{
	
	public void init() {
		showMSG(爱丽丝);
		say("在屏幕输入一句话");
		hideMSG();
	}
}
