package com.rpsg.rpg.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;
import com.badlogic.gdx.graphics.Color;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Persistence;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.view.GameViews;

import box2dLight.RayHandler;

public class DesktopLauncher {
	public static void main (String[] arg) {
        GameUtil.isDesktop=true;
        Logger.init();
        RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight (true);
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		boolean debug=true;
		config.width=1024/(debug?2:1);
		config.height=576/(debug?2:1);
//      config.backgroundFPS=config.foregroundFPS=40;
        Gdx.files=new LwjglFiles();
        Setting.persistence = Persistence.read();
        config.initialBackgroundColor= Color.WHITE;
        config.samples=Setting.persistence.antiAliasing?8:0;

		new LwjglApplication(new GameViews(), config);
	}
}
