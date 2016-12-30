package com.rpsg.rpg.core;

import com.badlogic.gdx.graphics.Texture;

/**
 * GDX-RPG UI工具类
 */
public class UI {
	public static Texture empty;
	
	public static void init(){
		empty = Res.getTexture(Path.IMAGE_MENU_GLOBAL + "white.jpg");
		
	}
}
