
package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.g2d.PolygonRegionLoader;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/** Defines a polygon shape on top of a texture region to avoid drawing transparent pixels.
 * @see PolygonRegionLoader
 * @author Stefan Bachmann
 * @author Nathan Sweet */
public class PolygonRegion extends com.badlogic.gdx.graphics.g2d.PolygonRegion{
	float[] textureCoords; // texture coordinates in atlas coordinates
	float[] vertices; // pixel coordinates relative to source image.
	short[] triangles;
	TextureRegion region;

	/** Creates a PolygonRegion by triangulating the polygon coordinates in vertices and calculates uvs based on that. TextureRegion
	 * can come from an atlas.
	 * @param region the region used for drawing
	 * @param vertices contains 2D polygon coordinates in pixels relative to source region */
	public PolygonRegion (TextureRegion region, float[] vertices, short[] triangles) {
		super(region, vertices, triangles);
		this.region = region;
		this.vertices = vertices;
		this.triangles = triangles;

		float[] textureCoords = this.textureCoords = new float[vertices.length];
		float u = region.getU(), v = region.getV();
		float uvWidth = region.getU2() - u;
		float uvHeight = region.getV2() - v;
		int width = region.getRegionWidth();
		int height = region.getRegionHeight();
		for (int i = 0, n = vertices.length; i < n; i++) {
			textureCoords[i] = u + uvWidth * (vertices[i] / width);
			i++;
			textureCoords[i] = v + uvHeight * (1 - (vertices[i] / height));
		}
	}

	/** Returns the vertices in local space. */
	@Override
	public float[] getVertices () {
		return vertices;
	}

	@Override
	public short[] getTriangles () {
		return triangles;
	}

	@Override
	public float[] getTextureCoords () {
		return textureCoords;
	}

	@Override
	public TextureRegion getRegion () {
		return region;
	}
	
	public void setVertices(float[] f){
		this.vertices=f;
	}
}
