package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;

public class SayFuckMe extends Script{
	
	public void init() {
		showMSG(����˿);
		showFGLeft(����˿����, ��);
		showFGRight(����˿����, ��);
		say("����Ļ����һ�仰");
		hideMSG();
		hideFG();
	}
}
