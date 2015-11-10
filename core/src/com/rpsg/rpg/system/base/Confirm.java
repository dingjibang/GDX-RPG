package com.rpsg.rpg.system.base;

import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.rpg.system.ui.HoverView;

public class Confirm {
	public String name;
	public CustomRunnable<HoverView> callBack;

	public Confirm(String name, CustomRunnable<HoverView> callBack) {
		this.name = name;
		this.callBack = callBack;
	}
	
	public static Confirm OK(CustomRunnable<HoverView> run){
		return new Confirm("确认", run);
	}
	
	public static Confirm CANCEL(CustomRunnable<HoverView> run){
		return new Confirm("取消", run);
	}
}
