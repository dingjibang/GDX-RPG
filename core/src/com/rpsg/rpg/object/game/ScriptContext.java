package com.rpsg.rpg.object.game;

import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.Script;

/**
 * GDX-RPG {@link Script 脚本}上下文<br>
 * 其实把{@link Script}本身作为上下文也不是不可以，只是为了界面简洁就把上下文部分单独抽出来了=。=
 */
public class ScriptContext {
	
	public Script _script;
	
	public ScriptContext(Script script) {
		this._script = script;
	}
	
	public String sleep(int _frame) {
		return (String)_script.set(new ScriptExecutor() {
			int frame;
			public void create() {
				frame = _frame;
			}
			
			public void act() {
				if(frame -- <= 0)
					executed();
			}
		});
	}
	
	public void print(Object object) {
		System.out.println(object);
	}
	
	public void say(JsonValue messageConfig, String str) {
		Game.view.msg.say(_script, messageConfig, null, str);
	}
	
	public void say(String str) {
		Game.view.msg.say(_script, null, null, str);
	}
	
	public void say(String title, String str) {
		Game.view.msg.say(_script, null, title, str);
	}
	
	public void say(JsonValue messageConfig, String title, String str) {
		Game.view.msg.say(_script, messageConfig, title, str);
	}
	
	public void hideMSG() {
		Game.view.msg.hide();
	}
	
	public void showFG(int position, String path) {
		Game.view.fg.show(position, path, null);
	}
	
	public void hideFG(int position) {
		Game.view.fg.hide(position);
	}
	
	public void hideFG() {
		Game.view.fg.hide(null);
	}
	
	public void hide() {
		hideFG();
		hideMSG();
	}
	
}
