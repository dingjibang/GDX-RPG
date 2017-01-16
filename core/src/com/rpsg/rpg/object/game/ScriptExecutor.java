package com.rpsg.rpg.object.game;

/**
 * GDX-RPG 异步脚本执行器
 * @see {@link MapScript#act()}
 *
 */
public abstract class ScriptExecutor {
	
	private boolean isExecuted = false;
	
	/**当脚本被创建，还没有执行create()*/
	private boolean needsCreate = true;
	
	/**当脚本执行完毕后，返回的变量。maybe null*/
	private Object returnObject = null;
	
	public void superCreate() {
		needsCreate = false;
		create();
	}
	
	/**每次被创建时执行一次该方法*/
	public abstract void create();
	/**每帧循环时候执行该方法*/
	public abstract void act();
	
	public void executed() {
		isExecuted = true;
	}
	
	public void executed(Object obj) {
		returnObject = obj;
		isExecuted = true;
	}
	
	public Object returnObject() {
		return returnObject;
	}
	
	public boolean isExecuted() {
		return isExecuted;
	}
	
	public boolean needsCreate() {
		return needsCreate;
	}
}
