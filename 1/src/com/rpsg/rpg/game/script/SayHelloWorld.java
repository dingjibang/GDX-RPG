package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.utils.display.ColorUtil;
import com.rpsg.rpg.view.GameViews;

public class SayHelloWorld extends Script{
	
	public void init() {
//		add(()->GameViews.global.test++);
//		showMSG();
//		say(GameViews.global.test+"");
//		hideMSG();
//		swapHeroQueue(Flandre.class);
		$(()->{
			if(++GameViews.global.day>2)
				GameViews.global.day=0;
			if(GameViews.global.day==0)
				_$(setGameTime(ColorUtil.DAY));
			else if(GameViews.global.day==1)
				_$(setGameTime(ColorUtil.DUSK));
			else
				_$(setGameTime(ColorUtil.NIGHT));
		});
//		add(()->a=999);
//		a=998;
//		showMSG(¾õ);
//		add(()->insert(say("a="+a)));
//		say("a="+a);
//		hideMSG();
//		changeSelf(SayFuckMe.class);
	}
}
