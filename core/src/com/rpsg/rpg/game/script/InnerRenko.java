package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.ui.Image;

public class InnerRenko extends Script {
	Image black,tip1,tip2;
	public void init() {
//			say("大家好");
//			say("我不是光头");
//			if(RPG.getFlag("1-1-inner")==null){
//				RPG.setFlag("1-1-inner",true);
//				$(new BaseScriptExecutor() {public void init() {
//					RPG.ctrl.cg.push(black=(Image) $.add(Res.getNP(Setting.UI_BASE_IMG)).setSize(GameUtil.getScreenWidth(), GameUtil.getScreenHeight()).setColor(Color.BLACK).getItem()).addAction(Actions.sequence(Actions.fadeOut(0.3f),Actions.run(new Runnable() {public void run() {
//						RPG.ctrl.cg.dispose(black);
//					}})));
//					PostUtil.showMenu=false;
//				}});
//				setKeyLocker(true);
//				$(new BaseScriptExecutor() {public void init() {
//					RPG.ctrl.hero.turn(RPGObject.FACE_U);
//				}});
//				wait(50);
//				
//				showMSG(莲子);
//				say("这里就是我家啦，当然今天开始也是结成同学家","莲子");
//				say("到晚上还有一点时间，结成同学可以随意转转\n二楼最里面是你的房间，已经给你准备了一些道具和食物，自己去拿就好了","莲子");
//				say("准备好了就来找我出发吧，当然有什么问题也是可以问我的哦！","莲子");
//				hideMSG();
//				wait(20);
//				$(new BaseScriptExecutor() {public void init() {
//					RPG.ctrl.cg.push(black=(Image) $.add(Res.getNP(Setting.UI_BASE_IMG)).setSize(GameUtil.getScreenWidth(), GameUtil.getScreenHeight()).setColor(0,0,0,0).getItem()).addAction(Actions.color(new Color(0,0,0,0.5f),0.3f));
//				}});
//				$(new BaseScriptExecutor() {public void init() {
//					tip1=Res.getNP(Setting.IMAGE_GLOBAL+(GameUtil.isDesktop?"tip_pc1.png":"tip_mobile1.png"));
//					tip2=Res.getNP(Setting.IMAGE_GLOBAL+(GameUtil.isDesktop?"tip_pc2.png":"tip_mobile2.png"));
//					_$(new BaseScriptExecutor() {public void init() {
//						RPG.ctrl.cg.push(tip1.color(1,1,1,0).oranCenter().position((int)(GameUtil.getScreenWidth()/2-tip1.getWidth()/2), (int)(GameUtil.getScreenHeight()/2-+tip1.getHeight()/2))).addAction(Actions.color(new Color(1,1,1,1),0.3f));
//					}});
//					_$(say("   "));
//					_$(new BaseScriptExecutor() {public void init() {
//						RPG.ctrl.cg.dispose(tip1);
//						RPG.ctrl.cg.push(tip2.oranCenter().position((int)(GameUtil.getScreenWidth()/2-tip2.getWidth()/2), (int)(GameUtil.getScreenHeight()/2-+tip2.getHeight()/2)));
//					}});
//					_$(say("   "));
//					_$(new BaseScriptExecutor() {public void init() {
//						tip2.addAction(Actions.sequence(Actions.fadeOut(0.3f),Actions.run(new Runnable() {public void run() {
//							RPG.ctrl.cg.dispose(tip2);
//						}})));
//						black.addAction(Actions.sequence(Actions.color(new Color(0,0,0,0),0.3f),Actions.run(new Runnable() {public void run() {
//							RPG.ctrl.cg.dispose(black);
//						}})));
//					}});
//					setKeyLocker(false);
//				}});
//				
//			}
//		removeSelf();
		
	}
}