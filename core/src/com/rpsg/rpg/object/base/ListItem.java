package com.rpsg.rpg.object.base;

public class ListItem {
	
	public boolean enable=true;
	public String name;
	public Runnable run;
	public Object userObject;
	
	public ListItem(String name) {
		this.name=name;
	}
	
	public ListItem setUserObject(Object u){
		userObject =u;
		return this;
	}
	
	public void run(){
		if(run!=null)
			run.run();
	}
	
	public ListItem setEnable(boolean enable){
		this.enable=enable;
		return this;
	}
	
	public ListItem setRunnable(Runnable run){
		this.run=run;
		return this;
	}
	
	public String toString(){
		return name;
	}

}
