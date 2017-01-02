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
	public int zIndex;
	
	public int getZIndex() {
		return zIndex;
	}
	
	public void setZIndex(int index) {
		this.zIndex = index;
	}
}
