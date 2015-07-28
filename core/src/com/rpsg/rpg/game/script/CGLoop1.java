package com.rpsg.rpg.game.script;

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
import com.rpsg.rpg.utils.game.Move;

public class CGLoop1 extends Script {
	Image black,y11,mask,mask2; 
	int flength,del;
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
		say("这句话，","八云紫");
		say("我想原封不动地还给你才最恰当呢。","八云紫");
		hideMSG();
		$(new BaseScriptExecutor() {public void init() {
			final Image cg=(Image) $.add(Res.getNP(Setting.UI_BASE_IMG)).setColor(1,0,0,0).setSize(GameUtil.screen_width,GameUtil.screen_height).setPosition(0,0).getItem();
			cg.addAction(Actions.sequence(Actions.color(new Color(1,0,0,1f),0.02f),Actions.fadeOut(0.15f),Actions.run(new Runnable() {public void run() {
				CGController.dispose(cg);
			}})));
			CGController.push(cg);
		}});
		stopAllSE(0.01f);
		playSE("attack.wav");
		wait(65);
		showMSG(MsgType.npc);
		say("什么！？","？？？",35);
		hideMSG();
		playSE("TornadoText3.mp3");
		setSEVolume(0, 0.13f);
		wait(30);
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
		showMSG(MsgType.npc);
		say("这不可能！！！","？？？");
		hideMSG();
		wait(140);
		showMSG(MsgType.npc);
		say("难道说……","？？？");
		hideMSG();
		wait(120);
		showMSG(MsgType.npc);
		say("原来如此……","？？？");
		hideMSG();
		wait(30);
		showMSG(MsgType.npc);
		say("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈！！！","？？？");
		hideMSG();
		$(new BaseScriptExecutor() {public void init() {
			y11.setOrigin(Align.center);
			y11.clearActions();
			y11.addAction(Actions.repeat(RepeatAction.FOREVER,Actions.addAction(new Action() {
				public boolean act(float delta) {
					if(del++==100){
						del=0;
						flength++;
					}
					actor.addAction(Actions.moveTo(MathUtils.random(-flength,flength), MathUtils.random(-flength,flength),0.3f,Interpolation.bounce));
					return false;
				}
			})));
			y11.addAction(Actions.scaleTo(2f,2f,8f,Interpolation.pow4In));
			mask=(Image) $.add(Res.getNP(Setting.UI_BASE_IMG)).setColor(1,0,0,0).setSize(GameUtil.screen_width,GameUtil.screen_height).setPosition(0,0).getItem();
			mask.addAction(Actions.color(new Color(1,0,0,1f),6,Interpolation.pow5In));
			CGController.push(mask);
		}});
		setSEVolume(5f, 1);
		stopAllSE(0);
		$(new BaseScriptExecutor() {public void init() {
			CGController.dispose(mask);
			CGController.dispose(y11);
			y11.clearActions();
		}});
//		playSE("450331082.mp3");
//		$(new BaseScriptExecutor() {public void init() {
//			mask2=(Image) $.add(Res.getNP(Setting.GAME_RES_IMAGE_CG+"flash3.jpg")).setColor(1,1,1,1).setSize(6144, 576).setOrigin(Align.topLeft).setPosition(0,0).getItem();
//			del=0;
//			flength=0;
//			mask2.addAction(Actions.repeat(RepeatAction.FOREVER,Actions.addAction(new Action() {
//				public boolean act(float delta) {
//					if(flength++>30){
//						flength=0;
//					if(++del>5)
//						del=0;
//					}
//					mask2.setX(-del*1024);
//					return false;
//				}
//			})));
//			CGController.push(mask2);
//		}});
//		wait(10);
//		stopAllSE(0);
//		$(new BaseScriptExecutor() {public void init() {
//			CGController.dispose(mask2);
//		}});
//		$(new BaseScriptExecutor() {
//			public void init() {
//				Move.teleportAnotherMap(CGLoop1.this, npc.params.get("TELEPORT") + ".tmx",
//						Integer.parseInt((String) npc.params.get("TELEPORTX")),
//						Integer.parseInt((String) npc.params.get("TELEPORTY")),
//						Integer.parseInt((String) npc.params.get("TELEPORTZ")));
//			}
//		});
		removeSelf();
	}
}
