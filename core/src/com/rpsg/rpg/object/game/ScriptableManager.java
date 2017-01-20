package com.rpsg.rpg.object.game;

import java.util.HashMap;
import java.util.Map;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;

import com.rpsg.rpg.core.File;

/**
 * GDX-RPG 脚本管理器<br>
 * 缓存已编译的脚本
 */
public class ScriptableManager {
	/**缓存编译后的脚本*/
	private Map<String, Script> completedScripts = new HashMap<>();
	/**脚本路径*/
	private String path;
	
	public ScriptableManager(String path) {
		this.path = path;
	}
	
	public Scriptable create(String jsName) {
		jsName = getPath(jsName);

		return new Scriptable(path + jsName, this);
	}
	
	/**编译脚本(如果没有就添加到缓存)*/
	protected Script get(Scriptable scriptable, Context ctx, String fileName) {
		fileName = getPath(fileName);

		synchronized(completedScripts) {
			Script script = completedScripts.get(fileName);
			
			if(script == null)
				completedScripts.put(fileName, script = ctx.compileString(File.readString(path + fileName), null, 1, null));
			return script;
		}
	}

	private static String getPath(String path){
		if(!path.endsWith(".js"))
			path += "@js";
		else path = path.replaceAll("\\.js", "@js");

		path = path.replaceAll("\\.", "/").replaceAll("@js", ".js");

		return path;
	}
}
