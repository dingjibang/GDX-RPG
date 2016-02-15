package com.rpsg.rpg.object.script;

import com.rpsg.rpg.object.script.Script.exeMode;

public abstract class ScriptExecutor extends BaseScriptExecutor{
	@Override
	public abstract void init();
	public void step(){}
	
	public Script script;
	
	public ScriptExecutor(Script script){
		this.script=script;
	}
	
	public void toInit(){
		script.currentExeced=exeMode.running;
		init();
	}
	
	public void dispose(){
		script.currentExeced=exeMode.stop;
	}
}
