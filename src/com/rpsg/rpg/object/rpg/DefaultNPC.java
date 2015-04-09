package com.rpsg.rpg.object.rpg;



import com.rpsg.rpg.object.script.ScriptCollide;
import com.rpsg.rpg.utils.game.Logger;

public abstract class DefaultNPC extends NPC {
	
	private static final long serialVersionUID = -8871098396830487464L;
	public static final String COLLIDE_NAME_FACE="face";
	public static final String COLLIDE_NAME_NEAR="near";
	public static final String COLLIDE_NAME_FACE_Z="faceZ";
	public static final String COLLIDE_NAME_Z="z";
	public static final String COLLIDE_NAME_FOOT="foot";
	public static final String AUTO_SCRIPT="auto";
	
	public DefaultNPC() {
		super();
	}

	public DefaultNPC(String path, Integer width, Integer height) {
		super(path, width, height);
	}

	public abstract void init();
	
	
	@Override
	public void toCollide(ScriptCollide sc) {
		if(sc.collideType!=ScriptCollide.AUTO_SCRIPT)
			Logger.info("碰撞模块触发["+sc+"]");
		if(!isScriptRunning())
		switch(sc.collideType){
		case ScriptCollide.COLLIDE_TYPE_FACE_Z:{
			if(scripts.get(DefaultNPC.COLLIDE_NAME_FACE_Z)==null)
				break;
			this.pushThreadAndRun(getScript(DefaultNPC.COLLIDE_NAME_FACE_Z,this));
			break;
		}
		case ScriptCollide.COLLIDE_TYPE_FACE:{
			if(scripts.get(DefaultNPC.COLLIDE_NAME_FACE)==null)
				break;
			this.pushThreadAndRun(getScript(DefaultNPC.COLLIDE_NAME_FACE,this));
			break;
		}
		case ScriptCollide.COLLIDE_TYPE_FOOT:{
			if(scripts.get(DefaultNPC.COLLIDE_NAME_FOOT)==null)
				break;
			this.pushThreadAndRun(getScript(DefaultNPC.COLLIDE_NAME_FOOT,this));
			break;
		}
		case ScriptCollide.COLLIDE_TYPE_NEAR:{
			if(scripts.get(DefaultNPC.COLLIDE_NAME_NEAR)==null)
				break;
			this.pushThreadAndRun(getScript(DefaultNPC.COLLIDE_NAME_NEAR,this));
			break;
		}
		case ScriptCollide.COLLIDE_TYPE_Z:{
			if(scripts.get(DefaultNPC.COLLIDE_NAME_Z)==null)
				break;
			this.pushThreadAndRun(getScript(DefaultNPC.COLLIDE_NAME_Z,this));
			break;
		}
		case ScriptCollide.AUTO_SCRIPT:{
			if(scripts.get(DefaultNPC.AUTO_SCRIPT)==null)
				break;
			this.pushThreadAndTryRun(DefaultNPC.AUTO_SCRIPT);
		}
		}
		
		
	}
}
