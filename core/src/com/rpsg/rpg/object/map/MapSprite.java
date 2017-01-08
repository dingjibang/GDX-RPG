package com.rpsg.rpg.object.map;

import java.io.Serializable;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.rpsg.rpg.core.Res;
import com.rpsg.rpg.ui.Image;





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
	private Image img;
	private int dx;
	private int dy;
	public static enum Facing
	{
		DOWN(0, 0, -1),
		UP(64, 0, 1),
		LEFT(128, -1, 0),
		RIGHT(192, 1, 0);
		public int textureOffset;
		public int dx;
		public int dy;
		private Facing(int textureOffset, int dx, int dy)
		{
			this.textureOffset = textureOffset;
			this.dx = dx;
			this.dy = dy;
		}
	}
	private Facing facing;
	private int walkFrame;
	

	public MapSprite(int x, int y, int zIndex, int speed, String imageFileName) {
		this.x = x;
		this.y = y;
		this.zIndex = zIndex;
		this.speed = speed;
		this.dx = this.dy = 0;
		this.facing = Facing.DOWN;
		this.walkFrame = 0;
		setTexture(imageFileName);
	}
	
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

	public void setSpeed(int speed) { // 只能是48的约数（1，2，3，4，6，8，12，16，24，48）
		this.speed = speed;
	}
	
	public Facing getFacing() {
		return facing;
	}

	public void setFacing(Facing facing) {
		this.facing = facing;
	}

	public void setTexture(String fileName)
	{
		img = Res.sync(fileName);
	}
	
	public boolean walk(Facing dir)
	{
		if (move(dir.dx, dir.dy))
		{
			this.facing = dir;
			return true;
		}
		return false;
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
		int walkOffset = 48;
		if (walkFrame > 0 && walkFrame <= 36) walkOffset = 0;
		if (walkFrame > 48 && walkFrame <= 84) walkOffset = 96;
		((TextureRegionDrawable)img.getDrawable()).getRegion().setRegion(walkOffset, facing.textureOffset, 48, 64);
		img.setPosition(x, y);
		img.setSize(48, 64);
		img.draw(batch, 1.0f);
	}
	
	public void act() {
		if (dx != 0 || dy != 0)
		{
			x += dx * speed;
			y += dy * speed;
			walkFrame = (walkFrame + speed) % 96;
		}
		if (x % 48 == 0 && y % 48 == 0)
			dx = dy = 0;
	}
}
