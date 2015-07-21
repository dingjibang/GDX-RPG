package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;


public class TalkerScript extends Script{

	@Override
	public void init() {
		setKeyLocker(true);
		showMSG();
		say(npc.params.get("SAYING")==null?"脚本异常，读取相应信息失败。":npc.params.get("SAYING").toString());
		hideMSG();
		setKeyLocker(false);
	}

}
