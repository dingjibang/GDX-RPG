package com.rpsg.rpg.game.object;

import com.rpsg.rpg.game.script.CGLoop1;
import com.rpsg.rpg.game.script.Door;
import com.rpsg.rpg.game.script.Teleporter;
import com.rpsg.rpg.object.rpg.CollideType;
import com.rpsg.rpg.object.rpg.DefaultNPC;

public class CGLOOP1 extends DefaultNPC{

	private static final long serialVersionUID = -3859019464901632709L;
	public CGLOOP1(String path, Integer width, Integer height) {
		
		super(path, width, height);
	}

	@Override
	public void init() {
		scripts.put(CollideType.auto,CGLoop1.class);
	}
	
}
