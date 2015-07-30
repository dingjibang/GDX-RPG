package com.rpsg.rpg.game.script;

import com.badlogic.gdx.graphics.Color;
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
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.display.PostUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Move;
import com.rpsg.rpg.view.GameViews;

public class CGLoop2 extends Script {
	Image black,flash;
	public void init() {
		BatchScript renko=((NPC) findNPC(SUBWAYRENKO.class)).script();
//		$(new BaseScriptExecutor() {public void init() {
//			CGController.push(black=(Image) $.add(Res.getNP(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(Color.BLACK).getItem());
//			PostUtil.showMenu=false;
//		}});
//		playSE("sw.mp3");
//		wait(460);
//		$(new BaseScriptExecutor() {public void init() {
//			HeroController.walk(1);
//			black.addAction(Actions.sequence(Actions.fadeOut(0.3f),Actions.run(new Runnable() {public void run() {
//				CGController.dispose(black);
//			}})));
//		}});
//		wait(120);
//		playSE("noise.wav");
//		$(new BaseScriptExecutor() {public void init() {
//			flash=(Image) $.add(Res.getNP(Setting.GAME_RES_IMAGE_CG+"y11cg.jpg")).setColor(1,1,1,0).setSize(GameUtil.screen_width,GameUtil.screen_height).setPosition(0,0).getItem();
//			flash.setOrigin(Align.topRight);
//			flash.addAction(Actions.sequence(Actions.fadeIn(0.05f),Actions.parallel(Actions.scaleTo(2, 2,0.05f),Actions.moveTo(300,400,0.05f)),Actions.color(Color.RED),Actions.moveTo(-100,200)));
//			CGController.push(flash);
//		}});
//		wait(10);
//		$(new BaseScriptExecutor() {public void init() {
//			CGController.dispose(flash);
//		}});
//		stopAllSE(0);
//		wait(60);
		showMSG();
		say("...");
		hideMSG();
		wait(60);
		
		faceTo(IRPGObject.FACE_R);
		and(faceTo(renko, IRPGObject.FACE_R));
		
		setCameraPositionWithHero(-176, 0, true);
		move(1);
		and(move(renko, 1));
		wait(60);
		
		and(move(renko, IRPGObject.FACE_U));
		removeSelf();
	}
}
