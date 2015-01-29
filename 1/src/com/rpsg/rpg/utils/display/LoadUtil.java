package com.rpsg.rpg.utils.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.core.Setting;

public class LoadUtil {

	public static boolean load = false;

	static Texture loader;

	public static void init() {
//		loader = new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOAD + "load.png"));
	}

	public static void draw(SpriteBatch batch) {
		if (load)
			batch.draw(loader, 0, 0);
	}

}
