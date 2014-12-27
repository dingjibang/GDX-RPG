package com.rpsg.rpg.system.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.control.MapControler;
import com.rpsg.rpg.utils.display.FG;
import com.rpsg.rpg.utils.display.Msg;
import com.rpsg.rpg.utils.display.RadarUtil;
import com.rpsg.rpg.view.GameView;

public class Initialization {
	public static void init(GameView gv){
		ThreadPool.init();
		MapControler.init(gv);
		Msg.init();
		FG.init();
		RadarUtil.init(100,new TextureRegion(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOGO+"bg.png"))),200);
		RadarUtil.show(new int[]{55,30,30,30,30},50,100,100);
	}
}
