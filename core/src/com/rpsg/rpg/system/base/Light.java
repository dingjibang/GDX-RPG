package com.rpsg.rpg.system.base;

public class Light {

	public Integer id;
	public box2dLight.Light light;
	
	public Light(Integer id, box2dLight.Light light) {
		this.id = id;
		this.light = light;
	}
}
