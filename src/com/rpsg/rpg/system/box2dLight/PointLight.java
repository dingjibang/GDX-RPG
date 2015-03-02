package com.rpsg.rpg.system.box2dLight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

/**
 * Light shaped as a circle with given radius
 * 
 * <p>Extends {@link PositionalLight}
 * 
 * @author kalle_h
 */
public class PointLight extends PositionalLight {

	/**
	 * Creates light shaped as a circle with default radius (15f), color and
	 * position (0f, 0f)
	 * 
	 * @param rayHandler
	 *            not {@code null} instance of RayHandler
	 * @param rays
	 *            number of rays - more rays make light to look more realistic
	 *            but will decrease performance, can't be less than MIN_RAYS
	 */
	public PointLight(RayHandler rayHandler, int rays) {
		this(rayHandler, rays, Light.DefaultColor, 15f, 0f, 0f);
	}
	
	/**
	 * Creates light shaped as a circle with given radius
	 * 
	 * @param rayHandler
	 *            not {@code null} instance of RayHandler
	 * @param rays
	 *            number of rays - more rays make light to look more realistic
	 *            but will decrease performance, can't be less than MIN_RAYS
	 * @param color
	 *            color, set to {@code null} to use the default color
	 * @param distance
	 *            distance of light
	 * @param x
	 *            horizontal position in world coordinates
	 * @param y
	 *            vertical position in world coordinates
	 */
	public PointLight(RayHandler rayHandler, int rays, Color color,
			float distance, float x, float y) {
		super(rayHandler, rays, color, distance, x, y, 0f);
	}
	
	@Override
	public void update () {
		updateBody();
		if (dirty) setEndPoints();
		
		if (cull()) return;
		if (staticLight && !dirty) return;
		
		dirty = false;
		updateMesh();
	}
	
	/**
	 * Sets light distance
	 * 
	 * <p>MIN value capped to 0.1f meter
	 * <p>Actual recalculations will be done only on {@link #update()} call
	 */
	@Override
	public void setDistance(float dist) {
		dist *= RayHandler.gammaCorrectionParameter;
		this.distance = dist < 0.01f ? 0.01f : dist;
		dirty = true;
	}
	
	/** Updates light basing on it's distance and rayNum **/
	void setEndPoints() {
		float angleNum = 360f / (rayNum - 1);
		for (int i = 0; i < rayNum; i++) {
			final float angle = angleNum * i;
			sin[i] = MathUtils.sinDeg(angle);
			cos[i] = MathUtils.cosDeg(angle);
			endX[i] = distance * cos[i];
			endY[i] = distance * sin[i];
		}
	}
	
	/** Not applicable for this light type **/
	@Deprecated
	@Override
	public void setDirection(float directionDegree) {
	}

}
