package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;

public class SayFuckMe extends Script{
	
	public void init() {
		setKeyLocker(true);
		showMSG(����˿);
		showFGLeft(����˿����, ��);
		say("����Ĳ���һ�仰_(:3����)_asdhqweuqweqwkeqwopeqwpoeiqweoq�ܳ��ܶ����hhhhhhhh");
		hideMSG();
		hideFG();
		setKeyLocker(false);
	}
}
