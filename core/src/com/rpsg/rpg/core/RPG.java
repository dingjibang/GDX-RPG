package com.rpsg.rpg.core;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;

/**
 * GDX-RPG 公共上下文类
 */
public class RPG {
	
	/**设置*/
	public static Setting setting;
	
	/**是否正在手机中运行*/
	public static boolean isMobile(){
		return Gdx.app.getType() == ApplicationType.Android || Gdx.app.getType() == ApplicationType.iOS;
	}
	
	/**初始化*/
	public static void init(){
		setting = Setting.create();
	}
}
