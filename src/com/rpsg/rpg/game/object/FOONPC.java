package com.rpsg.rpg.game.object;

import com.rpsg.rpg.game.script.SayHelloWorld;
import com.rpsg.rpg.object.ScriptCollide;
import com.rpsg.rpg.object.heros.NPC;
import com.rpsg.rpg.system.Image;

public class FOONPC extends NPC{


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
		scripts.put("face", new SayHelloWorld());
	}
	
	@Override
	public void toCollide(ScriptCollide sc) {
		if(sc.collideType==ScriptCollide.COLLIDE_TYPE_FACE_Z && !isScriptRunning()){
			this.pushThreadAndRun(new Thread(scripts.get("face")));
		}
			
	}

	
}
