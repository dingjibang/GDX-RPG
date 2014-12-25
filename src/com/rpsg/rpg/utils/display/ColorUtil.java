package com.rpsg.rpg.utils.display;

import com.badlogic.gdx.graphics.Color;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;


public class ColorUtil {
	public static Color night=new Color(0.3f,0.3f,1,1);
	public static Color day=Color.WHITE;
	public static Color dusk=new Color(0.85f,0.85f,0.4f,1);
	
	public static Color currentColor;
	
	public static BaseScriptExecutor set(final Script script,Color color){
		return script.add(()->{
			currentColor=color;
		});
	}
}
