/*******************************************************************************
 * Copyright 2012 bmanuel
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

import com.bitfire.postprocessing.utils.PingPongBuffer;

/** Encapsulates a separable 2D convolution kernel filter
 * 
 * @author bmanuel */
public final class Convolve2D extends MultipassFilter {
	public final int radius;
	public final int length; // NxN taps filter, w/ N=length

	public final float[] weights, offsetsHor, offsetsVert;

	private Convolve1D hor, vert;

	public Convolve2D (int radius) {
		this.radius = radius;
		length = (radius * 2) + 1;

		hor = new Convolve1D(length);
		vert = new Convolve1D(length, hor.weights);

		weights = hor.weights;
		offsetsHor = hor.offsets;
		offsetsVert = vert.offsets;
	}

	public void dispose () {
		hor.dispose();
		vert.dispose();
	}

	public void upload () {
		rebind();
	}

	@Override
	public void rebind () {
		hor.rebind();
		vert.rebind();
	}

	@Override
	public void render (PingPongBuffer buffer) {
		hor.setInput(buffer.capture()).render();
		vert.setInput(buffer.capture()).render();
	}
}
