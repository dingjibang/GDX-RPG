package com.rpsg.rpg.system.controller;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.system.ui.Image;

public class CGController {
	private ConcurrentLinkedQueue<Image> cgs = new ConcurrentLinkedQueue<Image>();

	public synchronized void draw(SpriteBatch sb) {
		if(cgs.size()==0)
			return;
		try {
			for(Image cg:cgs){
				cg.act(Gdx.graphics.getDeltaTime());
				cg.draw(sb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized Image push(Image i) {
		synchronized (cgs) {
			if(cgs.contains(i)){
				Image re=new Image(i);
				cgs.add(re);
				return re;
			}else{
				cgs.add(i);
				return i;
			}
		}
	}
	
	public synchronized Iterable<Image> pushAll(Iterable<Image> c){
		$.each(c,(obj)->push(obj));
		return c;
	}

	public synchronized CGController dispose(Image i) {
		cgs.remove(i);
		return this;
	}
	
	public synchronized CGController dispose(Iterable<Image> c){
		$.each(c,(obj)->dispose(obj));
		return this;
	}
	
	public CGController disposeAll(){
		cgs.clear();
		return this;
	}

}
