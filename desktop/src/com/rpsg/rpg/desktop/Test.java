package com.rpsg.rpg.desktop;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.ScriptableObject;

public class Test{
	public Abc abc = new Abc();
	public void postMessage(String... s) throws InterruptedException {
		System.out.println(s[0]);
	}


	public static void main(String[] args) throws InterruptedException {
		new Test().start();
	}


	public void start() {
		Context cx= Context.enter();
		ScriptableObject scope = cx.initStandardObjects();
//		scope.getPrototype().put(null, scope, );
//		cx.new
		String str = "this.postMessage(123);abc.a('cannix uema')"; 
//		scope.setPrototype(((NativeJavaObject)Context.javaToJS(this, scope)));
		Object result = cx.evaluateString(scope, str, null, 1, null);   
        double res = Context.toNumber(result);   
        System.out.println(res);   
	}

	
}
