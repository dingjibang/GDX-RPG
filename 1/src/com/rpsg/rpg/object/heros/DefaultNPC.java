package com.rpsg.rpg.object.heros;


import com.rpsg.rpg.object.ScriptCollide;
import com.rpsg.rpg.system.Image;

public abstract class DefaultNPC extends NPC{
	
	public static final String COLLIDE_NAME_FACE="face";
	public static final String COLLIDE_NAME_NEAR="near";
	public static final String COLLIDE_NAME_FACE_Z="faceZ";
	public static final String COLLIDE_NAME_Z="z";
	public static final String COLLIDE_NAME_FOOT="foot";
	
	public DefaultNPC() {
		super();
	}

	public DefaultNPC(Image txt, Integer width, Integer height) {
		super(txt, width, height);
	}

	public DefaultNPC(String path, Integer width, Integer height) {
		super(path, width, height);
	}

	public abstract void init();
	
	@Override
	public void toCollide(ScriptCollide sc) {
		if(isScriptRunning())
			return;
		switch(sc.collideType){
		case ScriptCollide.COLLIDE_TYPE_FACE_Z:{
			if(scripts.get(DefaultNPC.COLLIDE_NAME_FACE_Z)==null)
				break;
			this.pushThreadAndRun(new Thread(scripts.get(DefaultNPC.COLLIDE_NAME_FACE_Z)));
			break;
		}
		case ScriptCollide.COLLIDE_TYPE_FACE:{
			if(scripts.get(DefaultNPC.COLLIDE_NAME_FACE)==null)
				break;
			this.pushThreadAndRun(new Thread(scripts.get(DefaultNPC.COLLIDE_NAME_FACE)));
			break;
		}
		case ScriptCollide.COLLIDE_TYPE_FOOT:{
			if(scripts.get(DefaultNPC.COLLIDE_NAME_FOOT)==null)
				break;
			this.pushThreadAndRun(new Thread(scripts.get(DefaultNPC.COLLIDE_NAME_FOOT)));
			break;
		}
		case ScriptCollide.COLLIDE_TYPE_NEAR:{
			if(scripts.get(DefaultNPC.COLLIDE_NAME_NEAR)==null)
				break;
			this.pushThreadAndRun(new Thread(scripts.get(DefaultNPC.COLLIDE_NAME_NEAR)));
			break;
		}
		case ScriptCollide.COLLIDE_TYPE_Z:{
			if(scripts.get(DefaultNPC.COLLIDE_NAME_Z)==null)
				break;
			this.pushThreadAndRun(new Thread(scripts.get(DefaultNPC.COLLIDE_NAME_Z)));
			break;
		}
		}
	}
}
