// FXAA shader, GLSL code adapted from:
// http://horde3d.org/wiki/index.php5?title=Shading_Technique_-_FXAA
// Whitepaper describing the technique:
// http://developer.download.nvidia.com/assets/gamedev/files/sdk/11/FXAA_WhitePaper.pdf

#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

uniform sampler2D u_texture0;

// The inverse of the viewport dimensions along X and Y
uniform vec2 u_viewportInverse;
uniform float FXAA_REDUCE_MIN;
uniform float FXAA_REDUCE_MUL;
uniform float FXAA_SPAN_MAX;

varying vec2 v_texCoords;

vec4 fxaa(sampler2D texture, vec2 texCoords, vec2 viewportInv) {
	vec3 rgbNW = texture2D(texture,
			texCoords.xy + (vec2(-1.0, -1.0) * viewportInv)).xyz;
	vec3 rgbNE = texture2D(texture,
			texCoords.xy + (vec2(+1.0, -1.0) * viewportInv)).xyz;
	vec3 rgbSW = texture2D(texture,
			texCoords.xy + (vec2(-1.0, +1.0) * viewportInv)).xyz;
	vec3 rgbSE = texture2D(texture,
			texCoords.xy + (vec2(+1.0, +1.0) * viewportInv)).xyz;
	vec3 rgbM = texture2D(texture, texCoords.xy).xyz;

	vec3 luma = vec3(0.299, 0.587, 0.114);
	float lumaNW = dot(rgbNW, luma);
	float lumaNE = dot(rgbNE, luma);
	float lumaSW = dot(rgbSW, luma);
	float lumaSE = dot(rgbSE, luma);
	float lumaM = dot(rgbM, luma);

	float lumaMin = min(lumaM, min(min(lumaNW, lumaNE), min(lumaSW, lumaSE)));
	float lumaMax = max(lumaM, max(max(lumaNW, lumaNE), max(lumaSW, lumaSE)));

	vec2 dir;
	dir.x = -((lumaNW + lumaNE) - (lumaSW + lumaSE));
	dir.y = ((lumaNW + lumaSW) - (lumaNE + lumaSE));

	float dirReduce = max(
			(lumaNW + lumaNE + lumaSW + lumaSE) * (0.25 * FXAA_REDUCE_MUL),
			FXAA_REDUCE_MIN);

	float rcpDirMin = 1.0 / (min(abs(dir.x), abs(dir.y)) + dirReduce);

	dir = min(vec2(FXAA_SPAN_MAX, FXAA_SPAN_MAX),
			max(vec2(-FXAA_SPAN_MAX, -FXAA_SPAN_MAX), dir * rcpDirMin))
			* viewportInv;

	vec3 rgbA =
			(1.0 / 2.0)
					* (texture2D(texture,
							texCoords.xy + dir * (1.0 / 3.0 - 0.5)).xyz
							+ texture2D(texture,
									texCoords.xy + dir * (2.0 / 3.0 - 0.5)).xyz);
	vec3 rgbB =
			rgbA * (1.0 / 2.0)
					+ (1.0 / 4.0)
							* (texture2D(texture,
									texCoords.xy + dir * (0.0 / 3.0 - 0.5)).xyz
									+ texture2D(texture,
											texCoords.xy
													+ dir * (3.0 / 3.0 - 0.5)).xyz);
	float lumaB = dot(rgbB, luma);

	vec4 color = vec4(0.0);

	if ((lumaB < lumaMin) || (lumaB > lumaMax)) {
		color.xyz = rgbA;
	} else {
		color.xyz = rgbB;
	}
	color.a = 1.0;
	return color;
}

void main() {
	gl_FragColor = fxaa(u_texture0, v_texCoords, u_viewportInverse);
}
