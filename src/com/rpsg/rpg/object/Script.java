package com.rpsg.rpg.object;

public abstract class Script{
	public int waitTime=0;
	
	public void sleep(int frame){
		waitTime+=frame;
	}
	
	public abstract void run();
	
	public boolean isAlive=false;
	
	public boolean isAlive(){
		return isAlive;
	}
	
	public void dispose(){
		this.isAlive=false;
	}
}
