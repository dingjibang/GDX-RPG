package com.rpsg.rpg.core;

import com.badlogic.gdx.utils.Logger;
import com.rpsg.rpg.object.game.ConsoleFromType;

/**
 * GDX-RPG 日志工具类
 */
public class Log {
	
	private static Logger ilogger = new Logger("GDX-RPG[I]", Logger.INFO);
	private static Logger dlogger = new Logger("GDX-RPG[D]", Logger.DEBUG);
	private static Logger elogger = new Logger("GDX-RPG[E]", Logger.ERROR);

	/**添加一条 <b>消息</b> 等级的日志*/
	public static void i(Object obj){
		ilogger.info(obj == null ? "[null]" : obj.toString());
		Console.send(ConsoleFromType.LogInfo, obj == null ? "[null]" : obj.toString(), "ffffff",false);
	}
	
	/**添加一条 <b>调试</b> 等级的日志*/
	public static void d(Object obj){
		dlogger.debug(obj == null ? "[null]" : obj.toString());
		Console.send(ConsoleFromType.LogDebug, obj == null ? "[null]" : obj.toString(), "ffffff", false);
	}
	
	/**添加一条 <b>错误</b> 等级的日志*/
	public static void e(Object obj){
		elogger.error(obj == null ? "[null]" : obj.toString());
		Console.send(ConsoleFromType.LogError, obj == null ? "[null]" : obj.toString(), "ff1818", false);
	}
	
	/**添加一条 <b>错误</b> 等级的日志*/
	public static void e(Object obj, Throwable error){
		elogger.error(obj == null ? "[null]" : obj.toString(), error);

		String text = obj == null ? "[null]" : obj.toString();
		text += "\n";
		text += "[#ff00ff]" + error.getLocalizedMessage() + "[]";
		for(StackTraceElement ele : error.getStackTrace())
			text += "\n      [#fc2929]at " + ele.getClassName() + "." + ele.getMethodName() + ":[][#8493ff]" + ele.getLineNumber() + "[]";

		Console.send(ConsoleFromType.LogError, text, null, false);
	}
}
