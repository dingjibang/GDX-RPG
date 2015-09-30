package com.rpsg.rpg.desktop;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

public class Test {
	public static void main(String[] args) {
		new Test().exe();
	}
	
	public void use(){
		((Function)sable.get("use", sable)).call(Context.getCurrentContext(), sable, sable, null);
	}
	
	public void print(Object o){
		System.out.println(o);
	}
	
	transient Scriptable sable;
	
	public Test(Object o) {
		this.sable=(Scriptable) o;
	}
	
	public Test() {
		// TODO Auto-generated constructor stub
	}
	
	public int aaa =3,bbb=4;
	
	public void exe(){
		Context ctx = Context.enter();
		Scriptable sa = ctx.initStandardObjects();
		sa.setPrototype((Scriptable) Context.javaToJS(this, sa));
		Object o = ctx.evaluateString(sa, "[{id:1,name:'fuck',use:function(){java.lang.System.out.println('fuck'+this. id)}}]", null, 1, null);
		ctx.exit();
		NativeArray array = (NativeArray)o;
		NativeObject obj = ((NativeObject)array.get(0));
		((Function)obj.get("use")).call(Context.enter(), obj, obj, null);
//		test.use();
	}
}
