package com.rpsg.rpg.object.rpg;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.object.script.ScriptCollide;

public abstract class NPC extends RPGObject{


	private static final long serialVersionUID = -3609365853239176493L;

	public static final String RES_PATH=Setting.WALK+"npcs/";
	
	public abstract void toCollide(ScriptCollide sc);
	public transient Map<CollideType, String> scripts=new HashMap<CollideType, String>();
	
	public volatile transient List<Script> threadPool=new LinkedList<Script>();
	
	public boolean collideZAble=true;
	public boolean collideNearAble=true;
	public boolean collideFootAble=true;
	public boolean collideFaceAble=true;
	public boolean collideFaceZAble=true;
	
	public Map<String,Object> params;
	
	public synchronized void pushThreadAndRun(Script t){
		t.isAlive=true;
		threadPool.add(t);
		t.start();
	}
	
	/**
	 * return if has same class 
	 */
	public void pushThreadAndTryRun(CollideType type){
		for(Script s:threadPool)
			if(s.callType==type)
				return;
		pushThreadAndRun(getScript(type, this));
	}
	
	List<Script> removeList=new LinkedList<Script>();
	public boolean isScriptRunning(){
		for(Script t:threadPool)
			if(t.isAlive && !t.callType.equals(CollideType.auto))
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
	}
	
	public void init(){
		if(params==null)
			return;
		String script = "";
		String global = Gdx.files.internal(Setting.SCRIPT_MAP+"global.js").readString("utf-8");
		for(String key:params.keySet()){
			for(CollideType c:CollideType.values()){
				if(key.contains(c.name().toUpperCase())){
					if(key.endsWith("_SCRIPT"))
						script = Gdx.files.internal(Setting.SCRIPT_MAP+params.get(key)).readString("utf-8");
					else if(key.endsWith("_EXECUTE"))
						script = params.get(key).toString();
						scripts.put(c, script);
				}
			}
		}
		
		for(String key:params.keySet()){
			for(CollideType c:CollideType.values()){
				if(key.contains(c.name().toUpperCase())){
					if(key.endsWith("_SCRIPT_PARAM"))
						script = params.get(key)+"\n"+scripts.get(c);
						scripts.put(c, script);
				}
			}
		}
		
		//TODO add 'import'
	}
	
	public synchronized Script getScript(CollideType type,NPC npc){
		try {
			return new Script().generate(this, type, npc.scripts.get(type));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
