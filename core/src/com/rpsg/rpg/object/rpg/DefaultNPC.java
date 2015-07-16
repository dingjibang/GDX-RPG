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
		if(sc.collideType!=CollideType.auto)
			Logger.info("碰撞模块触发["+sc+"]");
		if(!isScriptRunning())
		if(sc.collideType!=CollideType.auto){
			if(scripts.get(sc.collideType)!=null)
				this.pushThreadAndRun(getScript(sc.collideType, this));
		}else{
			if(scripts.get(CollideType.auto)!=null)
				this.pushThreadAndTryRun(CollideType.auto);
		}
	}
}
