package com.rpsg.rpg.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.faendir.rhino_android.RhinoAndroidHelper;
import com.rpsg.gdxQuery.Callback;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.Log;
import com.rpsg.rpg.core.Path;

import java.io.File;

import box2dLight.RayHandler;
import com.rpsg.rpg.core.Views;
import org.mozilla.javascript.Context;

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

		RhinoAndroidHelper helper = new RhinoAndroidHelper(this);
		Game.setContextGetter(new Callback<Context>() {
			public Context run() {
				return helper.enterContext();
			}
		});

		initialize(new Views(), config);
	}


}
