package com.rpsg.rpg.core;

import com.rpsg.rpg.object.game.ScriptContext;
import com.rpsg.rpg.object.map.CollideType;
import com.rpsg.rpg.object.map.NPC;

/**
 * GDX-RPG 脚本<br>
 * 本脚本类仅为地图精灵使用，该类本身为一个线程，且内置了JS脚本，该线程将在地图精灵被碰撞而启动，脚本执行完毕后而结束。<br>
 * 所有线程都被{@link ScriptController}所创建、管理。<br>
 * 该类的内容将根据游戏持续增加。
 * 
 */
public class Script extends Thread{
	
	NPC npc;
	String js;
	CollideType collideType;
	ScriptContext context;
	boolean executed = false;
	
	public Script(NPC npc, CollideType collideType, String js) {
		this.npc = npc;
		this.js = js;
		this.collideType = collideType;
	}
	
	/**线程启动*/
	public void run() {
		//执行JS脚本，将上下文（ScriptContext）作为该脚本的prototype
		Game.executeJS(js, new ScriptContext(this));
		
		//执行完毕了，可以被ScriptController移除了
		executed = true;
	}
	
	public void act() {
		
	}
	
	public boolean executed() {
		return executed;
	}
	
	public boolean equals(NPC npc, CollideType collideType) {
		return npc == this.npc && collideType == this.collideType;
	}
}
