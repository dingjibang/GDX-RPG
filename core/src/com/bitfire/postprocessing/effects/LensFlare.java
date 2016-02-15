/*******************************************************************************
 * Copyright 2012 tsagrista
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

package com.bitfire.postprocessing.effects;

import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.bitfire.postprocessing.PostProcessorEffect;
import com.bitfire.postprocessing.filters.Lens;

/** Lens flare effect.
 * @deprecated Please use the better {@link com.bitfire.postprocessing.effects.LensFlare2}.
 * @author Toni Sagrista */
public final class LensFlare extends PostProcessorEffect {
	private Lens lens = null;

	public LensFlare (int viewportWidth, int viewportHeight) {
		setup(viewportWidth, viewportHeight);
	}

	private void setup (int viewportWidth, int viewportHeight) {
		lens = new Lens(viewportWidth, viewportHeight);
	}

	public void setIntensity (float intensity) {
		lens.setIntensity(intensity);
	}

	public float getIntensity () {
		return lens.getIntensity();
	}

	public void setColor (float r, float g, float b) {
		lens.setColor(r, g, b);
	}

	/** Sets the light position in screen coordinates [-1..1].
	 * @param x Light position x screen coordinate,
	 * @param y Light position y screen coordinate. */
	public void setLightPosition (float x, float y) {
		lens.setLightPosition(x, y);
	}

	@Override
	public void dispose () {
		if (lens != null) {
			lens.dispose();
			lens = null;
		}
	}

	@Override
	public void rebind () {
		lens.rebind();
	}

	@Override
	public void render (FrameBuffer src, FrameBuffer dest) {
		restoreViewport(dest);
		lens.setInput(src).setOutput(dest).render();
	}
}
