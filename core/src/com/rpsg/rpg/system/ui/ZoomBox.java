package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.rpsg.rpg.utils.game.GameUtil;

/**
 * 缩放组件
 */
public class ZoomBox extends Image{

	private static final float rate = 0.03f;
	
	private float originX = 0,originY = 0;
	
	// 传入要缩放的图片，以及zoombox初始时的宽高
	public ZoomBox(Drawable drawable) {
		setDrawable(drawable);
		setOrigin(Align.center);

		// 将图片等比拉抻到当前宽高下最大
		// TODO
		addListener(new ActorGestureListener(){
			@Override
			public void zoom(InputEvent event, float initialDistance, float distance) {
				float f = 1 + distance / 100;
				float x1 = getX();
				float y1 = getY();
				float ox = distance / 100 * rate * getWidth() / 2;
				float oy = distance / 100 * rate * getHeight() / 2;
				setX(x1 - ox);
				setY(y1 - oy);
				setWidth(getWidth() * f);
				setHeight(getHeight() * f);
				regAction();
			}
		});
		
		// 注册输入监听器
		addListener(new InputListener() {
			float offsetx=0,offsety=0;
			// 手或鼠标按下时触发
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				offsetx = event.getStageX() - getX();
				offsety = event.getStageY() - getY();
				clearActions();
				return true;
			}

			// 手或鼠标松开时触发
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				regAction();
				super.touchUp(event, x, y, pointer, button);
			}

			// 手或鼠标拖曳时触发
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				setPosition(event.getStageX()-offsetx,event.getStageY()-offsety);
			}

			// 鼠标使用滚轮时触发
			public boolean scrolled(InputEvent event, float x, float y, int amount) {
				amount = -amount;
				setScale(getScaleX() + amount/10f);
				setOrigin(0);
				regAction();
				if(getScaleX() < 1)
					addAction(Actions.scaleTo(1, 1,.7f,Interpolation.pow4Out));
				return super.scrolled(event, x, y, amount);
			}

		});

	}
	
	private void regAction(){
		clearActions();
		if(getHeight() * getScaleY() > GameUtil.getScreenHeight())
			addAction(Actions.moveBy(0, getOutY(), 1f, Interpolation.pow4Out));
		else
			addAction(Actions.moveBy(0, originY - getY(), 1f, Interpolation.pow4Out));
		if(getWidth() * getScaleX() > GameUtil.getScreenWidth())
			addAction(Actions.moveBy(getOutX(),0, 1f, Interpolation.pow4Out));
		else
			addAction(Actions.moveBy(originX - getX(),0, 1f, Interpolation.pow4Out));
	}
	
	private float getOutX(){
		float left = getX() - getWidth()/2 * getScaleX() + getWidth()/2;
		float right = getX() + getWidth()/2 * getScaleX() + getWidth()/2;
		if(left > 0)
			return -left ;
		if(right < GameUtil.getScreenWidth())
			return GameUtil.getScreenWidth() - right;
		return 0;
	}
	
	private float getOutY(){
		float bottom = getY() - getHeight()/2 * getScaleY() + getHeight()/2;
		float top = getY() + getHeight()/2 * getScaleY() + getHeight()/2;
		if(bottom > 0)
			return -bottom ;
		if(top < GameUtil.getScreenHeight())
			return GameUtil.getScreenHeight() - top;
		return 0;
	}
	
	@Override
	public Image position(float x, float y) {
		originX = x;
		originY = y;
		return super.position(x, y);
	}
	

}
