package com.rpsg.rpg.object.base;

public abstract class Item {
	
	public boolean throwable=true;
	public String name;
	public int maxCount;
	public String illustration;
	public boolean disable=false;
	public abstract void use();
	
	public String toString(){
		return name;
	}
}
