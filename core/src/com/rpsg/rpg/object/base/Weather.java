package com.rpsg.rpg.object.base;

public enum Weather{
	no("晴"),rain("雨"),snow("雪");
	
	private String value;
	private Weather(String value){
		this.value = value;
	}
	
	public String value(){
		return value;
	}
}