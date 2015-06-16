package com.rpsg.rpg.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Persistence;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.view.GameViews;

import java.io.File;

import box2dLight.RayHandler;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
        GameUtil.isDesktop=false;
		RayHandler.setGammaCorrection(true);
		RayHandler.useDiffuseLight(true);
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.numSamples=8;

		initialize(new GameViews(), config);
	}


}
