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

#ifdef GL_ES
	#define PRECISION mediump
	precision PRECISION float;
#else
	#define PRECISION
#endif

uniform PRECISION sampler2D u_texture0;
uniform float VignetteIntensity;
uniform float VignetteX;
uniform float VignetteY;
uniform float CenterX;
uniform float CenterY;

varying vec2 v_texCoords;


#ifdef CONTROL_SATURATION
	uniform float Saturation;
	uniform float SaturationMul;
	const vec3 GRAYSCALE = vec3(0.3, 0.59, 0.11);

	// 0 = totally desaturated
	// 1 = saturation unchanged
	// higher = increase saturation
	//const float BaseSat = 1;
	//const float BloomSat = 1;

	vec3 adjustSaturation(vec3 color, float saturation) {
		vec3 grey = vec3(dot(color, GRAYSCALE));
		//vec3 grey = vec3((color.r+color.g+color.b)*0.333);	// simple
		return mix(grey, color, saturation);	// correct
	}
#endif



#ifdef ENABLE_GRADIENT_MAPPING
	uniform PRECISION sampler2D u_texture1;
	uniform float LutIntensity;

	uniform int LutIndex;
	uniform int LutIndex2;
	uniform float LutIndexOffset;

	uniform float LutStep;
	uniform float LutStepOffset;

	vec3 do_lookup( vec3 color ) {
		vec3 curveColorA;
		vec3 curveColorB;

		float idxA = float(LutIndex) * LutStep + LutStepOffset;
		float idxB = float(LutIndex2) * LutStep + LutStepOffset;

		curveColorA.r = texture2D( u_texture1, vec2(color.r, idxA ) ).r;
		curveColorA.g = texture2D( u_texture1, vec2(color.g, idxA ) ).g;
		curveColorA.b = texture2D( u_texture1, vec2(color.b, idxA ) ).b;

		curveColorB.r = texture2D( u_texture1, vec2(color.r, idxB ) ).r;
		curveColorB.g = texture2D( u_texture1, vec2(color.g, idxB ) ).g;
		curveColorB.b = texture2D( u_texture1, vec2(color.b, idxB ) ).b;

		return mix(color,mix(curveColorA,curveColorB,LutIndexOffset),LutIntensity);
	}
#endif

void main() {
	vec3 rgb = texture2D(u_texture0, v_texCoords).xyz;
	float d = distance(v_texCoords, vec2(CenterX, CenterY));
	float factor = smoothstep(VignetteX, VignetteY, d);
	rgb = rgb*factor + rgb*(1.0-factor) * (1.0-VignetteIntensity);

#ifdef CONTROL_SATURATION
	rgb = adjustSaturation(rgb,Saturation) * SaturationMul;
#endif


#ifdef ENABLE_GRADIENT_MAPPING
	// theoretically, this conditional though still a branch instruction
	// should be able to shave some cycles instead of blindly performing
	// 3 lookups+mix with a 0 intensity... still, i don't like this
	if( LutIndex > -1 ) {
		rgb = do_lookup(rgb);
	}
#endif

	gl_FragColor = vec4(rgb,1);
}