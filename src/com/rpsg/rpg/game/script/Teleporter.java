package com.rpsg.rpg.game.script;

import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.utils.game.Base;
import com.rpsg.rpg.utils.game.Move;

public class Teleporter extends Script {

	@Override
	public void init() {
		System.out.println("tele");
		$(() -> {
			Move.teleportAnotherMap(this, npc.params.get("TELEPORT") + ".tmx",
					Integer.parseInt((String) npc.params.get("TELEPORTX")),
					Integer.parseInt((String) npc.params.get("TELEPORTY")),
					Integer.parseInt((String) npc.params.get("TELEPORTZ")));
		});
	}

}
