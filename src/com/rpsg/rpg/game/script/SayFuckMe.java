package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;

public class SayFuckMe extends Script{
	
	public void init() {
		showMSG(爱丽丝);
		showFGLeft(爱丽丝立绘, 哭);
		say("随意的测试一句话_(:3」∠)_asdhqweuqweqwkeqwopeqwpoeiqweoq很长很多的字hhhhhhhh");
		hideMSG();
		hideFG();
	}
}
