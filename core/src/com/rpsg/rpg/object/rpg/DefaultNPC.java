package com.rpsg.rpg.object.rpg;



import com.rpsg.rpg.object.script.ScriptCollide;
import com.rpsg.rpg.utils.game.Logger;

public abstract class DefaultNPC extends NPC {
	
	private static final long serialVersionUID = -8871098396830487464L;
	
	
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
			if(scripts.get(CollideType.facez)==null)
				break;
			this.pushThreadAndRun(getScript(CollideType.facez,this));
			break;
		}
		case ScriptCollide.COLLIDE_TYPE_FACE:{
			if(scripts.get(CollideType.face)==null)
				break;
			this.pushThreadAndRun(getScript(CollideType.face,this));
			break;
		}
		case ScriptCollide.COLLIDE_TYPE_FOOT:{
			if(scripts.get(CollideType.foot)==null)
				break;
			this.pushThreadAndRun(getScript(CollideType.foot,this));
			break;
		}
		case ScriptCollide.COLLIDE_TYPE_NEAR:{
			if(scripts.get(CollideType.near)==null)
				break;
			this.pushThreadAndRun(getScript(CollideType.near,this));
			break;
		}
		case ScriptCollide.COLLIDE_TYPE_Z:{
			if(scripts.get(CollideType.z)==null)
				break;
			this.pushThreadAndRun(getScript(CollideType.z,this));
			break;
		}
		case ScriptCollide.AUTO_SCRIPT:{
			if(scripts.get(CollideType.auto)==null)
				break;
			this.pushThreadAndTryRun(CollideType.auto);
		}
		}
		
		
	}
}
