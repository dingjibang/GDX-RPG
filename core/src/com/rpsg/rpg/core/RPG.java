package com.rpsg.rpg.core;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.ScriptableObject;

import com.rpsg.rpg.io.Input;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.system.controller.Hover;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.display.AlertUtil;
import com.rpsg.rpg.utils.display.TouchParticle;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.utils.game.TimeUtil;


/**
 * GDX-RPG Engine Context Class <br>
 * All RPG game object you can visitd by this class<br>
 * <br>
 * <b>GDX-RPG 引擎核心上下文</b>
 * @author dingjibang
 */
public class RPG {
	/** GDX-RPG game persistence data <br><b>GDX-RPG 当前游戏存档</b>**/
	public static Global global = new Global();
	
	/** GDX-RPG Controllers <br><b>GDX-RPG 各种控制器（游戏内）</b> **/
	public static Controllers ctrl = new Controllers();
	
	/** GDX-RPG map data context <br><b>GDX-RPG 当前游戏地图数据</b>**/
	public static Maps maps = new Maps();
	
	/** GDX-RPG popup view controller <br><b>GDX-RPG 弹出窗口管理器</b>**/
	public static Hover popup = new Hover();
	
	/** GDX-RPG input controller <br><b>GDX-RPG 输入控制器</b>**/
	public static Input input = new Input();
	
	/** GDX-RPG toast util <br><b>GDX-RPG 提示（类似安卓的toast）控制器</b>**/
	public static AlertUtil toast = new AlertUtil();
	
	/** GDX-RPG time util <br><b>GDX-RPG 时间管理器</b>**/
	public static TimeUtil time = new TimeUtil();
	
	/** GDX-RPG touch-particle controller <br><b>GDX-RPG 触屏粒子效果管理器</b>*/
	public static TouchParticle touch = new TouchParticle();
	
	/**
	 * put a message to GDX-RPG toast controller<br>
	 * <br><b>压入一条字符串，在下一帧时将会以toast的形式显示到屏幕上</b>
	 * @param msg message
	 * @param image the message {@link AlertUtil type} (e.g. AlertUtil.Red/AlertUtil.Yellow)
	 */
	public static void putMessage(String msg,Image image){
		toast.add(msg, image);
	}
	
	/**
	 * get {@link Global.flag} by key<br>
	 * <br><b>获取游戏存档变量</b>
	 * @param key
	 * @return the value of key
	 */
	public static Object getFlag(Object key){
		return global.flag.get(key);
	}
	
	/**
	 * set {@link Global.flag} by key<br>
	 * <br><b>设置游戏存档变量</b>
	 * @param key
	 * @param value
	 */
	public static void setFlag(Object key,Object value){
		global.flag.put(key, value);
	}
	
	/**
	 * execute a java-script string ,and set the "this" object to executor.<br>
	 * <br><b>执行一段JS脚本，并且设置本脚本里“this”变量的java变量指向。</b>
	 * @param js javascript
	 * @param self "this" object
	 */
	public static boolean executeJS(String js,Object self){
		try {
			Context ctx = Context.enter();
			if(!GameUtil.isDesktop)
				ctx.setOptimizationLevel(-1);
			
			ScriptableObject scope =ctx.initStandardObjects();
			if(self!=null)
				scope.setPrototype(((NativeJavaObject)Context.javaToJS(self, scope)));
			ctx.evaluateString(scope, js, null, 1, null);
			Context.exit();
			return true;
		} catch (Exception e) {
			Logger.error("无法执行脚本", e);
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * execute a java-script string.<br>
	 * <br><b>执行一段JS脚本。</b>
	 * @param js javascript
	 */
	public static boolean executeJS(String js){
		return executeJS(js,null);
	}
}
