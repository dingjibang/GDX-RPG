package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;

public class SayHelloWorld extends Script{
	
	public void init() {
//		$(()->{
//			if(++GameViews.global.day>2)
//				GameViews.global.day=0;
//			if(GameViews.global.day==0)
//				_$(setGameTime(ColorUtil.DAY));
//			else if(GameViews.global.day==1)
//				_$(setGameTime(ColorUtil.DUSK));
//			else
//				_$(setGameTime(ColorUtil.NIGHT));
//		});
//		setKeyLocker(true);
		showMSG();
		showFGLeft(����˿����, ˼��);
		say("���!");
		select("���","�������");
//		$(()->{
//			if(SelectUtil.currentSelect.equals("���"))
//				_$(say("�������"));
//			else
//				_$(say("����"));
//		});
		hideFG();
		hideMSG();
//		setKeyLocker(false);
	}
}
