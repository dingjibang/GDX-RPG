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

/** Normal filtered anti-aliasing filter.
 * @author Toni Sagrista */
public final class NfaaFilter extends Filter<NfaaFilter> {
	private Vector2 viewportInverse;

	public enum Param implements Parameter {
		// @formatter:off
		Texture("u_texture0", 0), ViewportInverse("u_viewportInverse", 2);
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

	public NfaaFilter (int viewportWidth, int viewportHeight) {
		this(new Vector2(viewportWidth, viewportHeight));
	}

	public NfaaFilter (Vector2 viewportSize) {
		super(ShaderLoader.fromFile("screenspace", "nfaa"));
		this.viewportInverse = viewportSize;
		this.viewportInverse.x = 1f / this.viewportInverse.x;
		this.viewportInverse.y = 1f / this.viewportInverse.y;

		rebind();
	}

	public void setViewportSize (float width, float height) {
		this.viewportInverse.set(1f / width, 1f / height);
		setParam(Param.ViewportInverse, this.viewportInverse);
	}

	public Vector2 getViewportSize () {
		return viewportInverse;
	}

	@Override
	public void rebind () {
		// Re-implement super to batch every parameter
		setParams(Param.Texture, u_texture0);
		setParams(Param.ViewportInverse, viewportInverse);
		endParams();
	}

	@Override
	protected void onBeforeRender () {
		inputTexture.bind(u_texture0);
	}
}
