package com.rpsg.rpg.object.script;

import java.util.ArrayList;
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
	
	public List<ScriptExecutor> scripts=new ArrayList<ScriptExecutor>();
	
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
				currentExeced=false;
				scripts.get(point).toInit();
			}
		scripts.get(point).step();
	}
	
	public ScriptExecutor add(ScriptExecutor exe){
		scripts.add(exe);
		return exe;
	}
}
