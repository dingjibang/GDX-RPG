package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;

public class SayHelloWorld extends Script{
	
	public void init() {
		setKeyLocker(true);
		faceToHero();
		showMSG();
		showFGLeft(����˿����, ˼��);
		say("���!");
		select("���","�������");
		$(()->{
			if(currentSelect("���"))
				_$(say("�������"));
			else
				_$(say("����"));
		});
		hideFG();
		hideMSG();
		setKeyLocker(false);
	}
}
