package com.rpsg.rpg.object.rpg;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.script.EasyScript;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.object.script.ScriptCollide;

public abstract class NPC extends IRPGObject{


	private static final long serialVersionUID = -3609365853239176493L;

	public static final String RES_PATH=Setting.GAME_RES_WALK+"npcs/";
	
	public abstract void toCollide(ScriptCollide sc);
	public transient Map<String, Class<? extends Script>> scripts=new HashMap<String, Class<? extends Script>>();
	
	public transient List<Script> threadPool=new LinkedList<Script>();
	
	public boolean collideZAble=true;
	public boolean collideNearAble=true;
	public boolean collideFootAble=true;
	public boolean collideFaceAble=true;
	public boolean collideFaceZAble=true;
	
	public Map<String,Object> params;
	
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

	public NPC(String path,Integer width,Integer height) {
		super(RES_PATH+path, width, height);
		this.waitWhenCollide=false;
		init();
	}
	
	public abstract void init();
	
	public Script getScript(String type,NPC npc){
		try {
			Script n;
			if(scripts.get(type).equals(EasyScript.class)){
				n=npc.scripts.get(type).newInstance();
				n.generate(npc,type);
			}else{
				n=npc.scripts.get(type).newInstance();
				n.generate(npc,type);
			}
			return n;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
