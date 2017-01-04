package com.rpsg.rpg.object.map;

import java.io.Serializable;

import com.badlogic.gdx.scenes.scene2d.Actor;





/**
 * GDX-RPG 地图精灵超类<br>
 * 所有地图上的精灵都基于本超类，本超类（们）被{@link com.rpsg.rpg.view.GameView GameView}的{@link com.rpsg.rpg.view.View#stage stage}所管理，
 * 被{@link com.rpsg.rpg.controller.MapController#draw(com.badlogic.gdx.graphics.g2d.Batch) MapController.draw()}所绘制
 */
public class MapSprite extends Actor implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**所在Z层*/
	private int zIndex;
	private int speed;

	public int getZIndex() {
		return zIndex;
	}
	
	public void setZIndex(int index) {
		this.zIndex = index;
	}
	
	public int getSpeed() {
		return speed;
	}

	public MapSprite(int zIndex, int speed) {
		super();
		this.zIndex = zIndex;
		this.speed = speed;
	}

	public void setSpeed(int speed) { // 只能是48的约数（1，2，3，4，6，8，12，16，24，48）
		this.speed = speed;
	}
}
