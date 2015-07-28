package com.rpsg.rpg.game.script;

import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.CGController;
import com.rpsg.rpg.system.ui.Image;

public class SayFuckMe extends Script{
	
	Image i;
	public void init() {
		lock(true);
		faceToHero();
		setKeyLocker(true);
		showMSG(爱丽丝);
		showFGLeft(爱丽丝立绘, 哭);
		say("随意的测试一句话_(:3」∠)_asdhqweuqweqwkeqwopeqwpoeiqweoq很长很多的字hhhhhhhh");
		hideMSG();
		hideFG();
		setKeyLocker(false);
//		$(new BaseScriptExecutor() {
//			public void init() {
//				i=Res.get(Setting.GAME_RES_WALK+"npcs/door.png");
//				CGController.push(i);
//			}
//		});
//		wait(300);
//		$(new BaseScriptExecutor() {
//			public void init() {
//				CGController.dispose(i);
//			}
//		});
	}
}
