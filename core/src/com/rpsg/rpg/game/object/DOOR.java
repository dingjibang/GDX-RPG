package com.rpsg.rpg.game.object;

import com.rpsg.rpg.game.script.Door;
import com.rpsg.rpg.game.script.Teleporter;
import com.rpsg.rpg.object.rpg.DefaultNPC;
import com.rpsg.rpg.object.script.ScriptCollide.COLLIDE_TYPE;

public class DOOR extends DefaultNPC{

	private static final long serialVersionUID = -3859019464901632709L;
	public DOOR(String path, Integer width, Integer height) {
		
		super(path, width, height);
	}

	@Override
	public void init() {
		this.enableCollide=false;
		scripts.put(COLLIDE_TYPE.NEAR.value(), Door.class);
		scripts.put(COLLIDE_TYPE.FOOT.value(),Teleporter.class);
	}
	
}
