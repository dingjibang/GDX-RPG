package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;

public class SayHelloWorld extends Script{
	
	public void init() {
		setKeyLocker(true);
		faceToHero();
		showMSG();
		showFGLeft(°®ÀöË¿Á¢»æ, Ë¼¿¼);
		say("ÄãºÃ!");
		select("ÄãºÃ","ºÃÄãÂè±Æ");
		$(()->{
			if(currentSelect("ÄãºÃ"))
				_$(say("ºÃÄãÂè±Æ"));
			else
				_$(say("¹ö´Ö"));
		});
		hideFG();
		hideMSG();
		setKeyLocker(false);
	}
}
