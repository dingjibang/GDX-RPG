package com.rpsg.rpg.android;

import android.os.Bundle;
import box2dLight.RayHandler;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.rpsg.rpg.core.Views;

/**
 * GDX-RPG 安卓版本启动器
 */
public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
        
		RayHandler.setGammaCorrection(true);
		RayHandler.useDiffuseLight(true);
		
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.numSamples=8;

		initialize(new Views(), config);
	}


}
