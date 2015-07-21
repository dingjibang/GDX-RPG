package com.rpsg.rpg.game.object;

import com.rpsg.rpg.game.script.Door;
import com.rpsg.rpg.game.script.TalkerScript;
import com.rpsg.rpg.game.script.Teleporter;
import com.rpsg.rpg.object.rpg.CollideType;
import com.rpsg.rpg.object.rpg.DefaultNPC;

public class Talker extends DefaultNPC{

	private static final long serialVersionUID = -3859019464901632709L;
	public Talker(String path, Integer width, Integer height) {
		super(path, width, height);
	}

	@Override
	public void init() {
		this.enableCollide=false;
		scripts.put(CollideType.z, TalkerScript.class);
		scripts.put(CollideType.facez, TalkerScript.class);
	}
	
}
