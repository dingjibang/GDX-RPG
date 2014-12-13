package com.rpsg.rpg.game.object;

import com.rpsg.rpg.game.script.SayHelloWorld;
import com.rpsg.rpg.object.heros.DefaultNPC;
import com.rpsg.rpg.system.Image;

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
		scripts.put(DefaultNPC.COLLIDE_NAME_FACE_Z, new SayHelloWorld());
	}
	
}
