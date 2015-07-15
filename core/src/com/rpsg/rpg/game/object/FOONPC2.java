package com.rpsg.rpg.game.object;

import com.rpsg.rpg.game.script.SayFuckMe;
import com.rpsg.rpg.object.rpg.DefaultNPC;
import com.rpsg.rpg.object.script.ScriptCollide.COLLIDE_TYPE;

public class FOONPC2 extends DefaultNPC{

	private static final long serialVersionUID = -3859019464901632709L;

	public FOONPC2(String path, Integer width, Integer height) {
		super(path, width, height);
	}

	@Override
	public void init() {
		this.waitWhenCollide=true;
		scripts.put(COLLIDE_TYPE.Z.value(), SayFuckMe.class);
	}
	
}
