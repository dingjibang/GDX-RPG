
package com.rpsg.rpg.system.box2dLight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Mesh.VertexDataType;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Light which source is at infinite distance
 * 
 * <p>Extends {@link Light}
 * 
 * @author kalle_h
 */
public class DirectionalLight extends Light {

	protected final Vector2 start[];
	protected final Vector2 end[];
	protected float sin;
	protected float cos;

	/**
	 * Creates directional light which source is at infinite distance,
	 * direction and intensity is same everywhere
	 * 
	 * <p>-90 direction is straight from up
	 * 
	 * @param rayHandler
	 *            not {@code null} instance of RayHandler
	 * @param rays
	 *            number of rays - more rays make light to look more realistic
	 *            but will decrease performance, can't be less than MIN_RAYS
	 * @param color
	 *            color, set to {@code null} to use the default color
	 * @param directionDegree
	 *            direction in degrees
	 */
	public DirectionalLight(RayHandler rayHandler, int rays, Color color,
			float directionDegree) {
		
		super(rayHandler, rays, color, Float.POSITIVE_INFINITY, directionDegree);
		
		vertexNum = (vertexNum - 1) * 2;
		start = new Vector2[rayNum];
		end = new Vector2[rayNum];
		for (int i = 0; i < rayNum; i++) {
			start[i] = new Vector2();
			end[i] = new Vector2();
		}
		
		lightMesh = new Mesh(
				VertexDataType.VertexArray, staticLight, vertexNum, 0,
				new VertexAttribute(Usage.Position, 2, "vertex_positions"),
				new VertexAttribute(Usage.ColorPacked, 4, "quad_colors"),
				new VertexAttribute(Usage.Generic, 1, "s"));
		softShadowMesh = new Mesh(
				VertexDataType.VertexArray, staticLight, vertexNum, 0,
				new VertexAttribute(Usage.Position, 2, "vertex_positions"),
				new VertexAttribute(Usage.ColorPacked, 4, "quad_colors"),
				new VertexAttribute(Usage.Generic, 1, "s"));
		
		update();
	}

	@Override
	public void setDirection (float direction) {
		this.direction = direction;
		sin = MathUtils.sinDeg(direction);
		cos = MathUtils.cosDeg(direction);
		if (staticLight) dirty = true;
	}
	
	@Override
	void update () {
		if (staticLight && !dirty) return;
		dirty = false;

		final float width = (rayHandler.x2 - rayHandler.x1);
		final float height = (rayHandler.y2 - rayHandler.y1);
		final float sizeOfScreen = width > height ? width : height;

		float xAxelOffSet = sizeOfScreen * cos;
		float yAxelOffSet = sizeOfScreen * sin;

		// preventing length <0 assertion error on box2d.
		if ((xAxelOffSet * xAxelOffSet < 0.1f) && (yAxelOffSet * yAxelOffSet < 0.1f)) {
			xAxelOffSet = 1;
			yAxelOffSet = 1;
		}
		
		final float widthOffSet = sizeOfScreen * -sin;
		final float heightOffSet = sizeOfScreen * cos;

		float x = (rayHandler.x1 + rayHandler.x2) * 0.5f - widthOffSet;
		float y = (rayHandler.y1 + rayHandler.y2) * 0.5f - heightOffSet;

		final float portionX = 2f * widthOffSet / (rayNum - 1);
		x = (MathUtils.floor(x / (portionX * 2))) * portionX * 2;
		final float portionY = 2f * heightOffSet / (rayNum - 1);
		y = (MathUtils.ceil(y / (portionY * 2))) * portionY * 2;
		for (int i = 0; i < rayNum; i++) {
			final float steppedX = i * portionX + x;
			final float steppedY = i * portionY + y;
			m_index = i;
			start[i].x = steppedX - xAxelOffSet;
			start[i].y = steppedY - yAxelOffSet;

			mx[i] = end[i].x = steppedX + xAxelOffSet;
			my[i] = end[i].y = steppedY + yAxelOffSet;

			if (rayHandler.world != null && !xray) {
				rayHandler.world.rayCast(ray, start[i], end[i]);
			}
		}

		// update light mesh
		// ray starting point
		int size = 0;
		final int arraySize = rayNum;

		for (int i = 0; i < arraySize; i++) {
			segments[size++] = start[i].x;
			segments[size++] = start[i].y;
			segments[size++] = colorF;
			segments[size++] = 1f;
			segments[size++] = mx[i];
			segments[size++] = my[i];
			segments[size++] = colorF;
			segments[size++] = 1f;
		}
		lightMesh.setVertices(segments, 0, size);

		if (!soft || xray) return;

		size = 0;
		for (int i = 0; i < arraySize; i++) {
			segments[size++] = mx[i];
			segments[size++] = my[i];
			segments[size++] = colorF;
			segments[size++] = 1f;

			segments[size++] = mx[i] + softShadowLength * cos;
			segments[size++] = my[i] + softShadowLength * sin;
			segments[size++] = zeroColorBits;
			segments[size++] = 1f;
		}
		softShadowMesh.setVertices(segments, 0, size);
	}

	@Override
	void render () {
		rayHandler.lightRenderedLastFrame++;
		lightMesh.render(
				rayHandler.lightShader, GL20.GL_TRIANGLE_STRIP, 0, vertexNum);
		
		if (soft && !xray) {
			softShadowMesh.render(
				rayHandler.lightShader, GL20.GL_TRIANGLE_STRIP, 0, vertexNum);
		}
	}
	
	@Override
	public boolean contains (float x, float y) {
		boolean oddNodes = false;
		float x2 = mx[rayNum] = start[0].x;
		float y2 = my[rayNum] = start[0].y;
		float x1, y1;
		for (int i = 0; i <= rayNum; x2 = x1, y2 = y1, ++i) {
			x1 = mx[i];
			y1 = my[i];
			if (((y1 < y) && (y2 >= y)) || (y1 >= y) && (y2 < y)) {
				if ((y - y1) / (y2 - y1) * (x2 - x1) < (x - x1)) oddNodes = !oddNodes;
			}
		}
		for (int i = 0; i < rayNum; x2 = x1, y2 = y1, ++i) {
			x1 = start[i].x;
			y1 = start[i].y;
			if (((y1 < y) && (y2 >= y)) || (y1 >= y) && (y2 < y)) {
				if ((y - y1) / (y2 - y1) * (x2 - x1) < (x - x1)) oddNodes = !oddNodes;
			}
		}
		return oddNodes;
	}

	/** Not applicable for this light type **/
	@Deprecated
	@Override
	public void attachToBody (Body body) {
	}
	
	/** Not applicable for this light type **/
	@Deprecated
	@Override
	public void setPosition (float x, float y) {
	}

	/** Not applicable for this light type
	 * <p>Always return {@code null}
	 **/
	@Deprecated
	@Override
	public Body getBody () {
		return null;
	}

	/** Not applicable for this light type
	 * <p>Always return {@code 0}
	 **/
	@Deprecated
	@Override
	public float getX () {
		return 0;
	}

	/** Not applicable for this light type
	 * <p>Always return {@code 0}
	 **/
	@Deprecated
	@Override
	public float getY () {
		return 0;
	}

	/** Not applicable for this light type **/
	@Deprecated
	@Override
	public void setPosition (Vector2 position) {
	}

	/** Not applicable for this light type **/
	@Deprecated
	@Override
	public void setDistance(float dist) {
	}
	
	/** Not applicable for this light type **/
	@Deprecated
	@Override
	public void setIgnoreAttachedBody(boolean flag) {
	}
	
	/** Not applicable for this light type
	 * <p>Always return {@code false}
	 **/
	@Deprecated
	@Override
	public boolean getIgnoreAttachedBody() {
		return false;
	}


}
