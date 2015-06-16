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
import com.bitfire.utils.ShaderLoader;

/** Fast approximate anti-aliasing filter.
 * @author Toni Sagrista */
public final class FxaaFilter extends Filter<FxaaFilter> {
	private Vector2 viewportInverse;
	private float FXAA_REDUCE_MIN;
	private float FXAA_REDUCE_MUL;
	private float FXAA_SPAN_MAX;

	public enum Param implements Parameter {
		// @formatter:off
		Texture("u_texture0", 0), ViewportInverse("u_viewportInverse", 2), FxaaReduceMin("FXAA_REDUCE_MIN", 0), FxaaReduceMul(
			"FXAA_REDUCE_MUL", 0), FxaaSpanMax("FXAA_SPAN_MAX", 0), ;
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

	public FxaaFilter (int viewportWidth, int viewportHeight) {
		this(new Vector2(viewportWidth, viewportHeight), 1f / 128f, 1f / 8f, 8f);
	}

	public FxaaFilter (int viewportWidth, int viewportHeight, float fxaa_reduce_min, float fxaa_reduce_mul, float fxaa_span_max) {
		this(new Vector2(viewportWidth, viewportHeight), fxaa_reduce_min, fxaa_reduce_mul, fxaa_span_max);
	}

	public FxaaFilter (Vector2 viewportSize, float fxaa_reduce_min, float fxaa_reduce_mul, float fxaa_span_max) {
		super(ShaderLoader.fromFile("screenspace", "fxaa"));
		this.viewportInverse = viewportSize;
		this.viewportInverse.x = 1f / this.viewportInverse.x;
		this.viewportInverse.y = 1f / this.viewportInverse.y;

		this.FXAA_REDUCE_MIN = fxaa_reduce_min;
		this.FXAA_REDUCE_MUL = fxaa_reduce_mul;
		this.FXAA_SPAN_MAX = fxaa_span_max;
		rebind();
	}

	public void setViewportSize (float width, float height) {
		this.viewportInverse.set(1f / width, 1f / height);
		setParam(Param.ViewportInverse, this.viewportInverse);
	}

	/** Sets the parameter. The default value is 1/128.
	 * @param value */
	public void setFxaaReduceMin (float value) {
		this.FXAA_REDUCE_MIN = value;
		setParam(Param.FxaaReduceMin, this.FXAA_REDUCE_MIN);
	}

	/** Sets the parameter. The default value is 1/8.
	 * @param value */
	public void setFxaaReduceMul (float value) {
		this.FXAA_REDUCE_MUL = value;
		setParam(Param.FxaaReduceMul, this.FXAA_REDUCE_MUL);
	}

	/** Sets the parameter. The default value is 8;
	 * @param value */
	public void setFxaaSpanMax (float value) {
		this.FXAA_SPAN_MAX = value;
		setParam(Param.FxaaSpanMax, this.FXAA_SPAN_MAX);
	}

	public Vector2 getViewportSize () {
		return viewportInverse;
	}

	@Override
	public void rebind () {
		// reimplement super to batch every parameter
		setParams(Param.Texture, u_texture0);
		setParams(Param.ViewportInverse, viewportInverse);
		setParams(Param.FxaaReduceMin, FXAA_REDUCE_MIN);
		setParams(Param.FxaaReduceMul, FXAA_REDUCE_MUL);
		setParams(Param.FxaaSpanMax, FXAA_SPAN_MAX);
		endParams();
	}

	@Override
	protected void onBeforeRender () {
		inputTexture.bind(u_texture0);
	}
}
