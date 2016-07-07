package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * 缩放组件
 */
public class ZoomBox extends Actor{
	
	//传入要缩放的图片，以及zoombox初始时的宽高
	public ZoomBox(Image image,int width,int height) {
		//设置宽高
		this.setWidth(width);
		this.setHeight(height);
		
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
				// TODO Auto-generated method stub
				return super.scrolled(event, x, y, amount);
			}
		});
	}
}
