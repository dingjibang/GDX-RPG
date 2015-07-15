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
		if(sc.collideType!=ScriptCollide.COLLIDE_TYPE.AUTO)
			Logger.info("碰撞模块触发["+sc+"]");
		if(!isScriptRunning())
		{
			if(scripts.get(sc.collideType.value())==null)
				return;
			this.pushThreadAndRun(getScript(sc.collideType.value(),this));
		}		
	}
}
