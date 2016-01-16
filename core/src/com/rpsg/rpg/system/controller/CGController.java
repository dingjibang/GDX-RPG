package com.rpsg.rpg.system.controller;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.ui.Image;

public class CGController {
	ArrayList<Image> cgs = new ArrayList<Image>();

	public synchronized void draw(SpriteBatch sb) {
		if(cgs.size()==0)
			return;
		try{
			synchronized (cgs) {
				System.out.println("dray");
				for(Image cg:cgs){
					cg.act(Gdx.graphics.getDeltaTime());
					cg.draw(sb);
				}
			}
			
		}catch(Exception e){
		}
	}

	public synchronized Image push(Image i) {
		System.out.println("push");
		if(cgs.contains(i)){
			Image re=new Image(i);
			cgs.add(re);
			return re;
		}else{
			cgs.add(i);
			return i;
		}
			
	}

	public synchronized void dispose(Image i) {
		cgs.remove(i);
	}
	
	public void disposeAll(){
		cgs.clear();
	}

	public BaseScriptExecutor pushSync(final Script script,final Image cg) {
		return script.set(new BaseScriptExecutor() {
			public void init() {
				push(cg);
			}
		});
	}
	
}
