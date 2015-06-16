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
import com.bitfire.postprocessing.filters.NfaaFilter;

/** Implements the normal filter anti-aliasing. Very fast and useful for combining with other post-processing effects.
 * @author Toni Sagrista */
public final class Nfaa extends Antialiasing {
	private NfaaFilter nfaaFilter = null;

	/** Create a NFAA with the viewport size */
	public Nfaa (int viewportWidth, int viewportHeight) {
		setup(viewportWidth, viewportHeight);
	}

	private void setup (int viewportWidth, int viewportHeight) {
		nfaaFilter = new NfaaFilter(viewportWidth, viewportHeight);
	}

	public void setViewportSize (int width, int height) {
		nfaaFilter.setViewportSize(width, height);
	}

	@Override
	public void dispose () {
		if (nfaaFilter != null) {
			nfaaFilter.dispose();
			nfaaFilter = null;
		}
	}

	@Override
	public void rebind () {
		nfaaFilter.rebind();
	}

	@Override
	public void render (FrameBuffer src, FrameBuffer dest) {
		restoreViewport(dest);
		nfaaFilter.setInput(src).setOutput(dest).render();
	}
}
