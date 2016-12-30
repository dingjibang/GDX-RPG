package com.rpsg.rpg.core;

import com.badlogic.gdx.graphics.Texture;
import com.rpsg.rpg.ui.Image;

/**
 * GDX-RPG UI工具类
 */
public class UI {
	public static Texture empty;
	
	public static Image empty(){
		return new Image(empty);
	}
	
	public static void init(){
		empty = Res.getTexture(Path.IMAGE_GLOBAL + "white.jpg");
	}
}
