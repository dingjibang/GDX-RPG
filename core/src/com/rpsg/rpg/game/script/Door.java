package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.rpg.NPC;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;


public class Door extends Script{

	@Override
	public void init() {
		set(new BaseScriptExecutor() {
			@Override
			public void init() {
				if (npc.currentImageNo == NPC.FACE_D) {
					npc.layer--;
					npc.currentImageNo = NPC.FACE_U;
					npc.collideFaceAble = npc.collideFaceZAble = npc.collideFootAble = npc.collideNearAble = npc.collideZAble = true;
				}
			}
		});
	}

}
