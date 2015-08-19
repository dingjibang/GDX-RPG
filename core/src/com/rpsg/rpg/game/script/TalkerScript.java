package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.rpg.NPC;
import com.rpsg.rpg.object.script.Script;


public class TalkerScript extends Script{

	public void init() {
		setKeyLocker(true);
		showMSG();
		say(prepareSaying(npc));
		hideMSG();
		setKeyLocker(false);
	}
	
	public static String prepareSaying(NPC npc){
		String pre=(String) npc.params.get("SAYING");
		if(pre==null) 
			return "脚本异常，读取相应信息失败。";
		return pre.replaceAll("\\\\n", "\n");
	}

}
