package com.rpsg.rpg.ui;

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

	private static final Pattern p = Pattern.compile("@extends\\s(.+?)\n");

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

		js = "new JavaAdapter(" + extend + ", (function(){\n"+js+"\nreturn this}.call({})))";

		Actor actor = null;
		try {
			actor = (Actor) Context.jsToJava(ctx.evaluateString(scope, js, jsFilePath, 1, null), Class.forName(extend));

		}catch (Exception e){
			Log.e("无法创建组件", e);
		}

		Context.exit();

		return actor;
	}

}
