package com.rpsg.rpg.view;


import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.ui.view.View;

import java.util.regex.Pattern;

/**
 * GDX-RPG javascript控制的视窗
 */
public abstract class JSView extends View {
	
	String jsFilePath;

	/**
	 * 创建一个由JS生成的View<br>
	 * JS组件的位置在[assets/script/ui/view]文件夹下
	 */
	public static JSView of(String jsFilePath) {
		Context ctx = Game.getJSContext();
		com.rpsg.rpg.object.game.Scriptable scriptable = Game.script.ui.create(jsFilePath);
		ScriptableObject scope = ctx.initStandardObjects();
		String js = scriptable.get(ctx, scope);

		js = "new JavaAdapter(" + JSView.class.getName() + ", (function(){\n"+js+"\nreturn this}.call({})))";
		JSView view = (JSView) Context.jsToJava(ctx.evaluateString(scope, js, jsFilePath, 1, null), JSView.class);
		view.jsFilePath = jsFilePath;
		Context.exit();
		
		return view;
	}
	
	public String toString() {
		return "JSView [" + jsFilePath + "]";
	}
	
}
