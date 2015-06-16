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

varying vec2 v_texCoord0;
uniform sampler2D u_texture0;

uniform float blur_div;
uniform float offset_x;
uniform float offset_y;
uniform float zoom;

#ifndef BLUR_LENGTH
#error Please define a BLUR_LENGTH
#endif

#ifndef ONE_ON_BLUR_LENGTH
#error Please define a ONE_ON_BLUR_LENGTH
#endif

// avoid compile errors
#define BLUR_LEN			BLUR_LENGTH
#define ONE_ON_BLUR_LEN		ONE_ON_BLUR_LENGTH


// precompute blur factors (faster, loops will be unrolled)
const float blur_start = 1.0;

// performant version
void main()
{
    float scale = blur_start * zoom;
	vec2 o = vec2(offset_x, offset_y);

	vec4 c = vec4(0);
	for( int i = 0; i < BLUR_LEN; ++i )
	{
		c += texture2D(u_texture0, (v_texCoord0 * scale) + o);
		scale += blur_div;
	}

	gl_FragColor = c * ONE_ON_BLUR_LEN;
}