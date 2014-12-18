package com.rpsg.rpg.object.script;

import java.util.ArrayList;
import java.util.List;


public abstract class Script{
	public int waitTime=0;
	
	public void sleep(int frame){
		waitTime+=frame;
	}
	
	public Script() {
		this.init();
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
		if(currentExeced)
			if(++point==scripts.size())
				this.dispose();
			else{
				currentExeced=false;
				scripts.get(point).toInit();
			}
		else
			scripts.get(point).step();
	}
	
	public ScriptExecutor add(ScriptExecutor exe){
		scripts.add(exe);
		return exe;
	}
}
