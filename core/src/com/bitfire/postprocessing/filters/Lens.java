/*******************************************************************************
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.bitfire.postprocessing.filters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bitfire.utils.ShaderLoader;

/** Lens flare effect.
 * @author Toni Sagrista **/
public final class Lens extends Filter<Lens> {
	private Vector2 lightPosition = new Vector2();
	private float intensity;
	private Vector3 color = new Vector3();
	private Vector2 viewport = new Vector2();

	public enum Param implements Parameter {
		// @formatter:off
		Texture("u_texture0", 0), LightPosition("u_lightPosition", 2), Intensity("u_intensity", 0), Color("u_color", 3), Viewport(
			"u_viewport", 2);
		// @formatter:on

		private String mnemonic;
		private int elementSize;

		private Param (String mnemonic, int arrayElementSize) {
			this.mnemonic = mnemonic;
			this.elementSize = arrayElementSize;
		}

		@Override
		public String mnemonic () {
			return this.mnemonic;
		}

		@Override
		public int arrayElementSize () {
			return this.elementSize;
		}
	}

	public Lens (float width, float height) {
		super(ShaderLoader.fromFile("screenspace", "lensflare"));
		viewport.set(width, height);
		rebind();
	}

	/** Sets the light position in screen coordinates [-1..1].
	 * @param x
	 * @param y */
	public void setLightPosition (float x, float y) {
		lightPosition.set(x, y);
		setParam(Param.LightPosition, this.lightPosition);
	}

	public Vector2 getLightPosition () {
		return lightPosition;
	}

	public void setLightPosition (Vector2 lightPosition) {
		setLightPosition(lightPosition.x, lightPosition.y);
	}

	public float getIntensity () {
		return intensity;
	}

	public void setIntensity (float intensity) {
		this.intensity = intensity;
		setParam(Param.Intensity, intensity);
	}

	public Vector3 getColor () {
		return color;
	}

	public void setColor (float r, float g, float b) {
		color.set(r, g, b);
		setParam(Param.Color, color);
	}

	public void setViewport (float width, float height) {
		viewport.set(width, height);
		setParams(Param.Viewport, viewport);
	}

	@Override
	public void rebind () {
		// Re-implement super to batch every parameter
		setParams(Param.Texture, u_texture0);
		setParams(Param.LightPosition, lightPosition);
		setParams(Param.Intensity, intensity);
		setParams(Param.Color, color);
		setParams(Param.Viewport, viewport);
		endParams();
	}

	@Override
	protected void onBeforeRender () {
		inputTexture.bind(u_texture0);
	}
}
