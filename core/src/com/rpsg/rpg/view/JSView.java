package com.rpsg.rpg.view;


import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.ui.view.View;

/**
 * GDX-RPG javascript控制的视窗
 */
public abstract class JSView extends View{
	
	public static JSView of(String jsFilePath) {
		Context ctx = Context.enter();
		com.rpsg.rpg.object.game.Scriptable scriptable = Game.script.ui.create(jsFilePath);
		ScriptableObject scope = ctx.initStandardObjects();
		String js = scriptable.get(ctx, scope);
		
		js = "new JavaAdapter(" + JSView.class.getName() + ", (function(){\n"+js+"\nreturn this}.call({})))";
		JSView view = (JSView) Context.jsToJava(ctx.evaluateString(scope, js, null, 1, null), JSView.class);
		Context.exit();
		
		return view;
	}
	
	public String toString() {
		return "JSView";
	}
	
}
