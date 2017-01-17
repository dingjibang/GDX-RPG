package com.rpsg.rpg.util;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class SimpleAction extends TemporalAction{

	float start, end, current;
	
	public SimpleAction(float start, float end, float duration, Interpolation interpolation) {
		super(duration, interpolation);
		this.start = this.current = start;
		this.end = end;
	}
	
	protected void update(float percent) {
		current = start + (end - start) * percent; 
	}
	
	public float get() {
		return current;
	}
	
	@Override
	public String toString() {
		return ((Float)get()).toString();
	}

}
