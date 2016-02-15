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

import com.badlogic.gdx.graphics.Texture;
import com.bitfire.utils.ShaderLoader;

public final class Vignetting extends Filter<Vignetting> {

	private float x, y;
	private float intensity, saturation, saturationMul;

	private Texture texLut;
	private boolean dolut, dosat;
	private float lutintensity;
	private int[] lutindex;
	private float lutStep, lutStepOffset, lutIndexOffset;
	private float centerX, centerY;

	public enum Param implements Parameter {
		// @formatter:off
		Texture0("u_texture0", 0), TexLUT("u_texture1", 0), VignetteIntensity("VignetteIntensity", 0), VignetteX("VignetteX", 0), VignetteY(
			"VignetteY", 0), Saturation("Saturation", 0), SaturationMul("SaturationMul", 0), LutIntensity("LutIntensity", 0), LutIndex(
			"LutIndex", 0), LutIndex2("LutIndex2", 0), LutIndexOffset("LutIndexOffset", 0), LutStep("LutStep", 0), LutStepOffset(
			"LutStepOffset", 0), CenterX("CenterX", 0), CenterY("CenterY", 0);
		// @formatter:on

		private final String mnemonic;
		private int elementSize;

		private Param (String m, int elementSize) {
			this.mnemonic = m;
			this.elementSize = elementSize;
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

	public Vignetting (boolean controlSaturation) {
		super(ShaderLoader.fromFile("screenspace", "vignetting",
			(controlSaturation ? "#define CONTROL_SATURATION\n#define ENABLE_GRADIENT_MAPPING" : "#define ENABLE_GRADIENT_MAPPING")));
		dolut = false;
		dosat = controlSaturation;

		texLut = null;
		lutindex = new int[2];
		lutindex[0] = -1;
		lutindex[1] = -1;

		lutintensity = 1f;
		lutIndexOffset = 0;
		rebind();
		setCoords(0.8f, 0.25f);
		setCenter(0.5f, 0.5f);
		setIntensity(1f);
	}

	public void setIntensity (float intensity) {
		this.intensity = intensity;
		setParam(Param.VignetteIntensity, intensity);
	}

	public void setSaturation (float saturation) {
		this.saturation = saturation;
		if (dosat) {
			setParam(Param.Saturation, saturation);
		}
	}

	public void setSaturationMul (float saturationMul) {
		this.saturationMul = saturationMul;
		if (dosat) {
			setParam(Param.SaturationMul, saturationMul);
		}
	}

	public void setCoords (float x, float y) {
		this.x = x;
		this.y = y;
		setParams(Param.VignetteX, x);
		setParams(Param.VignetteY, y);
		endParams();
	}

	public void setX (float x) {
		this.x = x;
		setParam(Param.VignetteX, x);
	}

	public void setY (float y) {
		this.y = y;
		setParam(Param.VignetteY, y);
	}

	/** Sets the texture with which gradient mapping will be performed. */
	public void setLut (Texture texture) {
		texLut = texture;
		dolut = (texLut != null);

		if (dolut) {
			lutStep = 1f / (float)texture.getHeight();
			lutStepOffset = lutStep / 2f; // center texel
			setParams(Param.TexLUT, u_texture1);
			setParams(Param.LutStep, lutStep);
			setParams(Param.LutStepOffset, lutStepOffset).endParams();
		}
	}

	public void setLutIntensity (float value) {
		lutintensity = value;
		setParam(Param.LutIntensity, lutintensity);
	}

	public void setLutIndexVal (int index, int value) {
		lutindex[index] = value;

		switch (index) {
		case 0:
			setParam(Param.LutIndex, lutindex[0]);
			break;
		case 1:
			setParam(Param.LutIndex2, lutindex[1]);
			break;
		}

	}

	public void setLutIndexOffset (float value) {
		lutIndexOffset = value;
		setParam(Param.LutIndexOffset, lutIndexOffset);
	}

	/** Specify the center, in normalized screen coordinates. */
	public void setCenter (float x, float y) {
		this.centerX = x;
		this.centerY = y;
		setParams(Param.CenterX, centerX);
		setParams(Param.CenterY, centerY).endParams();
	}

	public float getCenterX () {
		return centerX;
	}

	public float getCenterY () {
		return centerY;
	}

	public int getLutIndexVal (int index) {
		return (int)lutindex[index];
	}

	public float getLutIntensity () {
		return lutintensity;
	}

	public Texture getLut () {
		return texLut;
	}

	public float getX () {
		return x;
	}

	public float getY () {
		return y;
	}

	public float getIntensity () {
		return intensity;
	}

	public float getSaturation () {
		return saturation;
	}

	public float getSaturationMul () {
		return saturationMul;
	}

	public boolean isGradientMappingEnabled () {
		return dolut;
	}

	@Override
	public void rebind () {
		setParams(Param.Texture0, u_texture0);

		setParams(Param.LutIndex, lutindex[0]);
		setParams(Param.LutIndex2, lutindex[1]);
		setParams(Param.LutIndexOffset, lutIndexOffset);

		setParams(Param.TexLUT, u_texture1);
		setParams(Param.LutIntensity, lutintensity);
		setParams(Param.LutStep, lutStep);
		setParams(Param.LutStepOffset, lutStepOffset);

		if (dosat) {
			setParams(Param.Saturation, saturation);
			setParams(Param.SaturationMul, saturationMul);
		}

		setParams(Param.VignetteIntensity, intensity);
		setParams(Param.VignetteX, x);
		setParams(Param.VignetteY, y);
		setParams(Param.CenterX, centerX);
		setParams(Param.CenterY, centerY);
		endParams();
	}

	@Override
	protected void onBeforeRender () {
		inputTexture.bind(u_texture0);
		if (dolut) {
			texLut.bind(u_texture1);
		}
	}
}
