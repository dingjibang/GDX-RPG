package com.rpsg.rpg.core;

import com.badlogic.gdx.utils.Logger;

/**
 * GDX-RPG 日志工具类
 */
public class Log {
	
	private static Logger 	ilogger = new Logger("GDX-RPG[I]", Logger.INFO), 
							dlogger = new Logger("GDX-RPG[D]", Logger.DEBUG),
							elogger = new Logger("GDX-RPG[E]", Logger.ERROR);
	
	/**添加一条 <b>消息</b> 等级的日志*/
	public static void i(Object obj){
		ilogger.info(obj == null ? "[null]" : obj.toString());
	}
	
	/**添加一条 <b>调试</b> 等级的日志*/
	public static void d(Object obj){
		dlogger.debug(obj == null ? "[null]" : obj.toString());
	}
	
	/**添加一条 <b>错误</b> 等级的日志*/
	public static void e(Object obj){
		elogger.error(obj == null ? "[null]" : obj.toString());
	}
	
	/**添加一条 <b>错误</b> 等级的日志*/
	public static void e(Object obj, Throwable error){
		elogger.error(obj == null ? "[null]" : obj.toString(), error);
	}
}
