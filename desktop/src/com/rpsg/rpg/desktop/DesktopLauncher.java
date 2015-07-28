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

/**
 * PC版本启动器
 * @author dingjibang
 *
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
        GameUtil.isDesktop=true;//给引擎标明现在是桌面版在运行，这样会有一些改动
        Logger.init();//logger是全局的消息管理器，负责输出到控制台。
        
        RayHandler.setGammaCorrection(true);//rayHandler是一个图形颜色渲染（box2dlight），不用在意。
        RayHandler.useDiffuseLight (true);
        
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		boolean debug=false;//如果开启debug模式，窗口小一半，没太多意义233
		config.width=1024/(debug?2:1);
		config.height=576/(debug?2:1);
//      config.backgroundFPS=config.foregroundFPS=40;
        Gdx.files=new LwjglFiles();
        Setting.persistence = Persistence.read();//读取配置文件
        config.initialBackgroundColor= Color.WHITE;
        config.samples=Setting.persistence.antiAliasing?8:0;//如果游戏配置“抗锯齿”打开，则设置抗锯齿8级否则不抗锯齿。
		new LwjglApplication(new GameViews(), config);//gameviews就是咱们游戏的核心了。
	}
}
