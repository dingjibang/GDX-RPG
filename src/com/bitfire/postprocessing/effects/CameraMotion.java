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

package com.bitfire.postprocessing.effects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.bitfire.postprocessing.PostProcessorEffect;
import com.bitfire.postprocessing.filters.CameraBlur;

/** FIXME this effect is INCOMPLETE!
 * 
 * @author bmanuel */
public final class CameraMotion extends PostProcessorEffect {
	private CameraBlur camblur;
	private Matrix4 ctp = new Matrix4();
	private float width, height;

	public CameraMotion (int width, int height) {
		this.width = width;
		this.height = height;
		camblur = new CameraBlur();
		camblur.setNormalDepthMap(null);
	}

	@Override
	public void dispose () {
		camblur.dispose();
	}

	public void setNormalDepthMap (Texture normalDepthMap) {
		camblur.setNormalDepthMap(normalDepthMap);
	}

	public void setMatrices (Matrix4 inv_view, Matrix4 prevViewProj, Matrix4 inv_proj) {
		ctp.set(prevViewProj).mul(inv_view);
		camblur.setCurrentToPrevious(ctp);
		camblur.setInverseProj(inv_proj);
	}

	public void setBlurPasses (int passes) {
		camblur.setBlurPasses(passes);
	}

	public void setBlurScale (float scale) {
		camblur.setBlurScale(scale);
	}

	public void setNearFar (float near, float far) {
		camblur.setNearFarPlanes(near, far);
	}

	public void setDepthScale (float scale) {
		camblur.setDepthScale(scale);
	}

	@Override
	public void rebind () {
		camblur.rebind();
	}

	@Override
	public void render (FrameBuffer src, FrameBuffer dest) {
		if (dest != null) {
			camblur.setViewport(dest.getWidth(), dest.getHeight());
		} else {
			camblur.setViewport(width, height);
		}

		restoreViewport(dest);
		camblur.setInput(src).setOutput(dest).render();
	};
}
