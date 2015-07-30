package com.rpsg.rpg.object.script;

import com.rpsg.rpg.object.rpg.NPC;

public class BatchScript extends Script{
	
	public BatchScript(NPC npc) {
		this.npc=npc;
	}
	public void init() {
		this.isAlive=true;
	}

}
