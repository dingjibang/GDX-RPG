package com.rpsg.rpg.object.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;

import com.rpsg.rpg.core.File;
import com.rpsg.rpg.core.Path;

/**
 * GDX-RPG 脚本
 */
public class Scriptable {
	
	String js;
	List<String> include = new ArrayList<>();
	
	/**
	 * GDX-RPG 全局脚本上下文
	 * 他从[assets/script/system]中预编译常用的js环境脚本（以提升性能），并将上下文提供给{@link com.rpsg.rpg.core.Script}。
	 */
	private static Map<String, Script> completedScripts = new HashMap<>();
	
	public Scriptable(String fileName) {
		js = File.readString(Path.SCRIPT_MAP + fileName);
		
		//为了节省开销，include不允许嵌套，也就是说一个js脚本A，include了脚本B，那么仅仅简单读取B，并不再include其他脚本。
		Pattern p = Pattern.compile("\\/\\/#include\\s(.+?)\n");
		Matcher m = p.matcher(js);
		
		while (m.find()) {
	        String scriptName = m.group(1);
	        if(scriptName != null) 
	        	include.add(scriptName);
	    }
	}
	
	/**
	 * 返回js脚本并且预编译上下文（以提升性能）
	 */
	public String get(Context ctx, org.mozilla.javascript.Scriptable scope) {
		for(String file : include) 
			get(file, ctx).exec(ctx, scope);
		
		return "(function(){\n"+js+"\n}())";
	}
	
	public boolean executable() {
		return js != null && js.trim().length() != 0;
	}
	
	/***
	 * 获取一个预编译脚本，如果没有将缓存此脚本
	 */
	public static Script get(String fileName, Context ctx){
		synchronized(completedScripts) {
			Script script = completedScripts.get(fileName);
			
			if(script == null)
				completedScripts.put(fileName, script = ctx.compileString(File.readString(Path.SCRIPT_SYSTEM + fileName), null, 1, null));
			return script;
		}
		
	}
}
