package com.rpsg.rpg.game.object;


import com.rpsg.rpg.game.script.SayHelloWorld;
import com.rpsg.rpg.game.script.Walker;
import com.rpsg.rpg.object.rpg.DefaultNPC;

public class FOONPC extends DefaultNPC{

	private static final long serialVersionUID = -3859019464901632709L;

	public FOONPC(String path, Integer width, Integer height) {
		super(path, width, height);
	}

	public void init() {
		this.waitWhenCollide=false;
		this.walkSpeed=1f;
		this.
		scripts.put(DefaultNPC.AUTO_SCRIPT, Walker.class);
		scripts.put(DefaultNPC.COLLIDE_NAME_Z, SayHelloWorld.class);
	}
	
}
