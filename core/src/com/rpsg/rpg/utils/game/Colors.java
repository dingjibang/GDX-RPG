package com.rpsg.rpg.utils.game;

import com.badlogic.gdx.graphics.Color;

public class Colors {
	public static Color of(int r,int g,int b,float a){
		return new Color((float)r/255f,(float)g/255f,(float)b/255f,a);
	}
	
	public static Color of(int r,int g,int b){
		return of(r,g,b,1);
	}
	
	public static Color of(String hex){
		if(hex.startsWith("#")) hex = hex.substring(1);
		return Color.valueOf(hex);
	}
	
}

