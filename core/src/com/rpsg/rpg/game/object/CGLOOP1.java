package com.rpsg.rpg.game.object;

import com.rpsg.rpg.object.rpg.DefaultNPC;
import com.rpsg.rpg.object.script.Script;

public class CGLOOP1 extends DefaultNPC{

	private static final long serialVersionUID = -3859019464901632709L;
	public CGLOOP1(String path, Integer width, Integer height) {
		
		super(path, width, height);
	}

	@Override
	public void init() {
	}
	
	public static class Instance extends Script {
//		Image black,y11,mask,mask2; 
//		int flength,del;
//		public void init() {
//			set(new BaseScriptExecutor() {public void init() {
//				RPG.ctrl.cg.push(black=(Image) $.add(Res.getNP(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(Color.BLACK).getItem());
//				PostUtil.showMenu=false;
//			}});
//			playSE("fire.mp3");
//			wait(230);
//			playSE("YS070523.wav");
//			set(new BaseScriptExecutor() {public void init() {
//				final Image cg=(Image) $.add(Res.getNP(Setting.IMAGE_CG+"flash.png")).setColor(1,1,1,0).getItem();
//				cg.addAction(Actions.sequence(Actions.color(new Color(1,0.7f,0,0.8f),0.02f),Actions.fadeOut(0.1f),Actions.run(new Runnable() {public void run() {
//					RPG.ctrl.cg.dispose(cg);
//				}})));
//				RPG.ctrl.cg.push(cg);
//			}});
//			wait(79);
//			set(new BaseScriptExecutor() {public void init() {
//				final Image cg=(Image) $.add(Res.getNP(Setting.IMAGE_CG+"flash.png")).setColor(1,1,1,0).setScale(3.8f).setPosition(-800,-300).getItem();
//				cg.addAction(Actions.sequence(Actions.color(new Color(1,1,1,0.8f),0.02f),Actions.fadeOut(0.1f),Actions.run(new Runnable() {public void run() {
//					RPG.ctrl.cg.dispose(cg);
//				}})));
//				RPG.ctrl.cg.push(cg);
//			}});
//			wait(83);
//			wait(130);
//			playSE("woodwave.wav");
//			wait(300);
//			showMenu(false);
//			showMSG(MsgType.电脑);
//			wait(10);
//			say("哈哈哈哈，是我赢了！","？？？");
//			say("八云紫！","？？？");
//			hideMSG();
//			wait(60);
//			showMSG(MsgType.电脑);
//			say("好好看看你自己人生最后的样子吧！","？？？");
//			hideMSG();
//			wait(80);
//			showMSG(MsgType.紫);
//			say("这句话，","八云紫");
//			say("我想原封不动地还给你才最恰当呢。","八云紫");
//			hideMSG();
//			set(new BaseScriptExecutor() {public void init() {
//				final Image cg=(Image) $.add(Res.getNP(Setting.UI_BASE_IMG)).setColor(1,0,0,0).setSize(GameUtil.screen_width,GameUtil.screen_height).setPosition(0,0).getItem();
//				cg.addAction(Actions.sequence(Actions.color(new Color(1,0,0,1f),0.02f),Actions.fadeOut(0.15f),Actions.run(new Runnable() {public void run() {
//					RPG.ctrl.cg.dispose(cg);
//				}})));
//				RPG.ctrl.cg.push(cg);
//			}});
//			stopAllSE(0.01f);
//			playSE("attack.wav");
//			wait(65);
//			showMSG(MsgType.电脑);
//			say("什么！？","？？？",35);
//			hideMSG();
//			playSE("TornadoText3.mp3");
//			setSEVolume(0, 0.13f);
//			wait(30);
//			set(new BaseScriptExecutor() {public void init() {
//				y11=(Image) $.add(Res.getNP(Setting.IMAGE_CG+"y11cg.jpg")).setColor(1,1,1,0).setSize(GameUtil.screen_width,GameUtil.screen_height).setPosition(0,0).getItem();
//				y11.addAction(Actions.sequence(Actions.color(new Color(1,1,1,1f),0.5f,Interpolation.pow4In)));
//				RPG.ctrl.cg.push(y11);
//			}});
//			wait(200);
//			showMSG(MsgType.电脑);
//			say("为什么！！！","？？？");
//			hideMSG();
//			wait(30);
//			showMSG(MsgType.电脑);
//			say("这不可能！！！","？？？");
//			hideMSG();
//			wait(140);
//			showMSG(MsgType.电脑);
//			say("难道说……","？？？");
//			hideMSG();
//			wait(120);
//			showMSG(MsgType.电脑);
//			say("原来如此……","？？？");
//			hideMSG();
//			wait(30);
//			showMSG(MsgType.电脑);
//			say("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈！！！","？？？");
//			hideMSG();
//			set(new BaseScriptExecutor() {public void init() {
//				y11.setOrigin(Align.center);
//				y11.clearActions();
//				y11.addAction(Actions.repeat(RepeatAction.FOREVER,Actions.addAction(new Action() {
//					public boolean act(float delta) {
//						if(del++==850){
//							del=0;
//							flength++;
//						}
//						actor.addAction(Actions.moveTo(MathUtils.random(-flength,flength), MathUtils.random(-flength,flength),1f));
//						return false;
//					}
//				})));
//				y11.addAction(Actions.scaleTo(2f,2f,8f,Interpolation.pow4In));
//				mask=(Image) $.add(Res.getNP(Setting.UI_BASE_IMG)).setColor(1,0,0,0).setSize(GameUtil.screen_width,GameUtil.screen_height).setPosition(0,0).getItem();
//				mask.addAction(Actions.color(new Color(1,0,0,1f),6,Interpolation.pow5In));
//				RPG.ctrl.cg.push(mask);
//			}});
//			setSEVolume(4.1f, 1);
//			playSE("fx1.wav");
//			wait(50);
//			set(new BaseScriptExecutor() {public void init() {
//				RPG.ctrl.cg.dispose(mask);
//				RPG.ctrl.cg.dispose(y11);
//				y11.clearActions();
//			}});
//			stopAllSE(0,"fx1.wav");
//			wait(180);
//			set(new BaseScriptExecutor() {public void init() {
//				RPG.ctrl.cg.disposeAll();
//			}});
//			set(new BaseScriptExecutor() {
//				public void init() {
//					Move.teleportAnotherMap(Instance.this,  "subway.tmx",18,1,1);
//				}
//			});
//			removeSelf();
//		}
	}
}
