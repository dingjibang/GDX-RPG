package com.rpsg.rpg.system.base;

import com.rpsg.rpg.system.controller.MapController;
import com.rpsg.rpg.utils.display.FG;
import com.rpsg.rpg.utils.display.Msg;
import com.rpsg.rpg.view.GameView;
import com.rpsg.rpg.view.GameViews;

public class Initialization {
	public static void init(GameView gv){
		ThreadPool.init();
		MapController.init(gv);
		Msg.init();
		FG.init();
//		RadarUtil.init(100,new TextureRegion(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOGO+"bg.png"))),200);
//		RadarUtil.show(new int[]{55,30,30,30,30},50,100,100);
	}

	public static void restartGame() {
		GameViews.gameview.dispose();
		GameViews.gameview=new GameView();
		GameViews.gameview.init();
	}
}
