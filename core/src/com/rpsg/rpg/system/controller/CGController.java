package com.rpsg.rpg.system.controller;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.system.ui.Image;

public class CGController {
	static ArrayList<Image> cgs = new ArrayList<Image>();

	public static void draw(SpriteBatch sb) {
		sb.begin();
		for(Image cg:cgs)
			cg.draw(sb);
		sb.end();
	}

	public static Image push(Image i) {
		if(cgs.contains(i)){
			Image re=new Image(i);
			cgs.add(re);
			return re;
		}else{
			cgs.add(i);
			return i;
		}
			
	}

	public static void dispose(Image i) {
		cgs.remove(i);
	}
	
}
