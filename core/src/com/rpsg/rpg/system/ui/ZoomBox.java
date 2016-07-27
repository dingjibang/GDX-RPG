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
				regAction();
				addAction(Actions.scaleBy(-1*(distance - initialDistance)/100,-1*(distance - initialDistance)/100,.7f));
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
	}
	
	/**获取绝对的x坐标（缩放后的）*/
	private float getOutX(float left, float right){
		if(left > 0) return -left;
		if(right < GameUtil.stage_width) return GameUtil.stage_width - right;
		return 0;
	}
	
	/**获取绝对的y坐标（缩放后的）*/
	private float getOutY(float top,float bottom){
		if(bottom > 0) return -bottom;
		if(top < GameUtil.stage_height) return GameUtil.stage_height - top;
		return 0;
	}
	
	private float getAbsWidth(){
		return getWidth() * getScaleX();
	}
	
	private float getAbsHeight(){
		return getHeight() * getScaleY();
	}
	
	@Override
	public Image position(float x, float y) {
		originX = x;
		originY = y;
		return super.position(x, y);
	}
	

}
