package com.rpsg.rpg.object.script;

public abstract class ScriptExecutor implements BaseScriptExecutor{
	public abstract void init();
	public void step(){}
	
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
