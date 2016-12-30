package com.rpsg.rpg.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.rpsg.rpg.core.Path;
import com.rpsg.rpg.object.base.Persistence;
import com.rpsg.rpg.util.game.GameUtil;
import com.rpsg.rpg.view.GameViews;

import java.io.File;

import box2dLight.RayHandler;

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

		initialize(new GameViews(), config);
	}


}
