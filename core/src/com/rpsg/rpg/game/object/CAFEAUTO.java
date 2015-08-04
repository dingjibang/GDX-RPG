package com.rpsg.rpg.game.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.game.script.*;
import com.rpsg.rpg.object.rpg.CollideType;
import com.rpsg.rpg.object.rpg.DefaultNPC;
import com.rpsg.rpg.object.rpg.Balloon.BalloonType;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.CGController;
import com.rpsg.rpg.system.controller.HeroController;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.display.PostUtil;
import com.rpsg.rpg.utils.game.GameUtil;

public class CAFEAUTO extends DefaultNPC{
	private static final long serialVersionUID = -3859019464901632709L;
	public CAFEAUTO(String path, Integer width, Integer height) {
		super(path, width, height);
	}

	@Override
	public void init() {
		scripts.put(CollideType.auto,Instance.class);
	}
	
	public static class Instance extends Script {
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
			final Script yuki=findNPC("1").script();
			final Script renko=findNPC("2").script();
			final Script mari=findNPC("3").script();
			and(setBalloon(yuki, BalloonType.沉默));
			removeSelf();
		}
	}
	
}
