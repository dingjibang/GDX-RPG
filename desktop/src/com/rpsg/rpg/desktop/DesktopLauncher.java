package com.rpsg.rpg.desktop;

import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;
import com.badlogic.gdx.graphics.Color;
import com.rpsg.rpg.core.Views;

/**
 * GDX-RPG PC版本启动器
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
        
		//rayHandler是box2dlight，这里进行预设置。
        RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight (true);
        
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = 1280;
		config.height = 720;
		
		//程序未进入之前，先显示灰色的背景
        config.initialBackgroundColor= Color.valueOf("2c2c2c");
        
        //预先创建Gdx.files以提前读取配置文件
        Gdx.files = new LwjglFiles();
        
//        Path.persistence = Persistence.read();//读取配置文件
//        config.samples=Path.persistence.antiAliasing?8:0;//如果游戏配置“抗锯齿”打开，则设置抗锯齿8级否则不抗锯齿。
        
        //进入入口
		new LwjglApplication(new Views(), config);//gameviews就是咱们游戏的核心了。
        
	}
}
