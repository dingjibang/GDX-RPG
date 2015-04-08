package com.rpsg.rpg.game.object;


import com.rpsg.rpg.game.script.SayHelloWorld;
import com.rpsg.rpg.object.rpg.DefaultNPC;

public class FOONPC extends DefaultNPC{

	private static final long serialVersionUID = -3859019464901632709L;

	public FOONPC(String path, Integer width, Integer height) {
		super(path, width, height);
	}

	public void init() {
		this.waitWhenCollide=true;
		scripts.put(DefaultNPC.AUTO_SCRIPT, SayHelloWorld.class);
	}
	
	public void act(float f){
		super.act(f);
		System.out.println(this.threadPool+"\n\n");
	}
	
}
