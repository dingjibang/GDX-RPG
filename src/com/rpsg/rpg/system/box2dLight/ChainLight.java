package com.rpsg.rpg.system.box2dLight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Mesh.VertexDataType;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.Pools;

/**
 * A light whose ray starting points are evenly distributed along a chain of
 * vertices
 * 
 * <p> Extends {@link Light}
 * 
 * @author spruce
 */
public class ChainLight extends Light {
	
	public static float defaultRayStartOffset = 0.001f;
	public float rayStartOffset;
	public final FloatArray chain;

	protected int rayDirection;
	protected float bodyAngle;
	protected float bodyAngleOffset;

	protected Body body;

	protected final FloatArray segmentAngles = new FloatArray();
	protected final FloatArray segmentLengths = new FloatArray();

	protected final float[] startX;
	protected final float[] startY;
	protected final float[] endX;
	protected final float[] endY;

	protected final Vector2 bodyPosition = new Vector2();
	protected final Vector2 tmpEnd = new Vector2();
	protected final Vector2 tmpStart = new Vector2();
	protected final Vector2 tmpPerp = new Vector2();
	protected final Vector2 tmpVec = new Vector2();

	protected final Matrix3 zeroPosition = new Matrix3();
	protected final Matrix3 rotateAroundZero = new Matrix3();
	protected final Matrix3 restorePosition = new Matrix3();

	protected final Rectangle chainLightBounds = new Rectangle();
	protected final Rectangle rayHandlerBounds = new Rectangle();
	
	/**
	 * Creates chain light without vertices, they can be added any time later
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
	 * @param rayDirection
	 *            direction of rays
	 *            <ul>
	 *            <li>1 = left</li>
	 *            <li>-1 = right</li>
 	 *            </ul>
 	 */
	public ChainLight(RayHandler rayHandler, int rays, Color color,
			float distance, int rayDirection) {
		this(rayHandler, rays, color, distance, rayDirection, null);
	}

	/**
	 * Creates chain light from specified vertices
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
	 * @param rayDirection
	 *            direction of rays
	 *            <ul>
	 *            <li>1 = left</li>
	 *            <li>-1 = right</li>
	 *            </ul>
	 * @param chain
	 *            float array of (x, y) vertices from which rays will be
	 *            evenly distributed
	 */
	public ChainLight(RayHandler rayHandler, int rays, Color color,
			float distance, int rayDirection, float[] chain) {
		
		super(rayHandler, rays, color, distance, 0f);
		rayStartOffset = ChainLight.defaultRayStartOffset;
		this.rayDirection = rayDirection;
		vertexNum = (vertexNum - 1) * 2;
		endX = new float[rays];
		endY = new float[rays];
		startX = new float[rays];
		startY = new float[rays];
		this.chain = (chain != null) ?
					 new FloatArray(chain) : new FloatArray();
		
		lightMesh = new Mesh(
				VertexDataType.VertexArray, false, vertexNum, 0,
				new VertexAttribute(Usage.Position, 2, "vertex_positions"),
				new VertexAttribute(Usage.ColorPacked, 4, "quad_colors"),
				new VertexAttribute(Usage.Generic, 1, "s"));
		softShadowMesh = new Mesh(
				VertexDataType.VertexArray, false, vertexNum * 2,
				0, new VertexAttribute(Usage.Position, 2, "vertex_positions"),
				new VertexAttribute(Usage.ColorPacked, 4, "quad_colors"),
				new VertexAttribute(Usage.Generic, 1, "s"));
		setMesh();
	}
	
	@Override
	void update() {
		if (dirty) {
			updateChain();
			applyAttachment();
		} else {
			updateBody();
		}
		
		if (cull()) return;
		if (staticLight && !dirty) return;
		dirty = false;
		
		updateMesh();
	}
	
	@Override
	void render() {
		if (rayHandler.culling && culled) return;
		
		rayHandler.lightRenderedLastFrame++;
		lightMesh.render(
			rayHandler.lightShader, GL20.GL_TRIANGLE_STRIP, 0, vertexNum);
		
		if (soft && !xray) {
			softShadowMesh.render(
				rayHandler.lightShader, GL20.GL_TRIANGLE_STRIP, 0, vertexNum);
		}
	}
	
	/**
	 * Draws a polygon, using ray start and end points as vertices
	 */
	public void debugRender(ShapeRenderer shapeRenderer) {
		shapeRenderer.setColor(Color.YELLOW);
		FloatArray vertices = Pools.obtain(FloatArray.class);
		vertices.clear();
		for (int i = 0; i < rayNum; i++) {
			vertices.addAll(mx[i], my[i]);
		}
		for (int i = rayNum - 1; i > -1; i--) {
			vertices.addAll(startX[i], startY[i]);
		}
		shapeRenderer.polygon(vertices.shrink());
		Pools.free(vertices);
	}
	
	@Override
	public void attachToBody(Body body) {
		attachToBody(body, 0f);
	}
	
	/**
	 * Attaches light to specified body with relative direction offset
	 * 
	 * @param body
	 *            that will be automatically followed, note that the body
	 *            rotation angle is taken into account for the light offset
	 *            and direction calculations
	 * @param degrees
	 *            directional relative offset in degrees 
	 */
	public void attachToBody(Body body, float degrees) {
		this.body = body;
		this.bodyPosition.set(body.getPosition());
		bodyAngleOffset = MathUtils.degreesToRadians * degrees;
		bodyAngle = body.getAngle();
		applyAttachment();
		if (staticLight) dirty = true;
	}
	
	@Override
	public Body getBody() {
		return body;
	}
	
	@Override
	public float getX() {
		return tmpPosition.x;
	}
	
	@Override
	public float getY() {
		return tmpPosition.y;
	}
	
	@Override
	public void setPosition(float x, float y) {
		tmpPosition.x = x;
		tmpPosition.y = y;
		if (staticLight) dirty = true;
	}
	
	@Override
	public void setPosition(Vector2 position) {
		tmpPosition.x = position.x;
		tmpPosition.y = position.y;
		if (staticLight) dirty = true;
	}
	
	@Override
	public boolean contains(float x, float y) {
		// fast fail
		if (!this.chainLightBounds.contains(x, y))
			return false;
		// actual check
		FloatArray vertices = Pools.obtain(FloatArray.class);
		vertices.clear();
		
		for (int i = 0; i < rayNum; i++) {
			vertices.addAll(mx[i], my[i]);
		}
		for (int i = rayNum - 1; i > -1; i--) {
			vertices.addAll(startX[i], startY[i]);
		}
		
		int intersects = 0;
		for (int i = 0; i < vertices.size; i += 2) {
			float x1 = vertices.items[i];
			float y1 = vertices.items[i + 1];
			float x2 = vertices.items[(i + 2) % vertices.size];
			float y2 = vertices.items[(i + 3) % vertices.size];
			if (((y1 <= y && y < y2) || (y2 <= y && y < y1)) &&
					x < ((x2 - x1) / (y2 - y1) * (y - y1) + x1))
				intersects++;
		}
		boolean result = (intersects & 1) == 1;

		Pools.free(vertices);
		return result;
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
	
	/** Not applicable for this light type **/
	@Deprecated
	@Override
	public void setDirection(float directionDegree) {
	}
	
	/**
	 * Calculates ray positions and angles along chain. This should be called
	 * any time the number or values of elements changes in {@link #chain}.
	 */
	public void updateChain() {
		Vector2 v1 = Pools.obtain(Vector2.class);
		Vector2 v2 = Pools.obtain(Vector2.class);
		Vector2 vSegmentStart = Pools.obtain(Vector2.class);
		Vector2 vDirection = Pools.obtain(Vector2.class);
		Vector2 vRayOffset = Pools.obtain(Vector2.class);
		Spinor tmpAngle = Pools.obtain(Spinor.class);
		// Spinors used to represent perpendicular angle of each segment
		Spinor previousAngle = Pools.obtain(Spinor.class);
		Spinor currentAngle = Pools.obtain(Spinor.class);
		Spinor nextAngle = Pools.obtain(Spinor.class);
		// Spinors used to represent start, end and interpolated ray
		// angles for a given segment
		Spinor startAngle = Pools.obtain(Spinor.class);
		Spinor endAngle = Pools.obtain(Spinor.class);
		Spinor rayAngle = Pools.obtain(Spinor.class);
		
		int segmentCount = chain.size / 2 - 1;
		
		segmentAngles.clear();
		segmentLengths.clear();
		float remainingLength = 0;
		
		for (int i = 0, j = 0; i < chain.size - 2; i += 2, j++) {
			v1.set(chain.items[i + 2], chain.items[i + 3])
				.sub(chain.items[i], chain.items[i + 1]);
			segmentLengths.add(v1.len());
			segmentAngles.add(
				v1.rotate90(rayDirection).angle() * MathUtils.degreesToRadians
			);
			remainingLength += segmentLengths.items[j];
		}
		
		int rayNumber = 0;
		int remainingRays = rayNum;
		
		for (int i = 0; i < segmentCount; i++) {
			// get this and adjacent segment angles
			previousAngle.set(
				(i == 0) ?
				segmentAngles.items[i] : segmentAngles.items[i - 1]);
			currentAngle.set(segmentAngles.items[i]);
			nextAngle.set(
				(i == segmentAngles.size - 1) ?
				segmentAngles.items[i] : segmentAngles.items[i + 1]);
			
			// interpolate to find actual start and end angles
			startAngle.set(previousAngle).slerp(currentAngle, 0.5f);
			endAngle.set(currentAngle).slerp(nextAngle, 0.5f);

			int segmentVertex = i * 2;
			vSegmentStart.set(
				chain.items[segmentVertex], chain.items[segmentVertex + 1]);
			vDirection.set(
				chain.items[segmentVertex + 2], chain.items[segmentVertex + 3]
			).sub(vSegmentStart).nor();

			float raySpacing = remainingLength / remainingRays;
			int segmentRays = (i == segmentCount - 1) ?
				remainingRays :
				(int) ((segmentLengths.items[i] / remainingLength) *
						remainingRays);
			
			for (int j = 0; j < segmentRays; j++) {
				float position = j * raySpacing;

				// interpolate ray angle based on position within segment
				rayAngle.set(startAngle).slerp(
					endAngle, position / segmentLengths.items[i]);
				float angle = rayAngle.angle();
				vRayOffset.set(this.rayStartOffset, 0).rotateRad(angle);
				v1.set(vDirection).scl(position).add(vSegmentStart).add(vRayOffset);
				
				this.startX[rayNumber] = v1.x;
				this.startY[rayNumber] = v1.y;
				v2.set(distance, 0).rotateRad(angle).add(v1);
				this.endX[rayNumber] = v2.x;
				this.endY[rayNumber] = v2.y;
				rayNumber++;
			}
			
			remainingRays -= segmentRays;
			remainingLength -= segmentLengths.items[i];
			
		}
		
		Pools.free(v1);
		Pools.free(v2);
		Pools.free(vSegmentStart);
		Pools.free(vDirection);
		Pools.free(vRayOffset);
		Pools.free(previousAngle);
		Pools.free(currentAngle);
		Pools.free(nextAngle);
		Pools.free(startAngle);
		Pools.free(endAngle);
		Pools.free(rayAngle);
		Pools.free(tmpAngle);
	}
	
	/**
	 * Applies attached body initial transform to all lights rays
	 */
	void applyAttachment() {
		if (body == null || staticLight) return;
		
		restorePosition.setToTranslation(bodyPosition);
		rotateAroundZero.setToRotationRad(bodyAngle + bodyAngleOffset);
		for (int i = 0; i < rayNum; i++) {
			tmpVec.set(startX[i], startY[i]).mul(rotateAroundZero).mul(restorePosition);
			startX[i] = tmpVec.x;
			startY[i] = tmpVec.y;
			tmpVec.set(endX[i], endY[i]).mul(rotateAroundZero).mul(restorePosition);
			endX[i] = tmpVec.x;
			endY[i] = tmpVec.y;
		}
	}
	
	protected boolean cull() {
		if (!rayHandler.culling) {
			culled = false;
		} else {
			updateBoundingRects();
			culled = chainLightBounds.width > 0 &&
					 chainLightBounds.height > 0 &&
					 !chainLightBounds.overlaps(rayHandlerBounds);
		}
		return culled;
	}
	
	void updateBody() {
		if (body == null || staticLight) return;
	
		final Vector2 vec = body.getPosition();
		tmpVec.set(0, 0).sub(bodyPosition);
		bodyPosition.set(vec);
		zeroPosition.setToTranslation(tmpVec);
		restorePosition.setToTranslation(bodyPosition);
		rotateAroundZero.setToRotationRad(bodyAngle).inv().rotateRad(body.getAngle());
		bodyAngle = body.getAngle();
		
		for (int i = 0; i < rayNum; i++) {
			tmpVec.set(startX[i], startY[i]).mul(zeroPosition).mul(rotateAroundZero)
				.mul(restorePosition);
			startX[i] = tmpVec.x;
			startY[i] = tmpVec.y;

			tmpVec.set(endX[i], endY[i]).mul(zeroPosition).mul(rotateAroundZero)
				.mul(restorePosition);
			endX[i] = tmpVec.x;
			endY[i] = tmpVec.y;
		}
	}
	
	protected void updateMesh() {
		for (int i = 0; i < rayNum; i++) {
			m_index = i;
			f[i] = 1f;
			tmpEnd.x = endX[i];
			mx[i] = tmpEnd.x;
			tmpEnd.y = endY[i];
			my[i] = tmpEnd.y;
			tmpStart.x = startX[i];
			tmpStart.y = startY[i];
			if (rayHandler.world != null && !xray) {
				rayHandler.world.rayCast(ray, tmpStart, tmpEnd);
			}
		}
		setMesh();
	}
	
	protected void setMesh() {
		int size = 0;
		for (int i = 0; i < rayNum; i++) {
			segments[size++] = startX[i];
			segments[size++] = startY[i];
			segments[size++] = colorF;
			segments[size++] = 1;
			segments[size++] = mx[i];
			segments[size++] = my[i];
			segments[size++] = colorF;
			segments[size++] = 1 - f[i];
		}
		lightMesh.setVertices(segments, 0, size);
		if (!soft || xray) return;

		size = 0;
		for (int i = 0; i < rayNum; i++) {
			segments[size++] = mx[i];
			segments[size++] = my[i];
			segments[size++] = colorF;
			final float s = (1 - f[i]);
			segments[size++] = s;
			tmpPerp.set(mx[i], my[i]).sub(startX[i], startY[i]).nor()
				.scl(softShadowLength * s).add(mx[i], my[i]);
			segments[size++] = tmpPerp.x;
			segments[size++] = tmpPerp.y;
			segments[size++] = zeroColorBits;
			segments[size++] = 0f;
		}
		softShadowMesh.setVertices(segments, 0, size);
	}
	
	/** Internal method for bounding rectangle recalculation **/
	protected void updateBoundingRects() {
		float maxX = startX[0];
		float minX = startX[0];
		float maxY = startY[0];
		float minY = startY[0];

		for (int i = 0; i < rayNum; i++) {
			maxX = maxX > startX[i] ? maxX : startX[i];
			maxX = maxX > mx[i] ? maxX : mx[i];
			minX = minX < startX[i] ? minX : startX[i];
			minX = minX < mx[i] ? minX : mx[i];
			maxY = maxY > startY[i] ? maxY : startY[i];
			maxY = maxY > my[i] ? maxY : my[i];
			minY = minY < startY[i] ? minY : startY[i];
			minY = minY < my[i] ? minY : my[i];
		}
		chainLightBounds.set(minX, minY, maxX - minX, maxY - minY);
		rayHandlerBounds.set(
			rayHandler.x1, rayHandler.y1,
			rayHandler.x2 - rayHandler.x1, rayHandler.y2 - rayHandler.y1);
	}
	
}
