package com.rpsg.rpg.game.object;

import com.rpsg.rpg.game.script.SayHelloWorld;
import com.rpsg.rpg.object.rpgobj.DefaultNPC;
import com.rpsg.rpg.object.script.EasyScript;

public class FOONPC extends DefaultNPC{

	private static final long serialVersionUID = -3859019464901632709L;

	public FOONPC(String path, Integer width, Integer height) {
		super(path, width, height);
	}

	public void init() {
		this.waitWhenCollide=true;
		scripts.put(DefaultNPC.COLLIDE_NAME_Z, SayHelloWorld.class);
//		scripts.put(DefaultNPC.COLLIDE_NAME_Z,ez.class);
	}
	
	public static class ez extends EasyScript{
		public void init() {
			System.out.println("such fuckme");
		}
	}
}
