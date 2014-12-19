package com.rpsg.rpg.object.script;

import java.util.LinkedList;
import java.util.List;

import com.rpsg.rpg.object.rpgobj.NPC;


public abstract class Script{
	public int waitTime=0;
	
	public void sleep(int frame){
		waitTime+=frame;
	}
	
	public NPC npc;
	public String callType;
	public void generate(NPC npc,String type){
		this.npc=npc;
		this.callType=type;
		init();
	}
	
	public abstract void init();
	
	public boolean isAlive=false;
	
	public boolean isAlive(){
		return isAlive;
	}
	
	public void dispose(){
		this.isAlive=false;
	}
	
	public List<BaseScriptExecutor> scripts=new LinkedList<BaseScriptExecutor>();
	
	public int point=-1;
	public boolean currentExeced=true;
	
	
	public void run(){
		if(waitTime>0){
			waitTime--;
			return;
		}
		if(currentExeced)
			if(++point==scripts.size()){
				this.dispose();
				return;
			}else{
				if(scripts.get(point) instanceof ScriptExecutor){
					((ScriptExecutor)scripts.get(point)).toInit();
					currentExeced=false;
				}else{
					scripts.get(point).init();
				}
			}
		if(scripts.get(point) instanceof ScriptExecutor)
			((ScriptExecutor)scripts.get(point)).step();
	}
	
	
	public BaseScriptExecutor insert(BaseScriptExecutor exe){
		scripts.remove(scripts.size()-1);
		scripts.add(point+1, exe);
		return exe;
	}
	
	public BaseScriptExecutor add (BaseScriptExecutor exe){
		scripts.add(exe);
		return exe;
	}
	
}
