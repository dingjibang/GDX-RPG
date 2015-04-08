package com.rpsg.rpg.system.base;

import com.rpsg.rpg.object.base.ObjectRunnable;

public class Confirm {
	public String name;
	public ObjectRunnable callBack;

	public Confirm(String name, ObjectRunnable callBack) {
		this.name = name;
		this.callBack = callBack;
	}
	
	public static Confirm OK(ObjectRunnable run){
		return new Confirm("确认", run);
	}
	
	public static Confirm CANCEL(ObjectRunnable run){
		return new Confirm("取消", run);
	}
}
