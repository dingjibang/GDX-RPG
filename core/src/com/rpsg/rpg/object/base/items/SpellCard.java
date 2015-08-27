package com.rpsg.rpg.object.base.items;


public class SpellCard extends Item {
	
	private static final long serialVersionUID = 1L;

	public int magicConsume=0;
	
	public int maxCount=0;
	
	public String illustration2;
	
	public int added=0;
	
	public String toString(){
		return name;
	}
	
	public boolean use(){
		return true;
	}
}
