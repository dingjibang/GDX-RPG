package com.rpsg.rpg.controller;

import com.rpsg.rpg.core.Path;
import com.rpsg.rpg.object.game.Scriptable;
import com.rpsg.rpg.object.game.ScriptableManager;

/**
 * GDX-RPG {@link Scriptable}控制器<br>
 * 缓存、管理游戏内的脚本
 */
public class ScriptableController {
	public ScriptableManager map = new ScriptableManager(Path.SCRIPT_MAP);
	public ScriptableManager ui = new ScriptableManager(Path.SCRIPT_UI);
}
