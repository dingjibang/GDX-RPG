package com.rpsg.rpg.controller;

import java.util.ArrayList;
import java.util.List;

import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Script;
import com.rpsg.rpg.object.map.CollideType;
import com.rpsg.rpg.object.map.NPC;

/**
 * GDX-RPG 脚本控制器<br>
 * 该类控制所有当前执行的脚本，当一个NPC和玩家产生碰撞时，则创建一个脚本。<br>
 * 要想访问此类，请调用Game.map.script
 */
public class ScriptController {
	List<Script> scripts = new ArrayList<>();
	
	/**
	 * 当产生碰撞时，调用该方法以产生一个脚本
	 * @param npc 哪位NPC产生的碰撞
	 * @param type 碰撞类型
	 */
	public void add(NPC npc, CollideType type) {
		//如果有正在执行的，一样的脚本，则不在加了
		if($.anyMatch(scripts, script -> script.equals(npc, type)))
			return;
		
		//如果NPC拥有当前碰撞的脚本
		if(npc.scripts.containsKey(type)){
			Script script = new Script(npc, type, npc.scripts.get(type));
			scripts.add(script);
			
			//启动线程
			script.start();
		}
	}
	
	public void act() {
		//当有已经执行完毕的脚本，就移除托管
		$.removeIf(scripts, Script::executed);
		
		//act当前所有脚本
		$.each(scripts, Script::act);
		
		
	}
}
