package com.rpsg.rpg.desktop;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.V8Executor;
import com.eclipsesource.v8.utils.V8Runnable;
import com.eclipsesource.v8.utils.V8Thread;

public class Test {
	public static void main(String[] args) throws InterruptedException {
		new Test().run();
	}
	
	public void say(String str){
		System.out.println(str);
//		v8.getLocker().release();
//		runtime.getLocker().acquire();
//		System.out.println(v8.getLocker().hasLock());
//		exe.
	}
	
	V8Executor exe=new V8Executor("say('fuck you');\nsay('fuck you 2');",true,"a");
	V8 v8 = V8.createV8Runtime();
	public void run() throws InterruptedException{
		v8.registerJavaMethod(this, "say", "say", new Class<?>[] { String.class});
		v8.registerV8Executor(new V8Object(v8), exe);
		exe.start();
		exe.join();
//		runtime.release();
	}

	private void configureWorker(V8 runtime) {
		runtime.registerJavaMethod(this, "start", "Worker", new Class<?>[] { V8Object.class, String[].class }, true);
		V8Object worker = runtime.getObject("Worker");
		V8Object prototype = runtime.executeObjectScript("Worker.prototype");
		prototype.registerJavaMethod(this, "terminate", "terminate", new Class<?>[] { V8Object.class, Object[].class }, true);
		prototype.registerJavaMethod(this, "postMessage", "postMessage", new Class<?>[] { V8Object.class, String[].class }, true);
		worker.setPrototype(prototype);
		worker.release();
		prototype.release();
	}
}
