package com.rpsg.rpg.object.game;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mozilla.javascript.Context;

import com.rpsg.rpg.core.File;

/**
 * GDX-RPG 脚本
 */
public class Scriptable {
	
	String js;
	List<String> include = new ArrayList<>();

	private static final Pattern p = Pattern.compile("@include\\s(.+?)\n");

	ScriptableManager manager;

	protected Scriptable(String fileName, ScriptableManager manager) {
		this.manager = manager;

		js = File.readString(fileName);

		//为了节省开销，include不允许嵌套，也就是说一个js脚本A，include了脚本B，那么仅仅简单读取B，并不再include其他脚本。
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
			manager.get(this, ctx, file).exec(ctx, scope);
		
		return js;
	}
	
	public boolean executable() {
		return js != null && js.trim().length() != 0;
	}
	
}
