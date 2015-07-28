package com.rpsg.rpg.game.script;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.MsgType;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.CGController;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.game.GameUtil;

public class CGLoop1 extends Script {
	Image black,y11,mask; 
	public void init() {
		$(new BaseScriptExecutor() {public void init() {
				black=Res.getNP(Setting.UI_BASE_IMG);
				black.setSize(GameUtil.screen_width, GameUtil.screen_height);
				black.setColor(Color.BLACK);
				CGController.push(black);
		}});
		playSE("fire.mp3");
		wait(230);
		playSE("YS070523.wav");
		$(new BaseScriptExecutor() {public void init() {
			final Image cg=(Image) $.add(Res.getNP(Setting.GAME_RES_IMAGE_CG+"flash.png")).setColor(1,1,1,0).getItem();
			cg.addAction(Actions.sequence(Actions.color(new Color(1,0.7f,0,0.8f),0.02f),Actions.fadeOut(0.1f),Actions.run(new Runnable() {public void run() {
				CGController.dispose(cg);
			}})));
			CGController.push(cg);
		}});
		wait(79);
		$(new BaseScriptExecutor() {public void init() {
			final Image cg=(Image) $.add(Res.getNP(Setting.GAME_RES_IMAGE_CG+"flash.png")).setColor(1,1,1,0).setScale(3.8f).setPosition(-800,-300).getItem();
			cg.addAction(Actions.sequence(Actions.color(new Color(1,1,1,0.8f),0.02f),Actions.fadeOut(0.1f),Actions.run(new Runnable() {public void run() {
				CGController.dispose(cg);
			}})));
			CGController.push(cg);
		}});
		wait(83);
		wait(130);
		playSE("woodwave.wav");
		wait(300);
		showMenu(false);
		showMSG(MsgType.npc);
		wait(10);
		say("哈哈哈哈，是我赢了！","？？？");
		say("八云紫！","？？？");
		hideMSG();
		wait(60);
		showMSG(MsgType.npc);
		say("好好看看你自己人生最后的样子吧！","？？？");
		hideMSG();
		wait(80);
		showMSG(MsgType.紫);
		say("这句话","八云紫");
		say("我想原封不动地还给你才最恰当呢。","八云紫");
		hideMSG();
		wait(15);
		$(new BaseScriptExecutor() {public void init() {
			final Image cg=(Image) $.add(Res.getNP(Setting.UI_BASE_IMG)).setColor(1,0,0,0).setSize(GameUtil.screen_width,GameUtil.screen_height).setPosition(0,0).getItem();
			cg.addAction(Actions.sequence(Actions.color(new Color(1,0,0,1f),0.02f),Actions.fadeOut(0.15f),Actions.run(new Runnable() {public void run() {
				CGController.dispose(cg);
			}})));
			CGController.push(cg);
		}});
		playSE("attack.wav");
		playSE("TornadoText3.mp3");
		wait(45);
		showMSG(MsgType.npc);
		say("什么！？","？？？",35);
		hideMSG();
		wait(40);
		$(new BaseScriptExecutor() {public void init() {
			y11=(Image) $.add(Res.getNP(Setting.GAME_RES_IMAGE_CG+"y11cg.jpg")).setColor(1,1,1,0).setSize(GameUtil.screen_width,GameUtil.screen_height).setPosition(0,0).getItem();
			y11.addAction(Actions.sequence(Actions.color(new Color(1,1,1,1f),0.5f,Interpolation.pow4In)));
			CGController.push(y11);
		}});
		wait(200);
		showMSG(MsgType.npc);
		say("为什么！！！","？？？");
		hideMSG();
		wait(30);
		$(new BaseScriptExecutor() {public void init() {
			y11.addAction(Actions.repeat(RepeatAction.FOREVER,Actions.addAction(new Action() {
				public boolean act(float delta) {
					actor.addAction(Actions.moveTo(MathUtils.random(-20,20), MathUtils.random(-20,20),0.3f,Interpolation.bounce));
					return false;
				}
			})));
		}});
		showMSG(MsgType.npc);
		say("这不可能！！！","？？？");
		hideMSG();
		wait(100);
		showMSG(MsgType.npc);
		say("难道说……","？？？");
		hideMSG();
		wait(120);
		showMSG(MsgType.npc);
		say("原来如此……\n哈哈哈哈哈哈哈哈哈哈哈","？？？");
		hideMSG();
		$(new BaseScriptExecutor() {public void init() {
			y11.setOrigin(Align.center);
			y11.addAction(Actions.scaleTo(2f,2f,4f,Interpolation.pow4In));
			mask=(Image) $.add(Res.getNP(Setting.UI_BASE_IMG)).setColor(1,1,1,0).setSize(GameUtil.screen_width,GameUtil.screen_height).setPosition(0,0).getItem();
			mask.addAction(Actions.color(new Color(0,0,0,1f),3f));
			CGController.push(mask);
		}});
		
		wait(100);
		stopAllSE(4);
		$(new BaseScriptExecutor() {public void init() {
			CGController.dispose(mask);
			CGController.dispose(y11);
		}});
		removeSelf();
	}
}
