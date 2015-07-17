package com.rpsg.rpg.system.controller;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.io.Input;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.system.ui.HoverView;

public class HoverController{
	static List<HoverView> stack=new ArrayList<HoverView>();
	
	public static void add(Class<? extends HoverView> c){
		try {
			HoverView view = c.newInstance();
			view.superInit();
			stack.add(view);
		} catch (InstantiationException | IllegalAccessException e) {
			com.rpsg.rpg.utils.game.Logger.error("无法创建HoverView:"+c.toString(),e);
		}
	}
	
	public static boolean isEmpty(){
		return stack.isEmpty();
	}
	
	public static void add(HoverView hv){
		stack.add(hv);
	}
	
	public static void draw(SpriteBatch batch){
		logic();
		for (HoverView view : stack) {
			view.draw(batch);
		}
	}
	
	static void logic(){
		Input.state=stack.isEmpty()?IOMode.GameInput.NORMAL:IOMode.GameInput.HOVER;
		List<HoverView> removeList=new ArrayList<HoverView>();
		for(HoverView view:stack){
			if(view.disposed){
				view.dispose();
				removeList.add(view);
			}
		}
		stack.removeAll(removeList);
		for (HoverView view : stack) {
			view.logic();
		}
	}

	public static boolean keyDown(int keycode) {
		if(stack.isEmpty()) return false;
		return stack.get(stack.size()-1).keyDown(keycode);
	}

	public static boolean keyUp(int keycode) {
		if(stack.isEmpty()) return false;
		return stack.get(stack.size()-1).keyUp(keycode);
	}

	public static boolean keyTyped(char character) {
		if(stack.isEmpty()) return false;
		return stack.get(stack.size()-1).keyTyped(character);
	}

	public static boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(stack.isEmpty()) return false;
		return stack.get(stack.size()-1).touchDown(screenX, screenY, pointer, button);
	}

	public static boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(stack.isEmpty()) return false;
		return stack.get(stack.size()-1).touchUp(screenX, screenY, pointer, button);
	}

	public static boolean touchDragged(int screenX, int screenY, int pointer) {
		if(stack.isEmpty()) return false;
		return stack.get(stack.size()-1).touchDragged(screenX, screenY, pointer);
	}

	public static boolean mouseMoved(int screenX, int screenY) {
		if(stack.isEmpty()) return false;
		return stack.get(stack.size()-1).mouseMoved(screenX, screenY);
	}

	public static boolean scrolled(int amount) {
		if(stack.isEmpty()) return false;
		return stack.get(stack.size()-1).scrolled(amount);
	}
}
