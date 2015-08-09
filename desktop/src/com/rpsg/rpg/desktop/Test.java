package com.rpsg.rpg.desktop;

import java.lang.reflect.Method;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

public class Test {

	public void postMessage(String... s) throws InterruptedException {
		System.out.println(s[0]);
	}

	V8 v8;
	Abc abc = new Abc();

	public void start() throws InterruptedException {
		v8 = V8.createV8Runtime();
		v8.registerJavaMethod(this, "postMessage", "postMessage", new Class<?>[] { String[].class }, true);
		
		v8.registerJavaMethod(new JavaVoidCallback() {public void invoke(V8Object receiver, V8Array parameters) {
			System.out.println("a new 'tex' javascript object was created.");
		}}, "Abc");
		V8Object obj=v8.executeObjectScript("Abc");
		registerBridge(obj, abc);

		v8.executeVoidScript("var tex=new Abc();tex.a('a');postMessage('b')");
	}

	public void tex() {
	}

	public static void main(String[] args) throws InterruptedException {
		new Test().start();
	}

	public void registerBridge(V8Object v8o, Object obj) {
		Class<?> c = obj.getClass();
		Method[] methods = c.getDeclaredMethods();
		for (Method m : methods){ 
			V8Object o = v8o.getRutime().executeObjectScript(c.getSimpleName() + ".prototype");
			o.registerJavaMethod(obj, m.getName(), m.getName(), m.getParameterTypes(), false);
			o.release();
		}
	}
}
