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


uniform PRECISION sampler2D u_texture0;	// scene
uniform PRECISION sampler2D u_texture1;	// depthmap
uniform PRECISION mat4 ctp;
uniform PRECISION float near, far;
uniform vec2 viewport;
uniform mat4 inv_proj;
uniform float blur_scale;
uniform float depth_scale;
uniform int blur_passes;

varying vec2 v_texCoords;

vec3 get_eye_normal(){
	vec2 frag_coord = gl_FragCoord.xy/viewport;
	frag_coord = (frag_coord-0.5)*2.0;
	vec4 device_normal = vec4(frag_coord, 0.0, 1.0);
	return normalize((inv_proj * device_normal).xyz);
}

vec3 decode_normal(vec2 enc){
	vec2 fenc = enc*4.0-2.0;
	float f = dot(fenc,fenc);
	float g = sqrt(1.0-f/4.0);
	return vec3(fenc*g, 1.0-f/2.0);
}

float decode_depth(vec2 src){
	float depth = src.x/255.0+src.y;
	depth *= depth_scale;
	return depth*far+near;
}

void main() {
	vec3 eye_ray = get_eye_normal();
	vec4 eye_data = texture2D(u_texture1, v_texCoords);
	vec3 eye_normal = decode_normal(eye_data.xy);
	float eye_depth = decode_depth(eye_data.zw);

	vec4 current = vec4(eye_ray * eye_depth, 1.0);
	vec4 previous = ctp * current;
	previous.xyz /= previous.w;
	previous.xy = previous.xy * 0.5 + 0.5;

	vec2 velocity = (previous.xy - v_texCoords) * 0.5 * blur_scale;

	// blur pass
	vec4 result = vec4(0.0);
	float oneOnNumPasses = 1.0 / float(blur_passes);

	for (int i = 0; i < blur_passes; ++i) {
		// make offset in [-0.5, 0.5] range
		float scale = (float(i) * oneOnNumPasses) - 0.5;
		vec2 offset = scale * velocity;
		result += texture2D(u_texture0, v_texCoords + offset);
	}

	//// Get the initial color at this pixel.
	//result = texture2D(u_texture0, v_texCoords);
	//vec2 texCoord = v_texCoords + velocity;
	//for(int i = 1; i < blur_passes; ++i, texCoord += velocity)
	//{
		//// Add the current color to our color sum.
		//result += texture2D(u_texture0, texCoord);
	//}

	gl_FragColor = result * oneOnNumPasses;
}