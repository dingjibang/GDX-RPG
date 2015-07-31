package com.rpsg.rpg.game.script;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.ActorRunnable;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.gdxQuery.GdxQueryRunnable;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.game.object.SUBWAYMARI;
import com.rpsg.rpg.game.object.SUBWAYRENKO;
import com.rpsg.rpg.object.base.MsgType;
import com.rpsg.rpg.object.rpg.IRPGObject;
import com.rpsg.rpg.object.rpg.NPC;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.BatchScript;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.CGController;
import com.rpsg.rpg.system.controller.HeroController;
import com.rpsg.rpg.system.controller.MoveController;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.display.PostUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Move;
import com.rpsg.rpg.view.GameViews;

public class CGLoop2 extends Script {
	Image black,flash;
	public void init() {
		BatchScript renko=findNPC(SUBWAYRENKO.class).script();
		$(new BaseScriptExecutor() {public void init() {
			CGController.push(black=(Image) $.add(Res.getNP(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(Color.BLACK).getItem());
			PostUtil.showMenu=false;
		}});
		playSE("sw.mp3");
		wait(460);
		$(new BaseScriptExecutor() {public void init() {
			HeroController.walk(1);
			black.addAction(Actions.sequence(Actions.fadeOut(0.3f),Actions.run(new Runnable() {public void run() {
				CGController.dispose(black);
			}})));
		}});
		wait(120);
		playSE("noise.wav");
		$(new BaseScriptExecutor() {public void init() {
			flash=(Image) $.add(Res.getNP(Setting.GAME_RES_IMAGE_CG+"y11cg.jpg")).setColor(1,1,1,0).setSize(GameUtil.screen_width,GameUtil.screen_height).setPosition(0,0).getItem();
			flash.setOrigin(Align.topRight);
			flash.addAction(Actions.sequence(Actions.fadeIn(0.05f),Actions.parallel(Actions.scaleTo(2, 2,0.05f),Actions.moveTo(-300,400,0.05f)),Actions.color(Color.RED),Actions.moveTo(-100,200)));
			CGController.push(flash);
		}});
		wait(10);
		$(new BaseScriptExecutor() {public void init() {
			CGController.dispose(flash);
		}});
		stopAllSE(0);
		wait(60);
		faceTo(IRPGObject.FACE_R);
		and(faceTo(renko, IRPGObject.FACE_R));
		
		showMSG();
		say("...");
		hideMSG();
		wait(60);
		
		
		
		showMSG(莲子);
		say("啊啊！真是的！为什么我们大学总是要在愚人节这天开学啊！","？？？");
		hideMSG();
		$(new BaseScriptExecutor() {public void init() {
			MoveController.offsetActor.addAction(Actions.scaleTo(0.85f, 0.85f,1f,Interpolation.pow4Out));
		}});
		setCameraPositionWithHero(-249, 0, true);
		
		move(2);
		and(move(renko, 2));
		
		wait(60);
		faceTo(IRPGObject.FACE_D);
		wait(30);
		showMSG(梅莉);
		say("一开学就这么没干劲可不好啊，莲子\n最后一年再马马虎虎的话，会毕不了业的","？？？");
		hideMSG();
		wait(30);
		and(faceTo(renko,IRPGObject.FACE_U));
		wait(30);
		showMSG(莲子);
		say("毕业那种事情无所谓啦，只要能跟梅莉你在一起就好了","莲子？");
		hideMSG();
		wait(30);
		showMSG(梅莉);
		say("嘴上这么说着，今天不还是迟到了吗","梅莉？");
		say("明明是带着我去接别人，自己却迟到了算什么啊","梅莉？");
		showMSG(莲子);
		say("额……还在生气啊……","莲子？");
		showMSG(梅莉);
		say("没有，骗你的","梅莉？");
		showMSG(莲子);
		say("感觉真生气了……抱歉啦，一开学就把你拉出来","莲子？");
		showMSG(梅莉);
		say("说到一起的话，我正好有事要告诉你","梅莉？");
		say("毕业之后我就要回国了，所以如果莲子不努力的话，以后可能就再也无法相见了吧","梅莉？");
		hideMSG();
		wait(40);
		and(move(renko, -1));
		wait(40);
		showMSG(莲子);
		say("不……不会吧！","莲子？");
		showMSG(梅莉);
		say("骗你的","梅莉？");
		hideMSG();
		wait(20);
		and(move(renko, 2));
		wait(40);
		showMSG(莲子);
		say("吓死我了……愚人节就不要欺负咱了啦","莲子？");
		showMSG(梅莉);
		say("我不欺负你的话，你就会被别人欺负了。还是我来欺负比较好","梅莉？");
		showMSG(莲子);
		say("你不是认真的吧……","莲子？");
		hideMSG();
		wait(60);
		showMSG(莲子);
		say("啊，对了……","莲子？");
		hideMSG();
		$(new BaseScriptExecutor() {public void init() {
			MoveController.offsetActor.addAction(Actions.scaleTo(1, 1,1f,Interpolation.pow4Out));
		}});
		setCameraPositionWithHero(0, 0, true);
		$(new BaseScriptExecutor() {public void init() {
			HeroController.turn(IRPGObject.FACE_L);
		}});
		$(new BaseScriptExecutor() {public void init() {
			npc.turn(IRPGObject.FACE_R).setWalkSpeed(6);
			renko.npc.turn(IRPGObject.FACE_R).setWalkSpeed(6).walk(12).testWalk();
		}});
		move(12);
		showMSG(莲子);
		say("你一定就是结城有栖同学了吧！","莲子？");
		say("在下就是宇佐见莲子，抱歉让你久等了！","莲子？");
		hideMSG();
		wait(60);
		faceTo(IRPGObject.FACE_D);
		and(faceTo(renko,IRPGObject.FACE_U));
		$(new BaseScriptExecutor() {public void init() {
			MoveController.offsetActor.addAction(Actions.scaleTo(0.9f,0.9f,1f,Interpolation.pow4Out));
		}});
		setCameraPositionWithHero(-60, 0, true);
		showMSG(梅莉);
		say("这就是莲子你要接的人吗？","梅莉？");
		faceTo(IRPGObject.FACE_R);
		hideMSG();
		wait(80);
		showMSG(梅莉);
		say("嗯……果然有些特别呢","梅莉？");
		showMSG(莲子);
		say("嗯嗯，银灰色的头发，好像只在TVgame里有见过呢","莲子");
		faceTo(IRPGObject.FACE_D);
		showMSG(梅莉);
		say("不是指这个啦，笨蛋","梅莉？");
		say("……她身上有着没有见过的境界呢\n能力者？","梅莉？");
		hideMSG();
		faceTo(IRPGObject.FACE_R);
		and(faceTo(renko,IRPGObject.FACE_U));
		showMSG(莲子);
		say("诶，梅莉你看到什么了吗？","宇佐见莲子");
		and(faceTo(renko,IRPGObject.FACE_R));
		say("啊……有栖同学","宇佐见莲子");
		say("这位是梅莉，也是秘封俱乐部的另一位成员啦","莲子");
		faceTo(IRPGObject.FACE_D);
		showMSG(梅莉);
		say("看人家的表情就知道你从来没有跟她提起过我……","梅莉");
		and(faceTo(renko,IRPGObject.FACE_U));
		showMSG(莲子);
		say("因为只在网上聊过啦","莲子");
		showMSG(梅莉);
		say("你到底跟同时跟多少女孩子在网上聊天啊！","梅莉");
		showMSG(莲子);
		say("这……这不重要！总之我认为，结城有栖同学在春假突然获得了奇异的力量！","莲子");
		and(faceTo(renko,IRPGObject.FACE_R));
		faceTo(IRPGObject.FACE_R);
		say("是吧，有栖同学！？","莲子");
		hideMSG();
		wait(45);
		faceTo(IRPGObject.FACE_D);
		showMSG(梅莉);
		say("你看人家完全没有理解你的话……","梅莉");
		showMSG(莲子);
		and(faceTo(renko,IRPGObject.FACE_U));
		say("是这样的……有栖在网上找到我，说在她身边发生了奇怪的事情。打牌的时候连赢了好多把","莲子");
		showMSG(梅莉);
		say("我跟莲子你玩抽鬼牌时不也是一直赢到天边的吗","梅莉");
		showMSG(莲子);
		say("这，这不一样！我想想……","莲子");
		hideMSG();
		wait(80);
		showMSG(莲子);
		say("对了！有栖能够一下子读出银行卡里的余额哦！","莲子");
		showMSG(梅莉);
		say("不能增加存款的话也只能徒增悲伤而已吧","梅莉");
		showMSG(莲子);
		say("唔……还有，有栖家里突然收到各种意味不明的明信片！","莲子");
		say("而且发件人都没有写名字哦！","莲子");
		showMSG(梅莉);
		say("莲子你不也经常收到吗","梅莉");
		showMSG(莲子);
		say("唉？！我怎么不知道这件事……","莲子");
		showMSG(梅莉);
		say("因为都被我烧掉了。你真的喜欢看些肉麻话的话，我倒是可以写一大堆给你","莲子");
		hideMSG();
		wait(50);
		showMSG(正常);
		say("两人的关系似乎好得要命");
		$(new BaseScriptExecutor() {public void init() {
			npc.turn(IRPGObject.FACE_R).setWalkSpeed(4);
			renko.npc.turn(IRPGObject.FACE_R).setWalkSpeed(4).walk(4).testWalk();
		}});
		move(4);
		showMSG(莲子);
		say("梅莉你没有意识到这是多么奇特的事情呢\n有栖同学，麻烦把身份证给我们看一下吧","莲子");
		hideMSG();
		and(faceTo(renko,IRPGObject.FACE_U));
		faceTo(IRPGObject.FACE_D);
		wait(30);
		//TODO INSERT CG HERE
		showMSG(莲子);
		say("梅莉，你仔细看。一般人照身份证，不可能把头转过去，拍后脑勺吧","莲子");
		hideFG();
		wait(30);
		showMSG(梅莉);
		say("她是怎么用这张身份证上火车的……","梅莉");
		showMSG(莲子);
		say("事实上，她身份证上照片只要在她手中就会发生变化","莲子");
		say("来，有栖同学，你用一下你的能力","莲子");
		hideMSG();
		//TODO INSERT CG HERE
		wait(60);
		showMSG(梅莉);
		say("原来如此……","梅莉");
		say("莲子，你觉得她的能力大概是什么样的？","梅莉");
		showMSG(莲子);
		say("首先，肯定和卡片有关无视材质、无视大小，只要是卡都行","莲子");
		say("至于具体力量，恐怕是读取……或者影响……","莲子");
		showMSG(梅莉);
		say("甚至是操纵卡片吗……真是奇怪","梅莉");
		showMSG(莲子);
		say("嗯，纸牌、身份证、明信片等等，都是卡片呢……","莲子");
		hideMSG();
		setCameraPositionWithHero(-100, 0, true);
		showMSG(莲子);
		say("啊，不知不觉在这里呆了那么久","莲子");
		say("时间不早了，要不我们一边吃午饭一边继续聊天如何？","莲子");
		showMSG(梅莉);
		say("只要莲子你请客，什么都好说","梅莉");
		hideMSG();
		wait(30);
		showMSG(莲子);
		say("好好好…我请就是了，走吧。","莲子");
		//TODO STOP MUSIC?
		$(new BaseScriptExecutor() {public void init() {
			CGController.push(black=(Image) $.add(Res.getNP(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(1,1,1,0).addAction(Actions.fadeIn(2f)).getItem());
		}});
		wait(60);
		$(new BaseScriptExecutor() {public void init() {
			CGController.disposeAll();
		}});
		removeSelf();
	}
}
