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
import com.rpsg.rpg.game.hero.Arisu;
import com.rpsg.rpg.game.object.SUBWAYMARI;
import com.rpsg.rpg.game.object.SUBWAYRENKO;
import com.rpsg.rpg.object.base.MsgType;
import com.rpsg.rpg.object.rpg.IRPGObject;
import com.rpsg.rpg.object.rpg.NPC;
import com.rpsg.rpg.object.rpg.Balloon.BalloonType;
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

public class CafeAuto extends Script {
	Image black;
	public void init() {
		$(new BaseScriptExecutor() {public void init() {
			CGController.push(black=(Image) $.add(Res.getNP(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(Color.BLACK).getItem()).addAction(Actions.sequence(Actions.fadeOut(0.3f),Actions.run(new Runnable() {public void run() {
				CGController.dispose(black);
			}})));;
			PostUtil.showMenu=false;
			HeroController.setVisible(false);
		}});
		setKeyLocker(true);
		
		removeSelf();
	}
}
