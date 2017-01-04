package com.rpsg.rpg.ui.view;

import java.util.Map;

/**
 * 可以发送启动参数的View
 */
public abstract class ParameterizableView extends View{
	
	private Map<String, Object> param;
	
	public ParameterizableView() {};
	
	public ParameterizableView(Map<String, Object> map) {
		this.param = map;
	}
	
	public void create() {
		create(param);
	}
	
	public abstract void create(Map<String, Object> param);
	
	
}	
