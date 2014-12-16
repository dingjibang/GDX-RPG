package com.rpsg.rpg.object.heros;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.rpsg.rpg.object.IRPGObject;
import com.rpsg.rpg.object.Script;
import com.rpsg.rpg.object.ScriptCollide;
import com.rpsg.rpg.system.Image;
import com.rpsg.rpg.system.text.Setting;

public abstract class NPC extends IRPGObject{

	public static final String RES_PATH=Setting.GAME_RES_WALK+"npcs/";
	
	public abstract void toCollide(ScriptCollide sc);
	public Map<String, Script> scripts=new HashMap<String, Script>();
	
	public List<Script> threadPool=new LinkedList<Script>();
	
	public void pushThreadAndRun(Script t){
		t.isAlive=true;
		threadPool.add(t); 
	}
	
	List<Script> removeList=new LinkedList<Script>();
	public boolean isScriptRunning(){
		for(Script t:threadPool)
			if(t.isAlive())
				return true;
		return false;
	}
	
	public NPC() {
		super();
		this.waitWhenCollide=false;
		init();
	}

	public NPC(Image txt,Integer width,Integer height) {
		super(txt, width, height);
		this.waitWhenCollide=false;
		init();
	}

	public NPC(String path,Integer width,Integer height) {
		super(RES_PATH+path, width, height);
		this.waitWhenCollide=false;
		init();
	}
	
	public abstract void init();
	
}
