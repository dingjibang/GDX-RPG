package com.rpsg.rpg.core;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.ScriptableObject;

import com.rpsg.rpg.controller.ScriptController;
import com.rpsg.rpg.object.game.ScriptContext;
import com.rpsg.rpg.object.game.ScriptExecutor;
import com.rpsg.rpg.object.game.Scriptable;
import com.rpsg.rpg.object.map.CollideType;
import com.rpsg.rpg.object.map.NPC;

/**
 * GDX-RPG 脚本<br>
 * 本脚本类仅为地图精灵使用，该类本身为一个线程，且内置了JS脚本，该线程将在地图精灵被碰撞而启动，脚本执行完毕后而结束。<br>
 * 所有线程都被{@link ScriptController}所创建、管理。<br>
 * 该类的内容将根据游戏持续增加。
 * 
 */
public class Script extends Thread{
	
	NPC npc;
	Scriptable js;
	CollideType collideType;
	ScriptContext context;
	boolean executed = false;
	//当前异步执行器，如果是null状态则表示没有什么异步执行器在执行，详见act方法说明
	public ScriptExecutor currentExecutor = null; 
	
	public Script(NPC npc, CollideType collideType, Scriptable js) {
		this.npc = npc;
		this.js = js;
		this.collideType = collideType;
	}
	
	/**线程启动*/
	public void run() {
		setName("============GDX-RPG Script[" + npc + " & " + collideType + "]============");
		//执行JS脚本，将上下文（ScriptContext）作为该脚本的prototype
		try {
			if(js.executable()) {
				Context ctx = Game.getJSContext();
				
				ScriptableObject scope = ctx.initStandardObjects();
				
				//设置js prototype为ScriptContext
				scope.setPrototype(((NativeJavaObject)Context.javaToJS(context = new ScriptContext(this), scope)));
				
				//执行js脚本
				ctx.evaluateString(scope, js.get(ctx, scope), npc + " & " + collideType + " ", 1, null);
				
				Context.exit();
			}
		} catch (Exception e) {
			Log.e("无法执行脚本", e);
			e.printStackTrace();
		}finally {
			//执行完毕了，可以被ScriptController移除了
			executed = true;
		}
		
	}
	
	/**
	 * 本方法无论何时，都是从游戏主线程中的{@link com.rpsg.rpg.controller.ScriptController#act() 调用}的。<br>
	 * <br>
	 * 在GDX-RPG地图脚本系统中，脚本首先分为两种类型：<br>
	 * 第一种同步执行的脚本，他和任何正常的调用相同，即调用一个方法，等待方法执行完毕，然后继续执行JS。<br>
	 * 第二种即为异步执行的脚本，比如让玩家在地图上向前走三步，显然需要等待走完才继续执行脚本。<br>
	 * <br>
	 * 这件事实现起来可以简单可以复杂，简单的话我们可以弄出回调，让脚本传入回调，当玩家走完之后，执行回调。<br>
	 * 但这样的话，脚本将变得没法看下去（一个笑话，一个黑客黑入一家银行，把他们的服务器nodeJS代码最后一页搞到了，结果黑客发现最后一页代码全都是“}}}}}}”233333）<br>
	 * 所以我们为了保证脚本本身的简洁性，我们需要在程序层面来做到这件事情。<br>
	 * <br>
	 *  当一个需要异步执行的方法被执行时候，我们创建一个{@link ScriptExecutor}，他拥有{@link ScriptExecutor#create() create()}和{@link ScriptExecutor#act() act()}两个核心方法。<br>
	 *  顾名思义，create()将在第一次被创建时候执行一次，接下来就每帧都调用它的act()方法。<br>
	 *  在这期间，当前脚本线程将自我{@link Thread#sleep(long) 睡眠}以达到暂停脚本运行的效果，直到{@link ScriptExecutor#isExecuted()}返回true则代表执行完毕，然后线程取消睡眠，继续执行接下来的js脚本。
	 * */
	public void act() {
		//create 异步脚本
		if(currentExecutor != null && currentExecutor.needsCreate()){
			currentExecutor.superCreate();
		//act 异步脚本
		}else if(currentExecutor != null && !currentExecutor.isExecuted()){
			currentExecutor.act();
		//销毁 异步脚本
		}else if(currentExecutor != null && currentExecutor.isExecuted()){
			synchronized (this) {
				this.notifyAll();
			}
		}
	}
	
	/**
	 * 该方法无论如何都是在JS线程里调用的，用来执行一个异步方法
	 */
	public Object set(ScriptExecutor executor) {
		//设置当前执行器
		currentExecutor = executor;
		
		try {
			//冻结线程
			synchronized (this) {
				wait();
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		//获取返回对象（如果有）
		Object obj = currentExecutor.returnObject();
		currentExecutor = null;
		
		return obj;
	}
	
	public boolean executed() {
		return executed;
	}
	
	public boolean equals(NPC npc, CollideType collideType) {
		return npc == this.npc && collideType == this.collideType;
	}
	
}
