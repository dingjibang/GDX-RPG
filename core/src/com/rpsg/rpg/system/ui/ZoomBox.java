package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * 缩放组件
 */
public class ZoomBox extends Actor{
	
	private Image img;
	
	private static final float rate = 0.01f;
	
	//传入要缩放的图片，以及zoombox初始时的宽高
	public ZoomBox(Image image,int width,int height) {
		//设置宽高
		this.setWidth(width);
		this.setHeight(height);
		img = image;
		img.setWidth(width);
		img.setHeight(height);
		
		//将图片等比拉抻到当前宽高下最大
		//TODO
		
		//注册输入监听器
		addListener(new InputListener(){
			//手或鼠标按下时触发
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				return super.touchDown(event, x, y, pointer, button);
			}
			
			//手或鼠标松开时触发
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				super.touchUp(event, x, y, pointer, button);
			}
			
			//手或鼠标拖曳时触发
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				// TODO Auto-generated method stub
				super.touchDragged(event, x, y, pointer);
			}
			
			//鼠标使用滚轮时触发
			public boolean scrolled(InputEvent event, float x, float y, int amount) {
				float f = (1+amount*rate);
				img.setWidth(img.getWidth()*f);
				img.setHeight(img.getHeight()*f);
				return super.scrolled(event, x, y, amount);
			}
			
		});
		
		
		Gdx.input.setInputProcessor(new GestureDetector(new GestureListener(){

			@Override
			public boolean touchDown(float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean tap(float x, float y, int count, int button) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean longPress(float x, float y) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean fling(float velocityX, float velocityY, int button) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean pan(float x, float y, float deltaX, float deltaY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean panStop(float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean zoom(float initialDistance, float distance) {
				float f = distance/100;
				img.setWidth(img.getWidth()*f);
				img.setHeight(img.getHeight()*f);
				return false;
			}

			@Override
			public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
				// TODO Auto-generated method stub
				return false;
			}}));
	}
	
	public void draw (Batch batch, float parentAlpha) {
		img.draw(batch, parentAlpha);		
	}
}
