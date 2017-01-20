package com.rpsg.rpg.ui.widget;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.Log;
import com.rpsg.rpg.view.JSView;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.ScriptableObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * GDX-RPG JavaScript 控制的UI组件
 */
public abstract class JSWidget extends Actor{

	/**
	 * 自定义的注释注解，在JS文件里使用"@extends [superClassName]"来声明本组建继承的java类。
	 */
	private static final Pattern p = Pattern.compile("@extends\\s(.+?)\n");

	/**
	 * 创建一个由JS生成的UI组件<br>
	 * JS组件的位置在[assets/script/ui/widget]文件夹下<br>
	 * 所有JS组件必须拥有create(Object param)方法用以初始化，可以继承{@link com.badlogic.gdx.scenes.scene2d scene2d}里的组件，将使用Adapter来伪装生成相应继承类<br>
	 */
	public static Actor of(String jsFilePath){
		Context ctx = Game.getJSContext();
		com.rpsg.rpg.object.game.Scriptable scriptable = Game.script.ui.create(jsFilePath);
		ScriptableObject scope = ctx.initStandardObjects();
		String js = scriptable.get(ctx, scope);

		String extend = null;
		Matcher m = p.matcher(js);
		while (m.find()) {
			String scriptName = m.group(1);
			if(scriptName != null)
				extend = scriptName;
		}

		if(extend == null)
			extend = Actor.class.getName();

		js = "new JavaAdapter(" + extend + ", (function(){\n"+js+"\nthis.toString = () => {return 'JSWidget [" + jsFilePath + "]'};return this}.call({})))";

		Actor actor = null;
		try {
			Object result = ctx.evaluateString(scope, js, jsFilePath, 1, null);
			actor = (Actor)Context.jsToJava(result, Class.forName(extend));

		}catch (Exception e){
			Log.e("无法创建组件", e);
		}

		Context.exit();

		return actor;
	}

}
