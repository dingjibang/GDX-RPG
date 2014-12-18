package com.rpsg.rpg.game.object;

import com.rpsg.rpg.game.script.SayHelloWorld;
import com.rpsg.rpg.object.rpgobj.DefaultNPC;
import com.rpsg.rpg.system.base.Image;

public class FOONPC extends DefaultNPC{


	public FOONPC() {
		super();
	}

	public FOONPC(Image txt, Integer width, Integer height) {
		super(txt, width, height);
	}

	public FOONPC(String path, Integer width, Integer height) {
		super(path, width, height);
	}

	@Override
	public void init() {
		this.waitWhenCollide=false;
		scripts.put(DefaultNPC.COLLIDE_NAME_FACE_Z, SayHelloWorld.class);
	}
	
}
