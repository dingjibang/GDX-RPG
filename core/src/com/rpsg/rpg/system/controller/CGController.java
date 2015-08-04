package com.rpsg.rpg.system.controller;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.system.ui.Image;

public class CGController {
	ArrayList<Image> cgs = new ArrayList<Image>();

	public void draw(SpriteBatch sb) {
		if(cgs.size()==0)
			return;
		try{
			for(Image cg:cgs){
				cg.act(Gdx.graphics.getDeltaTime());
				cg.draw(sb);
			}
		}catch(Exception e){
		}
	}

	public Image push(Image i) {
		if(cgs.contains(i)){
			Image re=new Image(i);
			cgs.add(re);
			return re;
		}else{
			cgs.add(i);
			return i;
		}
			
	}

	public void dispose(Image i) {
		cgs.remove(i);
	}
	
	public void disposeAll(){
		cgs.clear();
	}
	
}
