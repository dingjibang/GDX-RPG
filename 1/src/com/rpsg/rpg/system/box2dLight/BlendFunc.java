package com.rpsg.rpg.system.box2dLight;

import com.badlogic.gdx.Gdx;

/**
 * Helper class that stores source and destination factors for blending
 */
public class BlendFunc {
	
	final int default_sfactor;
	final int default_dfactor;
	int sfactor;
	int dfactor;
	
	public BlendFunc(int sfactor, int dfactor) {
		this.default_sfactor = sfactor;
		this.default_dfactor = dfactor;
		this.sfactor = sfactor;
		this.dfactor = dfactor;
	}
	
	/**
	 * Sets source and destination blending factors
	 */
	public void set(int sfactor, int dfactor) {
		this.sfactor = sfactor;
		this.dfactor = dfactor;
	}
	
	/**
	 * Resets source and destination blending factors to default values
	 * that were set on instance creation
	 */
	public void reset() {
		sfactor = default_sfactor;
		dfactor = default_dfactor;
	}
	
	/**
	 * Calls glBlendFunc with own source and destination factors
	 */
	public void apply() {
		Gdx.gl20.glBlendFunc(sfactor, dfactor);
	}
	
}

