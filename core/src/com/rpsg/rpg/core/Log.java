package com.rpsg.rpg.core;

import com.badlogic.gdx.utils.Logger;

/**
 * GDX-RPG 日志工具类
 */
public class Log {
	
	static Logger 	ilogger = new Logger("GDX-RPG", Logger.INFO), 
					dlogger = new Logger("GDX-RPG", Logger.DEBUG),
					elogger = new Logger("GDX-RPG", Logger.ERROR);
	
	/**添加一条 <b>消息</b> 等级的日志*/
	public static void i(String str){
		ilogger.info(str);
	}
	
	/**添加一条 <b>调试</b> 等级的日志*/
	public static void d(String str){
		dlogger.debug(str);
	}
	
	/**添加一条 <b>错误</b> 等级的日志*/
	public static void e(String str){
		elogger.error(str);
	}
}
