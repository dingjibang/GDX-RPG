package com.rpsg.rpg.system.text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.rpsg.rpg.system.Setting;

public class TextFilter extends ShaderProgram{

	public TextFilter() {
		super(Gdx.files.internal(Setting.GAME_RES_SHADER+"distancefield.vert"),Gdx.files.internal(Setting.GAME_RES_SHADER+"distancefield.frag"));
		if (!isCompiled()) 
			throw new RuntimeException("Shader compilation failed:\n" + getLog());
	}
	
	
	public void setSmoothing (float smoothing) {
		float delta = 0.5f * MathUtils.clamp(smoothing, 0, 1);
		setUniformf("u_lower", 0.5f - delta);
		setUniformf("u_upper", 0.5f + delta);
	}

}
