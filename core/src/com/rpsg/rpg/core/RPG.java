package com.rpsg.rpg.core;

import java.lang.reflect.Field;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.ScriptableObject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.io.Input;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.system.controller.Hover;
import com.rpsg.rpg.utils.display.Toasts;
import com.rpsg.rpg.utils.display.TouchParticle;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.utils.game.TimeUtil;
import com.rpsg.rpg.view.BattleView;
import com.rpsg.rpg.view.GameViews;


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
	public static Toasts toast = new Toasts();
	
	/** GDX-RPG time util <br><b>GDX-RPG 时间管理器</b>**/
	public static TimeUtil time = new TimeUtil();
	
	/** GDX-RPG touch-particle controller <br><b>GDX-RPG 触屏粒子效果管理器</b>*/
	public static TouchParticle touch = new TouchParticle();
	
	/**
	 * put a message to GDX-RPG toast controller<br>
	 * <br><b>压入一条字符串，在下一帧时将会以toast的形式显示到屏幕上</b>
	 * @param msg message
	 * @param color toast border-color.
	 */
	public static void putMessage(String msg,Color color){
		toast.add(msg, color);
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
	public static Object executeJS(String js,Object self){
		try {
			Context ctx = Context.enter();
			if(!GameUtil.isDesktop)
				ctx.setOptimizationLevel(-1);
			
			ScriptableObject scope = ctx.initStandardObjects();
			if(self!=null)
				scope.setPrototype(((NativeJavaObject)Context.javaToJS(self, scope)));
			Object obj = ctx.evaluateString(scope, js, null, 1, null);
			Context.exit();
			return obj;
		} catch (Exception e) {
			Logger.error("无法执行脚本", e);
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * execute a javascript string.<br>
	 * <br><b>执行一段JS脚本。</b>
	 * @param js javascript
	 */
	public static Object executeJS(String js){
		return executeJS(js,null);
	}
	
	/**
	 * send a message to battle view.<br>
	 * <br><b>发送一条消息到战斗控制器里。</b>
	 * @param str message
	 */
	public static void battleMsg(String str){
		if(!RPG.ctrl.battle.isBattle()) return;
		GameViews.gameview.battleView.status.add(str);
	}
	

	public static <T> T jsToJava(Class<T> cls, NativeObject param) {
		try {
			T obj = cls.getConstructor().newInstance();
			for(Object key:param.getAllIds()){
				Field f = obj.getClass().getDeclaredField(key.toString());
				Object val = param.get(key);
				if(val.getClass().equals(Double.class) && f.getType().getName().equalsIgnoreCase("int"))//js double to java int
					val = Double.valueOf(val.toString()).intValue();
				
				f.set(obj, val);
			}
			
			return obj;
		} catch (Exception e) {
			Logger.error("无法从js中生成java对象",e);
			return null;
		}
	}
	
	public static <T> T jsonToJava(Class<T> cls,JsonValue json){
		try {
//			T obj = cls.getConstructor().newInstance();
//			for(JsonValue val : json){
////				val.asBoolean()
//			}
//			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public static <T> T jsonToJava(Class<T> cls,String json){
		return jsonToJava(cls, new JsonReader().parse(json));
	}
	
}
