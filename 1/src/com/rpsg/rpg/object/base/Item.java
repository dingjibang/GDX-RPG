package com.rpsg.rpg.object.base;

public abstract class Item {
	
	public boolean throwable;
	public String name;
	public String maxCount;
	public String illustration;
	
	public abstract void use();
	
	public String toString(){
		return name;
	}
}
