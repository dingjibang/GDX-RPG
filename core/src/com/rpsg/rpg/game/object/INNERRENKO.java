package com.rpsg.rpg.game.object;

import com.rpsg.rpg.game.script.InnerRenko;
import com.rpsg.rpg.object.rpg.CollideType;
import com.rpsg.rpg.object.rpg.DefaultNPC;

public class INNERRENKO extends DefaultNPC{
	private static final long serialVersionUID = -3859019464901632709L;
	public INNERRENKO(String path, Integer width, Integer height) {
		super(path, width, height);
	}

	@Override
	public void init() {
		scripts.put(CollideType.z,InnerRenko.class);
	}
}
