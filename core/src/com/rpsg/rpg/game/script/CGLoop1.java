package com.rpsg.rpg.game.script;

import com.badlogic.gdx.graphics.Color;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.CGController;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.utils.game.Move;

public class CGLoop1 extends Script {
	Image black,cg;
	@Override
	public void init() {
		$(new BaseScriptExecutor() {
			@Override
			public void init() {
				black=Res.getNP(Setting.UI_BASE_IMG);
				black.setSize(GameUtil.screen_width, GameUtil.screen_height);
				black.setColor(Color.BLACK);
				CGController.push(black);
			}
		});
		removeSelf();
	}
}
