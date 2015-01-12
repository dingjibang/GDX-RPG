package com.rpsg.rpg.core;


import box2dLight.RayHandler;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.view.GameViews;

public class Main {
	public static void main(String[] args) {
		Logger.init();
		try {
			final LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
			cfg.title = "RPG";
			cfg.width = 1024;
			cfg.height = 576;
			cfg.samples=0;
			cfg.resizable=true;
			cfg.initialBackgroundColor=Color.WHITE;
			cfg.samples=8;
			RayHandler.setGammaCorrection(true);
			RayHandler.useDiffuseLight(true);
			new LwjglApplication(new GameViews(), cfg);
			Logger.info("底层引擎初始化成功。");
		} catch (Exception e) {
			Logger.faild("底层引擎初始化失败。",e);
		}
	}
}
