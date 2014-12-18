package com.rpsg.rpg.object.script;


public abstract class ScriptExecutor {
	public abstract void init();
	public abstract void step();
	
	public Script script;
	
	public ScriptExecutor(Script script){
		this.script=script;
	}
	
	public void toInit(){
		script.currentExeced=false;
		init();
	}
	
	public void dispose(){
		script.currentExeced=true;
	}
}
