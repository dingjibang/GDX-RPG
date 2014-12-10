package com.rpsg.rpg.object.heros;

import com.rpsg.rpg.object.IRPGObject;
import com.rpsg.rpg.system.Image;
import com.rpsg.rpg.system.Setting;

public class NPC extends IRPGObject{

	public static final int NPC_WIDTH=48;
	public static final int NPC_HEIGHT=64;
	public static final String RES_PATH=Setting.GAME_RES_WALK+"heros/";
	
	public NPC() {
		super();
		this.waitWhenCollide=false;
	}

	public NPC(Image txt) {
		super(txt, NPC_WIDTH, NPC_HEIGHT);
		this.waitWhenCollide=false;
	}

	public NPC(String path) {
		super(RES_PATH+path, NPC_WIDTH, NPC_HEIGHT);
		this.waitWhenCollide=false;
	}
	
}
