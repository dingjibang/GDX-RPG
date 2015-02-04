package com.rpsg.rpg.object.rpgObject;

import java.io.Serializable;

public class Walker implements Serializable{
	private static final long serialVersionUID = 1L;
	public int face;
	public int step;
	
	public Walker(int face,int step){
		this.face=face;
		this.step=step;
	}
}
