package com.rpsg.rpg.object.map;

import java.io.Serializable;

import com.badlogic.gdx.graphics.g2d.Batch;





/**
 * GDX-RPG 地图精灵超类<br>
 * 所有地图上的精灵都基于本超类，本超类（们）被{@link com.rpsg.rpg.view.GameView GameView}的{@link com.rpsg.rpg.ui.view.View#stage stage}所管理，
 * 被{@link com.rpsg.rpg.controller.MapController#draw(com.badlogic.gdx.graphics.g2d.Batch) MapController.draw()}所绘制
 */
public class MapSprite implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**所在Z层*/
	private int x;
	private int y;
	private int zIndex;
	private int speed;
	private int dx;
	private int dy;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getZIndex() {
		return zIndex;
	}
	
	public void setZIndex(int index) {
		this.zIndex = index;
	}
	
	public int getSpeed() {
		return speed;
	}

	public MapSprite(int x, int y, int zIndex, int speed) {
		this.x = x;
		this.y = y;
		this.zIndex = zIndex;
		this.speed = speed;
		this.dx = this.dy = 0;
	}

	public void setSpeed(int speed) { // 只能是48的约数（1，2，3，4，6，8，12，16，24，48）
		this.speed = speed;
	}
	
	public boolean move(int dx, int dy) { // dx和dy应为0、1或-1
		if (this.dx != 0 || this.dy != 0)
			return false;
		this.dx = dx;
		this.dy = dy;
		return true;
	}
	
	public void draw(Batch batch) {
		// TODO 画图
		
	}
	
	public void act() {
		x += dx * speed;
		y += dy * speed;
		if(dx != 0 || dy != 0) System.out.printf("x=%d y=%d\n", x, y); // debug
		if(x % 48 == 0 && y % 48 == 0)
			dx = dy = 0;
	}
}
