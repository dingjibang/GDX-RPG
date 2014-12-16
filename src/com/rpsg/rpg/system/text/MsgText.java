package com.rpsg.rpg.system.text;

public class MsgText {
	public String text;
	
	public MsgText(String text){
		this.text=text;
	}
	
	private int current=0;
	private int currentP=1000; 
	public int speed=50;
	public boolean end=false;
	
	public void next(){
		current++;
	}
	
	public void logic(){
		currentP-=speed;
		if(currentP<=0){
			if(current==text.length()){
				end=true;
				return;
			}
			currentP=1000;
			next();
		}
	}
	
	public String getCurrentText(){
		return text.substring(0,current);
	}
	
}
