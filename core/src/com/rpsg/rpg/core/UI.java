package com.rpsg.rpg.core;

import com.badlogic.gdx.graphics.Texture;
import com.rpsg.rpg.ui.Image;

/**
 * GDX-RPG UI工具类
 */
public class UI {
	public static Texture base;
	
	public static Image base(){
		return new Image(base);
	}
	
	public static void init(){
		base = Res.getTexture(Path.IMAGE_GLOBAL + "white.jpg");
	}
}
