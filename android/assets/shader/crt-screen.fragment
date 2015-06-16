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

/**
 * Credits to Inigo Quilez 2009 (iq/rgba) for the original 'Postpro'
 * code at http://www.iquilezles.org/apps/shadertoy/?p=postpro
 *
 * This version of the original fragment program has been adapted and
 * parameterized to permit runtime configuration.
 *
 * Furthermore it has been reworked to mimic the GL_REPEAT texture
 * wrapping on the FBO due to problems arised when running on a real
 * Android device, equipped with a Tegra2 GPU (Asus TF101).
 */

#ifdef GL_ES
	#define PRECISION mediump
	precision PRECISION float;
#else
	#define PRECISION
#endif

uniform vec3 tint;
uniform float time;
uniform sampler2D u_texture0;
varying vec2 v_texCoords;

#ifdef ENABLE_RGB_SHIFT
	// sane values between ~[-0.003, 0.003];
	uniform float offset;
#endif

#ifdef  ENABLE_CHROMATIC_ABERRATIONS
	// x -> red/cyan, y -> blue/yellow, both in [-1, 1]
	uniform vec2 chromaticDispersion;
#endif

const vec4 Zero = vec4(0.0,0.0,0.0,1.0);

#ifdef ENABLE_BARREL_DISTORTION
	uniform float Distortion;	// ~0.3
	uniform float zoom;

	vec2 barrelDistortion(vec2 coord)
	{
		vec2 cc = coord - 0.5;
		float dist = dot(cc, cc) * Distortion;
		return (coord + cc * (1.0 + dist) * dist);
	}
#endif

#ifdef ENABLE_CHROMATIC_ABERRATIONS

	vec3 colorAberrate(sampler2D tex, vec2 coord)
	{
		// index of refraction of each color channel, causing chromatic dispersion
		vec3 eta = vec3(1.0+chromaticDispersion.x*0.09,
						1.0+chromaticDispersion.x*0.06,
						1.0+chromaticDispersion.x*0.03);

		vec3 eta2 = vec3(1.0+chromaticDispersion.y*0.06,
						 1.0+chromaticDispersion.y*0.06,
						 1.0+chromaticDispersion.y*0.03);

		vec3 frag;

		// apply aberrations, if needed
		if (chromaticDispersion.x == 0.0 && chromaticDispersion.y == 0.0) {
			float x = (coord.x-0.5)+0.5;
			float y = (coord.y-0.5)+0.5;
			frag = texture2D(tex,vec2(x,y)).rgb;
		} else {
			vec2 rCoords = (eta.r*eta2.r)*(coord.xy-0.5)+0.5;
			vec2 gCoords = (eta.g*eta2.g)*(coord.xy-0.5)+0.5;
			vec2 bCoords = (eta.b*eta2.b)*(coord.xy-0.5)+0.5;
			float x = (coord.x-0.5)+0.5;
			float y = (coord.y-0.5)+0.5;

			// avoid edge duplication
			// although this produces left/right banding, only the red channel is needed
			if(rCoords.x<0.0 || rCoords.x>1.0 || rCoords.y<0.0 || rCoords.y >1.0) {
				return Zero.rgb;
			}

			frag = vec3(
				texture2D(tex,rCoords).r,
				texture2D(tex,gCoords).g,
				texture2D(tex,bCoords).b
				//,texture2D(tex,vec2(x,y)).a
			);
	   }

		return frag;
	}
#endif

#ifdef ENABLE_SCAN_DISTORTION

	vec2 scandistort(vec2 uv) {
		float amplitude = 10.0;
		float scan1 = clamp(cos(uv.y * 2.0 + time), 0.0, 1.0);
		float scan2 = clamp(cos(uv.y * 2.0 + time + 4.0) * amplitude, 0.0, 1.0) ;
		float amount = scan1 * scan2 * uv.x;

		//uv.x -= 0.05 * mix(texture2D(iChannel1, vec2(uv.x, amount)).r * amount, amount, 0.9);
		uv.x -= 0.05 * amount;

		return uv;
	}
#endif

void main(void)
{
	vec2 uv = v_texCoords;
	uv.y = 1.0 - uv.y;

#ifdef ENABLE_BARREL_DISTORTION
    uv = barrelDistortion(uv);
    uv = 0.5 + (uv-0.5)*(zoom);

    if(uv.s<0.0 || uv.s>1.0 || uv.t<0.0 || uv.t >1.0) {
        gl_FragColor = Zero;
        return;
    }
#endif

#ifdef ENABLE_SCAN_DISTORTION
	uv = scandistort(uv);
#endif

	vec2 flipped_uv = vec2(uv.x,1.0-uv.y);

	float org_alpha = texture2D(u_texture0,flipped_uv).a;
	vec3 col;

#ifdef ENABLE_RGB_SHIFT
	col.r = texture2D(u_texture0,fract(vec2(uv.x+offset,-uv.y))).r;
	col.g = texture2D(u_texture0,fract(vec2(uv.x+0.000,-uv.y))).g;
	col.b = texture2D(u_texture0,fract(vec2(uv.x-offset,-uv.y))).b;
#endif

#ifdef ENABLE_CHROMATIC_ABERRATIONS
	col = colorAberrate(u_texture0, flipped_uv);
#endif

#ifdef ENABLE_TWEAK_CONTRAST
	// contrast up, lightness down a bit, loose detail in dark areas
	col = clamp(col*0.5+0.5*col*col*1.2,0.0,1.0);
#endif

#ifdef ENABLE_VIGNETTE
	// vignette
	col *= 0.5 + 0.5*16.0*uv.x*uv.y*(1.0-uv.x)*(1.0-uv.y);
#endif

#ifdef ENABLE_TINT
	// tint
	col *= tint;
#endif

#ifdef ENABLE_SCANLINES
	// scanline
	col *= 0.9+0.1*sin(10.0*time-uv.y*1000.0);
#endif

#ifdef ENABLE_PHOSPHOR_VIBRANCE
	// phosphor vibrance
	col *= 0.97+0.03*sin(110.0*time);
#endif

	gl_FragColor = vec4(col,org_alpha);
}