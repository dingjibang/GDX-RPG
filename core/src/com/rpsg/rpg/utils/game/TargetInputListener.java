package com.rpsg.rpg.utils.game;

import com.badlogic.gdx.scenes.scene2d.InputListener;

public class TargetInputListener extends InputListener{
	
	Object target;
	
	public TargetInputListener setTarget(Object actor){
		target = actor;
		return this;
	}
	
	public Object getTarget() {
		return target;
	}
}
